package com.example.chirp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.chirp.model.Post;
import com.example.chirp.model.User;
import com.example.util.dao.EntityDao;
import com.example.util.dao.HibernateJpaEntityDao;

/**
 * Data access object for users. Note that the user repository also manages bulk
 * deletion of users, which will be covered when modeling RESTful transactions.
 * This class is thread-safe.
 */
public class JpaChirpRepository implements ChirpRepository {

	private static Logger logger = LoggerFactory.getLogger(JpaChirpRepository.class);

	private static final JpaChirpRepository instance = new JpaChirpRepository();
	
	private EntityManager persistence;
	private EntityDao<User> userDao = new HibernateJpaEntityDao<User>(User.class, persistence);
	private EntityDao<Post> postDao = new HibernateJpaEntityDao<Post>(Post.class, persistence);

	private final List<Set<User>> bulkDeletions = new ArrayList<Set<User>>();

	private JpaChirpRepository() {
		logger.trace("Created new JpaChirpRepository.");
	}

	/**
	 * Use this method to create a new UserRespository suitable for unit testing
	 * if a repository does not exist. Always return an existing repository.
	 * 
	 * @return a new empty UserRepository
	 */
	public static JpaChirpRepository getInstance() {
		return instance;
	}

	/**
	 * Use this method to reset the current database.
	 */
	public synchronized void clear() {
		persistence.createNativeQuery("DELETE FROM USER").executeUpdate();
		persistence.createNativeQuery("DELETE FROM MESSAGE").executeUpdate();
	}

	public User createUser(String username, String realname) {
		User user = new User(username, realname);
		persistence.persist(user);
		return user;
	}

	public Collection<User> getUsers() {
		return userDao.findAll();
	}

	public User getUser(String username) {
		TypedQuery<User> query = persistence.createQuery("from User u where u.username=:username", User.class).setParameter("username", username);
		User user = query.getSingleResult();
		return user;
	}

	public boolean isExistingUser(String username) {
		TypedQuery<User> query = persistence.createQuery("from User u where u.username=:username", User.class).setParameter("username", username);
		List<User> list = query.getResultList();
		return list.size() > 0;
	}

	public void deleteUser(String username) {
		User user = getUser(username);
		userDao.delete(user);
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
			if (!isExistingUser(user.getUsername())) {
				return false;
			}
		}
		for (User user : bulkDeletion) {
			deleteUser(user.getUsername());
		}
		return true;
	}

	@Override
	public Post createPost(String username, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExistingPost(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Post> getPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post getPost(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePost(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Post createPost(String username, String content, String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
