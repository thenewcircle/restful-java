package chirp.model;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


/**
 * Entity representing a user of the "chirp" service. A user logically owns a
 * collection of chirps, indexed by id.
 */
public class User implements Serializable {

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
		ChirpId id = new ChirpId();
		if (chirps.containsKey(id))
			throw new DuplicateEntityException();

		Chirp chirp = new Chirp(id, content, this);
		chirps.put(id, chirp);
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

}
