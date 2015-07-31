package chirp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

	private Map<String, User> users;

	private final List<Set<User>> bulkDeletions = new ArrayList<Set<User>>();

	private UserRepository(boolean favorPersistence) {
		users = new ConcurrentHashMap<String, User>();
		logger.trace("Created new UserRepositry with new Users Map");
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

	private static String duplicateUserMessage(String operation, String username) {
		return String.format(
				"Can not %s a user with username=%s as one already exists.",
				operation, username);
	}

	public User createUser(String username, String realname) {
		if (users.containsKey(username))
			throw new DuplicateEntityException(duplicateUserMessage("create",
					username));

		User user = new User(username, realname);
		user.setLastModificationTime(new Date());
		users.put(username, user);
		return user;
	}

	public Deque<User> getUsers() {
		return new LinkedList<User>(users.values());
	}

	private static String noSuchUserMessage(String operation, String username) {
		return String.format(
				"Can not %s user with username=%s as it does not exist.",
				operation, username);
	}

	public User getUser(String username) {
		User user = users.get(username);
		if (user == null)
			throw new NoSuchEntityException(noSuchUserMessage("find", username));

		return user;
	}

	public void deleteUser(String username) {
		if (users.remove(username) == null)
			throw new NoSuchEntityException(noSuchUserMessage("delete",
					username));
	}

	public int createBulkDeletion() {
		bulkDeletions.add(new HashSet<User>());
		return bulkDeletions.size() - 1;
	}

	public void addToBulkDeletion(int id, String username) {
		try {
			bulkDeletions.get(id).add(getUser(username));
		} catch (Exception e) {
			throw new NoSuchEntityException("Can add user with an id of "
					+ username + " to bulk deletion set as it does not exist.");
		}
	}

	public void cancelBulkDeletion(int id) {
		try {
			bulkDeletions.set(id, null);
		} catch (Exception e) {
			throw new NoSuchEntityException("Can remove object with an id of "
					+ id + " from bulk deletion set as it does not exist.");
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
			throw new NoSuchEntityException(
					"Cannot not commit deletion set as entity with id " + id
							+ " does not exist");
		}
	}

}
