package com.example.chirp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.util.dao.BaseEntity;

/**
 * Entity representing a user of the "chirp" service. A user logically owns a
 * collection of posts, indexed by timestamp.
 */
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String username;
	private String realname;
	private final List<Post> posts = new ArrayList<Post>();

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
		return posts;
	}

	public void setPosts(Collection<Post> posts) {
		this.posts.clear();
		this.posts.addAll(posts);
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
