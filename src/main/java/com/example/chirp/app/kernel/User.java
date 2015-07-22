package com.example.chirp.app.kernel;

import java.net.URI;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.UserResource;
import com.example.chirp.app.UserResource.Variant;
import com.example.chirp.app.kernel.exceptions.DuplicateEntityException;
import com.example.chirp.app.kernel.exceptions.NoSuchEntityException;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubUser;

/**
 * Entity representing a user of the "chirp" service. A user logically owns a
 * collection of chirps, indexed by id.
 */
public class User {

	private final String username;
	private final String realname;
	private final Map<ChirpId, Chirp> chirps = new TreeMap<ChirpId, Chirp>();

	public User(String username, String realname) {
		this.username = username;
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public String getRealname() {
		return realname;
	}

	public Chirp createChirp(String content) {
		ChirpId chirpId = new ChirpId();
		if (chirps.containsKey(chirpId))
			throw new DuplicateEntityException();

		Chirp chirp = new Chirp(chirpId, content, this);
		chirps.put(chirpId, chirp);
		return chirp;
	}

	public Chirp createChirp(String content, String id) {
		ChirpId chripId = new ChirpId(id);
		if (chirps.containsKey(chripId)) {
			throw new DuplicateEntityException();
		}
		return addChirp(new Chirp(chripId, content, this));
	}

	public Chirp addChirp(Chirp chirp) {
		this.chirps.put(chirp.getId(), chirp);
		return chirp;
	}

	public Deque<Chirp> getChirps() {
		return new LinkedList<Chirp>(chirps.values());
	}

	public Chirp getChirp(ChirpId id) {
		Chirp chirp = chirps.get(id);
		if (chirp == null)
			throw new NoSuchEntityException();

		return chirp;
	}

	public void deleteChirp(String id) {
		if (chirps.remove(id) == null)
			throw new NoSuchEntityException();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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

	public PubUser toPubUser(UriInfo uriInfo, String variantString) {

		Variant variant;

		try {
			if (variantString == null || variantString.isEmpty()) {
				variant = Variant.STANDARD;
			} else {
				variant = Variant.valueOf(variantString);
			}
		} catch (IllegalArgumentException e) {
			String msg = String.format("The value %s is not a valid Variant.", variantString);
			throw new BadRequestException(msg, e);
		}

		URI selfLink = uriInfo.getBaseUriBuilder().path("users").path(username).build();
		URI chirpsLink = uriInfo.getBaseUriBuilder().path("users").path(username).path("chirps").build();

		List<PubChirp> pubChirps = new ArrayList<>();
		if (UserResource.Variant.FULL == variant) {
			for (Chirp chirp : this.getChirps()) {
				PubChirp pubChirp = chirp.toPubChirp(uriInfo);
				pubChirps.add(pubChirp);
			}
		}

		return new PubUser(chirpsLink, selfLink, username, realname, pubChirps);
	}
}
