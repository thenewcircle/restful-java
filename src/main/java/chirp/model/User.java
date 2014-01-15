package chirp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Entity representing a user of the "chirp" service. A user logically owns a
 * collection of posts, indexed by timestamp.
 */
@XmlRootElement
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private final String username;
	@XmlElement
	private final String realname;

	@JsonIgnore
	@XmlElement
	private final Map<Timestamp, Post> posts = new TreeMap<Timestamp, Post>();

	public User() {
		this.username = null;
		this.realname = null;
	}

	@JsonCreator
	public User(@JsonProperty("username") String username,
			@JsonProperty("realname") String realname) {
		this.username = username;
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public String getRealname() {
		return realname;
	}

	public Post createPost(String content) {
		Timestamp timestamp = new Timestamp();
		if (posts.containsKey(timestamp))
			throw new DuplicateEntityException("The post with timestamp "
					+ timestamp + " already exists");

		Post post = new Post(timestamp, content, this);
		posts.put(timestamp, post);
		return post;
	}

	public Collection<Post> getPosts() {
		return new ArrayList<Post>(posts.values());
	}

	public Post getPost(Timestamp timestamp) {
		Post post = posts.get(timestamp);
		if (post == null)
			throw new NoSuchEntityException("No timestamp equal to "
					+ timestamp + " exists for user " + username);

		return post;
	}

	public void deletePost(String timestamp) {
		if (posts.remove(timestamp) == null)
			throw new NoSuchEntityException("No timestamp equal to "
					+ timestamp + " exists for user " + username);
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
