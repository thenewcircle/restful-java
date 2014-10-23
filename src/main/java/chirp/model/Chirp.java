package chirp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity representing a "chirp" posted by a user. To properly create a Chirp, call
 * User.createChirp().
 */
public class Chirp implements Serializable {

    private static final long serialVersionUID = 1L;

    private final ChirpId id;
    private final String content;
    private final User user;
    private final Date lastModified;

    public Chirp(ChirpId id, String content, User user) {
        this.id = id;
        this.content = content;
        this.user = user;
        lastModified = new Date();
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

    public Date getLastModified() {
        return lastModified;
    }

    public int strongHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chirp other = (Chirp) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Chirp [id=" + id + ", content=" + content + "]";
    }

}
