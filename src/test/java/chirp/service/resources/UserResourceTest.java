package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest<UserResource> {

	final private UserRepository userRepository = UserRepository.getInstance();

	/**
	 * Execute this method before every <em>@Test</em> method to insure the user
	 * repository is empty (expected state).
	 */
	@Before
	public void clearRepository() {
		userRepository.clear();
	}

	/**
	 * Use this method to test if the create user request returns the status
	 * expected.
	 * 
	 * @param expectedStatus
	 *            -- what the server should return in response to this request.
	 * @return the response object from the POST request used to create the
	 *         user.
	 */
	private Response createUserWithStatus(final Status expectedStatus) {
		final Form form = new Form().param("username", "gordonff").param(
				"realname", "Gordon Force");
		final Response response = target("/users").request().post(
				Entity.form(form));
		assertStatusEquals(expectedStatus, response);
		return response;
	}

	/**
	 * Use this method to test if the get user request returns the status
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
	private Response getUserWithStatus(final URI location,
			final Status expectedStatus) {
		final Response response = target(location.getPath()).request().get();
		assertStatusEquals(expectedStatus, response);
		return response;
	}

	/**
	 * Use this method to verify one can create a user in an empty repository
	 * and that the user exists after a subsequent get request.
	 */
	@Test
	public void createUserSuccess() {
		final Response response = createUserWithStatus(Status.CREATED);
		getUserWithStatus(response.getLocation(), Status.OK);
	}

	/**
	 * Use this method to verify that creating the same user twice in an empty
	 * repository will return an HTTP Response Status Code Forbidden (403) in
	 * response the second create request. The first create request must pass.
	 */
	@Test
	public void createTwoUsersFail() {
		createUserSuccess();
		createUserWithStatus(Status.FORBIDDEN);
	}

	/**
	 * Use this method to verify that request a user that does not exist results
	 * in an HTTP response with a FORBIDDEN (404) status code.
	 */
	@Test
	public void getUserNotFound() {
		getUserWithStatus(UriBuilder.fromPath("/users/gordonff").build(),
				Status.NOT_FOUND);
	}

	User getNewlyCreatedUser(MediaType mediaType) {

		// create the user
		final Response response = createUserWithStatus(Status.CREATED);

		// get the user with the following client statement
		final User user = target().path(response.getLocation().getPath()).request()
				.accept(mediaType).get(User.class);
		assertEquals("gordonff", user.getUsername());
		
		return user;
	}

	@Test
	public void getNewlyCreatedUserMarshalledAsXML() {
		getNewlyCreatedUser(MediaType.APPLICATION_XML_TYPE);
	}

	@Test
	public void getNewlyCreatedUserMarshalledAsJSON() {
		getNewlyCreatedUser(MediaType.APPLICATION_JSON_TYPE);
	}


}
