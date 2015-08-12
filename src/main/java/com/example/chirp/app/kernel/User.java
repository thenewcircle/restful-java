package com.example.chirp.app.kernel;

import java.net.URI;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.kernel.exceptions.DuplicateEntityException;
import com.example.chirp.app.kernel.exceptions.NoSuchEntityException;
import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.pub.PubUtils;

/**
 * Entity representing a user of the "chirp" service. A user logically owns a
 * collection of chirps, indexed by id.
 */
public class User {

	private final String username;
	private final String realName;
	private final Map<ChirpId, Chirp> chirps = new TreeMap<ChirpId, Chirp>();

	public User(String username, String realName) {
		this.username = username;
		this.realName = realName;
	}

	public String getUsername() {
		return username;
	}

	public String getRealName() {
		return realName;
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
			throw new NoSuchEntityException(Chirp.class, id.getId());

		return chirp;
	}

	public void deleteChirp(String id) {
		if (chirps.remove(id) == null)
			throw new NoSuchEntityException(Chirp.class, id);
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

	public PubUser toPubUser(URI uri, UriInfo uriInfo, 
			String limitString, 
			String offsetString, 
			String spraseString) {
		
		PubChirps chirps = null;
		boolean sparse;

		try {
			sparse = Boolean.valueOf(spraseString);
		} catch (Exception e) {
			throw new BadRequestException("The value sprase is not a boolean value.");
		}
		
		if (sparse == false) {
			chirps = PubUtils.toPubChirps(uriInfo, this, limitString, offsetString);
		}

		return new PubUser(uri, username, realName, chirps);
	}
}








