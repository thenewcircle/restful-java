package chirp.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data access object for users. Note that the user repository also manages bulk
 * deletion of users, which will be covered when modeling RESTful transactions.
 */
public class UserRepository implements Serializable {

	private static final long serialVersionUID = 2526248585736292013L;

	private static UserRepository instance;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final File file = new File("state.bin");
	private Map<String, User> users;

	private final List<Set<User>> bulkDeletions = new ArrayList<Set<User>>();

	private UserRepository(boolean favorPersistence) {
		if (favorPersistence == false || file.exists() == false) {
			users = new ConcurrentHashMap<String, User>();
			logger.info("Created new UserRepositry with new Users Map");
		} else {
			users = thaw();
			logger.info("Created new UserRepositry from persisted Users Map");
		}
	}

	public static UserRepository getInstance() {
		return getInstance(false);
	}

	public static synchronized UserRepository getInstance(
			boolean favorPersistence) {
		if (instance == null)
			instance = new UserRepository(favorPersistence);

		return instance;
	}

	public synchronized void clear() {
		users = new ConcurrentHashMap<String, User>();
		logger.info("Cleared / recreated Users Map");

	}

	@SuppressWarnings("unchecked")
	private static Map<String, User> thaw() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				file))) {
			return (Map<String, User>) in.readObject();
		} catch (Exception e) {
			return new ConcurrentHashMap<String, User>();
		}
	}

	public void freeze() {
		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(file))) {
			out.writeObject(users);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User createUser(String username, String realname) {
		if (users.containsKey(username))
			throw new DuplicateEntityException();

		User user = new User(username, realname);
		users.put(username, user);
		return user;
	}

	public Collection<User> getUsers() {
		return new ArrayList<User>(users.values());
	}

	public User getUser(String username) {
		User user = users.get(username);
		if (user == null)
			throw new NoSuchEntityException();

		return user;
	}

	public void deleteUser(String username) {
		if (users.remove(username) == null)
			throw new NoSuchEntityException();
	}

	public int createBulkDeletion() {
		bulkDeletions.add(new HashSet<User>());
		return bulkDeletions.size() - 1;
	}

	public void addToBulkDeletion(int id, String username) {
		try {
			bulkDeletions.get(id).add(getUser(username));
		} catch (Exception e) {
			throw new NoSuchEntityException();
		}
	}

	public void cancelBulkDeletion(int id) {
		try {
			bulkDeletions.set(id, null);
		} catch (Exception e) {
			throw new NoSuchEntityException();
		}
	}

	public boolean commitBulkDeletion(int id) {
		try {
			Set<User> bulkDeletion = bulkDeletions.get(id);
			for (User user : bulkDeletion) {
				if (!users.containsValue(user)) {
					return false;
				}
			}
			for (User user : bulkDeletion) {
				users.remove(user.getUsername());
			}
			bulkDeletions.set(id, null);
			return true;
		} catch (Exception e) {
			throw new NoSuchEntityException();
		}
	}

}