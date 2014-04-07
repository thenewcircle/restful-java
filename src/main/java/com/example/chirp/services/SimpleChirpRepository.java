package com.example.chirp.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.example.chirp.model.Post;
import com.example.chirp.model.User;
import com.example.util.dao.GuidRepository;

/**
 * Data access object for users. Note that the user repository also manages bulk
 * deletion of users, which will be covered when modeling RESTful transactions.
 * This class is thread-safe.
 */
public class SimpleChirpRepository implements Serializable, ChirpRepository {

	private static final long serialVersionUID = 2526248585736292013L;

	private final GuidRepository guidRepository;
	
	private Map<String, User> users = new ConcurrentHashMap<String, User>();

	private Map<String, Post> posts = new ConcurrentHashMap<String, Post>();

	private final List<Set<User>> bulkDeletions = new ArrayList<Set<User>>();

	public SimpleChirpRepository(GuidRepository guidRepository) {
		this.guidRepository = guidRepository;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#clear()
	 */
	@Override
	public synchronized void clear() {
		users.clear();
		posts.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#createUser(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public User createUser(String username, String realname) {
		if (users.containsKey(username))
			throw new DuplicateEntityException(User.class, username);
		User user = new User(username, realname);
		user.setId((long) System.identityHashCode(user));
		users.put(username, user);
		return user;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#isExistingUser(java.lang.String)
	 */
	@Override
	public boolean isExistingUser(String username) {
		return users.containsKey(username);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#getUsers()
	 */
	@Override
	public Collection<User> getUsers() {
		return new ArrayList<User>(users.values());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#getUser(java.lang.String)
	 */
	@Override
	public User getUser(String username) {
		User user = users.get(username);
		if (user == null)
			throw new NoSuchEntityException(User.class, username);

		return user;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String username) {
		User user = users.remove(username);
		if (user == null)
			throw new NoSuchEntityException(User.class, username);
		for (Post post : user.getPosts()) {
			String id = post.getTimestamp().toString();
			deletePost(id);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#createPost(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Post createPost(String username, String content) {
		User user = getUser(username);
		Post post = new Post();
		post.setId((long) System.identityHashCode(post));
		String guid = guidRepository.createGuid(post);
		post.setContent(content);
		post.setUser(user);
		post.setGuid(guid);
		posts.put(guid, post);
		user.getPosts().add(post);
		return post;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#createPost(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Post createPost(String username, String content, String guid) {
		User user = getUser(username);
		Post post = new Post();
		post.setId((long) System.identityHashCode(post));
		guidRepository.registerGuid(guid, post);
		post.setContent(content);
		post.setUser(user);
		post.setGuid(guid);
		posts.put(guid, post);
		user.getPosts().add(post);
		return post;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#isExistingPost(java.lang.String)
	 */
	@Override
	public boolean isExistingPost(String guid) {
		return posts.containsKey(guid);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#getPosts()
	 */
	@Override
	public Collection<Post> getPosts() {
		return new ArrayList<Post>(posts.values());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#getPost(java.lang.String)
	 */
	@Override
	public Post getPost(String guid) {
		Post post = posts.get(guid);
		if (post == null)
			throw new NoSuchEntityException(Post.class, guid);
		return post;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#deletePost(java.lang.String)
	 */
	@Override
	public void deletePost(String guid) {
		if (posts.remove(guid) == null)
			throw new NoSuchEntityException(Post.class, guid);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#createBulkDeletion()
	 */
	@Override
	public int createBulkDeletion() {
		bulkDeletions.add(new HashSet<User>());
		return bulkDeletions.size() - 1;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#addToBulkDeletion(int,
	 *      java.lang.String)
	 */
	@Override
	public void addToBulkDeletion(int id, String username) {
		Set<User> bulkDeletion = bulkDeletions.get(id);
		if (bulkDeletion == null) {
			throw new NoSuchEntityException("BulkDeletion", id);
		}
		bulkDeletion.add(getUser(username));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#cancelBulkDeletion(int)
	 */
	@Override
	public void cancelBulkDeletion(int id) {
		Set<User> bulkDeletion = bulkDeletions.get(id);
		if (bulkDeletion == null) {
			throw new NoSuchEntityException("BulkDeletion", id);
		}
		bulkDeletions.set(id, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.chirp.services.ChirpRepository#commitBulkDeletion(int)
	 */
	@Override
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

}
