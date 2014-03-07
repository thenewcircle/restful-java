package chirp.service.representations;

import java.net.URI;

/**
 * Use this interface to expose common behaviors / members of all entity representations.
 * @author Gordon Force
 */
public interface Representation {
	
	/**
	 * Obtain a representation's <em>self</em> URI
	 * @return
	 */
	URI getSelf();

}
