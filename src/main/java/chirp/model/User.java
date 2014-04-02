package chirp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Entity representing a user of the "chirp" service. A user logically owns a
 * collection of posts, indexed by timestamp.
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String realname;
	private final Map<Timestamp, Post> posts = new TreeMap<Timestamp, Post>();

	public User() {
	}
	
	public User(String username, String realname) {
		this.username = username;
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Collection<Post> getPosts() {
		return new ArrayList<Post>(posts.values());
	}

	public void setPosts(Map<Timestamp, Post> posts) {
		this.posts.clear();
		this.posts.putAll(posts);
	}

	public Post createPost(String content) {
		Timestamp timestamp = new Timestamp();
		return createPost(content, timestamp);
	}

	public Post createPost(String content, Timestamp timestamp) {
		if (posts.containsKey(timestamp))
			throw new DuplicateEntityException(Post.class, timestamp);

		Post post = new Post(timestamp, content, this);
		posts.put(timestamp, post);
		return post;
	}

	public Post getPost(Timestamp timestamp) {
		Post post = posts.get(timestamp);
		if (post == null)
			throw new NoSuchEntityException(Post.class, timestamp);

		return post;
	}

	public void deletePost(String timestamp) {
		if (posts.remove(timestamp) == null)
			throw new NoSuchEntityException(Post.class, timestamp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [username=" + username + "]";
	}

}
