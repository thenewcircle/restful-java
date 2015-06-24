package chirp.model;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * Entity representing a user of the "chirp" service. A user logically owns a
 * collection of chirps, indexed by id.
 */
@XmlRootElement
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private  String username;
	private  String realname;
	private  final Map<ChirpId, Chirp> chirps = new TreeMap<ChirpId, Chirp>();
	
	public User() {}
	
	public User(String username, String realname) {
		this.username = username;
		this.realname = realname;
	}

	@XmlElement
	public String getUsername() {
		return username;
	}

	@XmlElement
	public String getRealname() {
		return realname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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
		if (chirps.containsKey(chripId))
			throw new DuplicateEntityException();

		Chirp chirp = new Chirp(chripId, content, this);
		chirps.put(chripId, chirp);
		return chirp;
	}
	
	@XmlTransient
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
