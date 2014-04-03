package com.example.chirp.model;

import java.io.Serializable;

/**
 * Entity representing a "chirp" posted by a user. To properly create a Post,
 * call User.createPost().
 */
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	private Timestamp timestamp;
	private String content;
	private User user;

	public Post() {
	}
	
	public Post(Timestamp timestamp, String content, User user) {
		this.timestamp = timestamp;
		this.content = content;
		this.user = user;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Post other = (Post) obj;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String username = (user != null) ? user.getUsername() : null;
		String result = String.format("Post[user=\"%s\", content=\"%s\", timestamp=\"%s\"]", username, content, timestamp);
		return result;
	}

}
