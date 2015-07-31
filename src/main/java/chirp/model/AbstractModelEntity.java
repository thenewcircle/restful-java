package chirp.model;

import java.util.Date;

/**
 * Use this class to define common class members for all model objects. The
 * lastModidicationTime field allows support for client caching of responses as
 * the time stamp to use in representation objects.
 * 
 * @author gordon
 * 
 */
public class AbstractModelEntity {

	private Date lastModificationTime;

	public Date getLastModificationTime() {
		return lastModificationTime;
	}

	public void setLastModificationTime(Date lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}

}
