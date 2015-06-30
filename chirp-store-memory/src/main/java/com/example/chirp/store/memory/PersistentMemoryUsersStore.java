package com.example.chirp.store.memory;

import com.example.chirp.kernel.User;
import com.example.chirp.kernel.stores.UsersStoreUtils;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data access object for users. Note that the user repository also manages bulk
 * deletion of users, which will be covered when modeling RESTful transactions.
 * This class is thread-safe.
 */
public class PersistentMemoryUsersStore extends InMemoryUsersStore implements Serializable {

	private static final long serialVersionUID = 2526248585736292013L;

	private static final File file = new File("state.bin");

	public PersistentMemoryUsersStore(boolean seedDatabase) {
		super(false);

		users = thaw();
		logger.trace("Created new UserRepository from persisted Users Map");

		if (seedDatabase) {
			UsersStoreUtils.resetAndSeedRepository(this);
		}
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
	 * @return the "thawed" map of users
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, User> thaw() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(file));
			return (Map<String, User>) in.readObject();
		} catch (Exception e) {
			return new ConcurrentHashMap<String, User>();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * Call this method to persist the state of the user repository to the file
	 * <code>state.bin</code>.
	 */
	public void freeze() {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(users);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
