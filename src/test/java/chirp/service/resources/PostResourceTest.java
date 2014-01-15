package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;

public class PostResourceTest extends JerseyResourceTest<PostResource> {

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
	private Response createPost(final Status expectedStatus, final String content) {

		UserResourceTest urt = new UserResourceTest();
		User user = urt.getNewlyCreatedUser(MediaType.APPLICATION_XML_TYPE);

		final Form form = new Form()
				.param("content", content);
		final Response response = target("/posts").path(user.getUsername())
				.request().post(Entity.form(form));
		assertStatusEquals(expectedStatus, response);
		return response;
	}
	


	/**
	 * Use this method to verify one can create a user in an empty repository
	 * and that the user exists after a subsequent get request.
	 */
	@Test
	public void createUserSuccess() {
		createPost(Status.CREATED, "this is the first post");
		createPost(Status.CREATED, "this is the second post");
		// System.out.println("All created posts " + getAllPosts());
	}


}
