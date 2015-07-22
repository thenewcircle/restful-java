package com.example.chirp.app.kernel;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.pub.PubChirp;

/**
 * Entity representing a "chirp" posted by a user. To properly create a Chirp,
 * call User.createChirp().
 */
public class Chirp {

	private final ChirpId id;
	private final String content;
	private final User user;

	public Chirp(ChirpId id, String content, User user) {
		this.id = id;
		this.content = content;
		this.user = user;
	}

	public ChirpId getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public User getUser() {
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Chirp other = (Chirp) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "Chirp [id=" + id + ", content=" + content + "]";
	}

	public PubChirp toPubChirp(UriInfo uriInfo) {

		// http://localhost:8080/chirp-app/chrips/123
		URI self = uriInfo.getBaseUriBuilder().path("chirps").path(id.toString()).build();

		// http://localhost:8080/chirp-app/users/tom/chirps
		URI chirpsLink = uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).path("chirps").build();

		// http://localhost:8080/chirp-app/users/tom
		URI userLink = uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).build();

		// http://localhost:8080/chirp-app/users/
		// URI allUsersLink = uriInfo.getBaseUriBuilder().path("users").build();

		return new PubChirp(id.toString(), content, self, chirpsLink, userLink);
	}
}
