package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import chirp.service.representations.EntityRepresentation;

/** Implement this interface to separate interactions with a resource from the 
 * sequencing of iteractinos that make up test case.

 *  
 * @author Gordon Force
 *
 * @param <E> The type of entity the resource uses.
 */
public interface EntityClient<E extends EntityRepresentation,C extends EntityRepresentation> {
	
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
	
	C getAll(MediaType mediaType);
	
}
