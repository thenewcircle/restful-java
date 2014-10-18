package chirp.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Entity representing a chirp id. This isn't much more than a wrapper
 * class for a String.
 */
public class ChirpId implements Comparable<ChirpId>, Serializable {

	private static final long serialVersionUID = 1L;
	// hack fix for id resolution not accommodating fast computers.
	private static final AtomicLong baseId = new AtomicLong(
			System.currentTimeMillis());

	private final String id;

	public ChirpId(String id) {
		this.id = id;
	}

	public ChirpId() {
		this.id = Long.toString(baseId.getAndIncrement());
	}

	@Override
	public int compareTo(ChirpId other) {
		return id.compareTo(other.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
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
		ChirpId other = (ChirpId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id;
	}

}
