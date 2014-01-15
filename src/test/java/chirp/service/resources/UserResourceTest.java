package chirp.service.resources;

import java.util.Collection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import chirp.model.User;
import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	final private UserRepository userRepository = UserRepository.getInstance();
	final private EntityClient<User> uc = new UserResourceClient(this);

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
		uc.createWithHeadLocationVerify(MediaType.APPLICATION_XML_TYPE);
	}

	/**
	 * Use this method to verify that creating the same user twice in an empty
	 * repository will return an HTTP Response Status Code Forbidden (403) in
	 * response the second create request. The first create request must pass.
	 */
	@Test
	public void createTwoUsersFail() {
		createUserSuccess();
		uc.createWithStatus(Status.FORBIDDEN);
		Collection<User> users = uc.getAll(MediaType.APPLICATION_XML_TYPE);
		assertEquals(1,users.size());

	}

	/**
	 * Use this method to verify that request a user that does not exist results
	 * in an HTTP response with a FORBIDDEN (404) status code.
	 */
	@Test
	public void getUserNotFound() {
		uc.getWithStatus(UriBuilder.fromPath("/users/gordonff").build(),
				MediaType.APPLICATION_XML_TYPE, Status.NOT_FOUND);
	}

	@Test
	public void getNewlyCreatedUserMarshalledAsXML() {
		uc.createWithGetLocationVerify(MediaType.APPLICATION_XML_TYPE);
	}

	@Test
	public void getNewlyCreatedUserMarshalledAsJSON() {
		uc.createWithGetLocationVerify(MediaType.APPLICATION_JSON_TYPE);
	}

}
