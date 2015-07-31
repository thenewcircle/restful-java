package chirp.service.caching;

import java.util.Date;

import javax.ws.rs.core.EntityTag;

public interface CacheableResource {

	EntityTag getEntityTag();

	Date getLastModificationTime();

}
