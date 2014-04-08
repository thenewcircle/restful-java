package chirp.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data access object for users. Note that the user repository also manages bulk
 * deletion of users, which will be covered when modeling RESTful transactions.
 * This class is thread-safe.
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
			logger.trace("Created new UserRepositry with new Users Map");
		} else {
			users = thaw();
			logger.trace("Created new UserRepositry from persisted Users Map");
		}
	}

	/**
	 * Use this method to create a new UserRespository suitable for unit testing
	 * if a repository does not exist. Always return an existing repository.
	 * 
	 * @return a new empty UserRepository
	 */
	public static UserRepository getInstance() {
		return getInstance(false);
	}

	/**
	 * Use this method to create a UserRespository
	 * 
	 * @param favorPersistence
	 *            Create a repository if one has not been create already
	 *            according the value of the parameter. If a repository has not
	 *            been created and favorPersistence equals false, create an
	 *            empty repository, otherwise, if true, create a repository from
	 *            the state.bin file if it exists. If the file does not exist,
	 *            create an empty new repository.
	 * @return a UserRepistory.
	 */
	public static synchronized UserRepository getInstance(
			boolean favorPersistence) {
		if (instance == null)
			instance = new UserRepository(favorPersistence);

		return instance;
	}

	/**
	 * Use this method to empty the current UserRepository.
	 */
	public synchronized void clear() {
		users = new ConcurrentHashMap<String, User>();
	}

	/**
	 * Call this method to create user repository from the file
	 * <code>state.bin</code> if it exists.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, User> thaw() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				file))) {
			return (Map<String, User>) in.readObject();
		} catch (Exception e) {
			return new ConcurrentHashMap<String, User>();
		}
	}

	/**
	 * Call this method to persist the state of the user repository to the file
	 * <code>state.bin</code>.
	 */
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

	public Deque<User> getUsers() {
		return new LinkedList<User>(users.values());
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
