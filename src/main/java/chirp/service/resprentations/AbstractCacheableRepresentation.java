package chirp.service.resprentations;

import java.util.Date;

import javax.ws.rs.core.EntityTag;
import javax.xml.bind.annotation.XmlTransient;

import chirp.model.AbstractModelEntity;
import chirp.service.caching.CacheableResource;

/**
 * Extend from this class when creating cacheable representation classes with
 * support for HTTP caching using the CacheResponseBuilder class. Extended
 * classes should use all class member instance fields when overriding their
 * hashcode and equals methods.
 * 
 */
public class AbstractCacheableRepresentation implements CacheableResource {

	private Date lastModificationTime;

	protected AbstractCacheableRepresentation() {
	}

	protected AbstractCacheableRepresentation(
			AbstractModelEntity modelEntity) {
		this.lastModificationTime = modelEntity.getLastModificationTime();
	}

	@XmlTransient
	@Override
	public EntityTag getEntityTag() {
		return new EntityTag(Integer.toHexString(this.hashCode()));
	}

	@XmlTransient
	@Override
	public Date getLastModificationTime() {
		return lastModificationTime;
	}

	public void setLastModificationTime(final Date lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}

}
