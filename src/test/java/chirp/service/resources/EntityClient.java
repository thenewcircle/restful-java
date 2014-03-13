package chirp.service.resources;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/** Implement this interface to separate predefined interactions with a resource and 
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
	
	/**
	 * Use this method to return the entity created by following the URL 
	 * specified in the POST response.
	 * 
	 * @param mediaType
	 * @return the instance of E created.
	 */
	E createWithGetLocationVerify(MediaType mediaType);

	/**
	 * Use this method to verify the URL returned by POST request is valid by
	 * submitting a subsequent HEAD request.
	 *   
	 * @param mediaType
	 * @return The HTTP response header object for the HEAD request.
	 */
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
	
	/**
	 * Use this method to obtain an resource from a specific URI and with a specific encoding.
	 * @param location the URL for the resource
	 * @param mediaType the encoding for the resource.
	 * @return the resource requested.
	 */
	E get(URI location, MediaType mediaType);
	
	/**
	 * Uset his method to obtain a collection of resources of a given media type. 
	 * @param mediaType
	 * @return
	 */
	Collection<E> getAll(MediaType mediaType);
	
}
