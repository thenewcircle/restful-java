package com.example.chirp.kernel;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.example.chirp.kernel.exceptions.DuplicateEntityException;
import com.example.chirp.kernel.exceptions.NoSuchEntityException;
import com.example.chirp.pub.PubUser;


/**
 * Entity representing a user of the "chirp" service. A user logically owns a
 * collection of chirps, indexed by id.
 */
public class User implements Serializable {

	public static enum Variant {summary, full, abbreviated};

	private static final long serialVersionUID = 1L;

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
			throw new DuplicateEntityException(chirpId.toString());

		Chirp chirp = new Chirp(chirpId, content, this);
		chirps.put(chirpId, chirp);
		return chirp;
	}

	public Chirp createChirp(String content, String id) {
		ChirpId chripId = new ChirpId(id);
		if (chirps.containsKey(chripId)) {
			throw new DuplicateEntityException(id);
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

	public PubUser toPubUser(String variantString, URI self, URI parent) {
		
		Variant variant;
		
		// this would clean up a lot with a custom exception like HttpBadRequestException
		// and an exception mapper to handle it.
		
		try {
			variant = (variantString == null) ? null : Variant.valueOf(variantString);
		} catch (IllegalArgumentException e) {
			String msg = String.format("The variant %s is not supported. Must be one of %s.", 
					variantString, 
					Arrays.asList(Variant.values()));
			Response response = Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
			throw new WebApplicationException(response);
		}
		
		if (variant == null || Variant.summary == variant) {
			return new PubUser(self, parent, this.username, this.realname);

		} else {
			String msg = String.format("The variant %s is not yet supported.", variantString);
			Response response = Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
			throw new WebApplicationException(response);
		}
	}
}
