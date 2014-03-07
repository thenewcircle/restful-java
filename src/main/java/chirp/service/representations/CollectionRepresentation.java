package chirp.service.representations;

import java.util.Collection;

/**
 * Implementing this class enables behaviors common to class's who contains
 * collections of entity representations.
 * 
 * @author Gordon Force
 * 
 * @param <P> the type of class within the collection of representations.
 */
public interface CollectionRepresentation<P extends Representation> extends Representation {

	/**
	 * 
	 * @return the collection of to representations
	 */
	public abstract Collection<P> get();

}