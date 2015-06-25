package com.example.chirp.store.memory;

import com.example.chirp.kernel.User;
import com.example.chirp.kernel.exceptions.DuplicateEntityException;
import com.example.chirp.kernel.exceptions.NoSuchEntityException;
import com.example.chirp.kernel.stores.UserStore;
import com.example.chirp.kernel.stores.UserStoreUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data access object for users. Note that the user repository also manages bulk
 * deletion of users, which will be covered when modeling RESTful transactions.
 * This class is thread-safe.
 */
public class InMemoryUsersStore implements UserStore {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Map<String, User> users;

	private final List<Set<User>> bulkDeletions = new ArrayList<>();

	public InMemoryUsersStore(boolean seedDatabase) {
		users = new ConcurrentHashMap<>();
		logger.trace("Created new UserRepository with new Users Map");

		if (seedDatabase) {
			UserStoreUtils.resetAndSeedRepository(this);
		}
	}

	/**
	 * Use this method to empty the current UserRepository.
	 */
	public synchronized void clear() {
		users = new ConcurrentHashMap<>();
	}

  public final User createUser(String username, String fullName) {
    if (users.containsKey(username))
      throw new DuplicateEntityException();

    User user = new User(username, fullName);
    users.put(username, user);
    return user;
  }

  public final void updateUser(User user) {
    users.put(user.getUsername(), user);
  }

	public final Deque<User> getUsers() {
		return new LinkedList<>(users.values());
	}

	public final User getUser(String username) {
		User user = users.get(username);
		if (user == null)
			throw new NoSuchEntityException();

		return user;
	}

	public final void deleteUser(String username) {
		if (users.remove(username) == null)
			throw new NoSuchEntityException();
	}

	public final int createBulkDeletion() {
		bulkDeletions.add(new HashSet<User>());
		return bulkDeletions.size() - 1;
	}

	public final void addToBulkDeletion(int id, String username) {
		try {
			bulkDeletions.get(id).add(getUser(username));
		} catch (Exception e) {
			throw new NoSuchEntityException();
		}
	}

	public final void cancelBulkDeletion(int id) {
		try {
			bulkDeletions.set(id, null);
		} catch (Exception e) {
			throw new NoSuchEntityException();
		}
	}

	public final boolean commitBulkDeletion(int id) {
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
