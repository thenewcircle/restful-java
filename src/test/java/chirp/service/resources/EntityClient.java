package chirp.service.resources;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/** Implement this interface to separate prefdefined interactions with a resource and 
 *  a test case.
 *  
 * @author Gordon Force
 *
 * @param <E> The type of entity the resource uses.
 */
public interface EntityClient<E> {
	
	/**
	 * Use this method to test if the create entity request returns the status
	 * expected.
	 * 
	 * @param expectedStatus
	 *            -- what the server should return in response to this request.
	 * @return the response object from the POST request used to create the
	 *         user.
	 */
	Response createWithStatus(Status expectedStatus);
	
	E createWithGetLocationVerify(MediaType mediaType);

	Response createWithHeadLocationVerify(MediaType mediaType);
	
	/**
	 * Use this method to test if the get entity request returns the status
	 * expected.
	 * 
	 * @param location
	 *            -- the URI of the user to retrieve. This method only uses the
	 *            path component of the URI.
	 * @param expectedStatus
	 *            -- what the server should return in response to this request.
	 * @return the response object from the GET request used to retrieve the
	 *         user.
	 */
	Response getWithStatus(URI location, MediaType mediaType, Status status);
	
	E get(URI location, MediaType mediaType);
	
	Collection<E> getAll(MediaType mediaType);
	
}
