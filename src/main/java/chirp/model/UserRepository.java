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
 * This class is thread-safe.
 */
public class UserRepository implements Serializable {

	private static final long serialVersionUID = 2526248585736292013L;

	private static final File file = new File("state.bin");

	private static Logger logger = LoggerFactory
			.getLogger(UserRepository.class);

	private static final UserRepository instance = new UserRepository();

	private Map<String, User> users = new ConcurrentHashMap<String, User>();

	private final List<Set<User>> bulkDeletions = new ArrayList<Set<User>>();

	private UserRepository() {
		logger.trace("Created new UserRepositry.");
		prepopulate();
	}

	/**
	 * Use this method to create a new UserRespository suitable for unit testing
	 * if a repository does not exist. Always return an existing repository.
	 * 
	 * @return a new empty UserRepository
	 */
	public static UserRepository getInstance() {
		return instance;
	}

	/**
	 * Use this method to empty the current UserRepository.
	 */
	public synchronized void clear() {
		users = new ConcurrentHashMap<String, User>();
	}

	public User createUser(String username, String realname) {
		if (users.containsKey(username))
			throw new DuplicateEntityException(User.class, username);

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
			throw new NoSuchEntityException(User.class, username);

		return user;
	}

	public void deleteUser(String username) {
		if (users.remove(username) == null)
			throw new NoSuchEntityException(User.class, username);
	}

	public int createBulkDeletion() {
		bulkDeletions.add(new HashSet<User>());
		return bulkDeletions.size() - 1;
	}

	public void addToBulkDeletion(int id, String username) {
		Set<User> bulkDeletion = bulkDeletions.get(id);
		if (bulkDeletion == null) {
			throw new NoSuchEntityException("BulkDeletion", id);
		}
		bulkDeletion.add(getUser(username));
	}

	public void cancelBulkDeletion(int id) {
		Set<User> bulkDeletion = bulkDeletions.get(id);
		if (bulkDeletion == null) {
			throw new NoSuchEntityException("BulkDeletion", id);
		}
		bulkDeletions.set(id, null);
	}

	public boolean commitBulkDeletion(int id) {
		Set<User> bulkDeletion = bulkDeletions.get(id);
		if (bulkDeletion == null) {
			throw new NoSuchEntityException("BulkDeletion", id);
		}
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
	}

	/**
	 * Call this method to create user repository from the file
	 * <code>state.bin</code> if it exists.
	 */
	public void thaw() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				file))) {
			@SuppressWarnings("unchecked")
			Map<String, User> newUsers = (Map<String, User>) in.readObject();
			users.clear();
			users.putAll(newUsers);
			logger.trace("User repository state loaded from file.");
		} catch (Exception e) {
			logger.error("User repository state loaded failed to load from file.");
			users = new ConcurrentHashMap<String, User>();
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

	@SuppressWarnings("unused")
	public final void prepopulate() {
		User maul = this.createUser("maul", "Darth Maul");
		User luke = this.createUser("luke", "Luke Skywaler");
		luke.createPost("I am a Jedi, like my father before me.",
				new Timestamp("10001111000000"));
		User vader = this.createUser("vader", "Darth Vader");
		vader.createPost("You have failed me for the last time.",
				new Timestamp("10001111000000"));
		User yoda = this.createUser("yoda", "Master Yoda");
		yoda.createPost("Do or do not.  There is no try.", new Timestamp(
				"10001111000000"));
		yoda.createPost(
				"Fear leads to anger, anger leads to hate, and hate leads to suffering.",
				new Timestamp("10001111000001"));
	}

}
