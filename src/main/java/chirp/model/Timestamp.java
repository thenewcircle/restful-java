package chirp.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * Entity representing a chirp timestamp. This isn't much more than a wrapper
 * class for a String.
 */
public class Timestamp implements Comparable<Timestamp>, Serializable {

	private static final long serialVersionUID = 1L;

	private final String timestamp;

	@JsonCreator
	public Timestamp(@JsonProperty("timestamp") String timestamp) {
		this.timestamp = timestamp;
	}

	public Timestamp() {
		this(DateTimeFormat.forPattern("yyyyMMddHHmmss").print(new DateTime()));
	}

	@Override
	public int compareTo(Timestamp other) {
		return timestamp.compareTo(other.timestamp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
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
		Timestamp other = (Timestamp) obj;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return timestamp;
	}

}
