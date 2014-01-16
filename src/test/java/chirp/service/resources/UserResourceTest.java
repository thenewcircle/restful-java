package chirp.service.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	final private UserRepository userRepository = UserRepository.getInstance();

	public UserResourceTest() {
		super(UserResource.class);
	}

	/**
	 * Execute this method before every <em>@Test</em> method to insure the user
	 * repository is empty (expected state).
	 */
	@Before
	public void clearRepository() {
		userRepository.clear();
	}

	/**
	 * Use this method to verify one can create a user in an empty repository
	 * and that the user exists after a subsequent get request.
	 */
	@Test
	public void createUserSuccess() {
		testResourceClient.createWithHeadLocationVerify(getDefaultMediaType());
	}

	/**
	 * Use this method to verify that creating the same user twice in an empty
	 * repository will return an HTTP Response Status Code Forbidden (403) in
	 * response the second create request. The first create request must pass.
	 */
	@Test
	public void createTwoUsersFail() {
		createUserSuccess();
		testResourceClient.createWithStatus(Status.FORBIDDEN);
	}

	/**
	 * Use this method to verify that request a user that does not exist results
	 * in an HTTP response with a NOT_FOUND (404) status code.
	 */
	@Test
	public void getUserNotFound() {
		testResourceClient.getWithStatus(UriBuilder.fromPath("/users/gordonff").build(),
				getDefaultMediaType(), Status.NOT_FOUND);
	}

	@Test
	public void getNewlyCreatedUserMarshalledAsXML() {
		testResourceClient
				.createWithHeadLocationVerify(MediaType.APPLICATION_XML_TYPE);
	}

	@Test
	public void getNewlyCreatedUserMarshalledAsJSON() {
		testResourceClient.createWithGetLocationVerify(MediaType.APPLICATION_JSON_TYPE);
	}

}
