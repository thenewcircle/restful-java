package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest<UserResource> {

	private UserRepository userRepository = UserRepository.getInstance();

	@Before
	public void testInit() {
		userRepository.clear();
	}

	@Test
	public void createUserSuccess() {
		Form userForm = new Form().param("realname", "Gordon Force").param(
				"username", "gordonff");
		Response response = target("/user").request().post(
				Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());
		User actual = userRepository.getUser("gordonff");
		assertEquals("gordonff", actual.getUsername());

		// You wan't to an object from the server -- User
		// the entity to read is in the previous response's location header
		UserRepresentation userRead = target(response.getLocation().getPath())
				.request().get(UserRepresentation.class);
		assertEquals("gordonff", userRead.getUsername());

	}

	@Test
	public void createDuplicateUserFail() {
		Form userForm = new Form().param("realname", "Gordon Force").param(
				"username", "gordonff");

		Response response = target("/user").request().post(
				Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		response = target("/user").request().post(Entity.form(userForm));
		assertEquals(Response.Status.FORBIDDEN.getStatusCode(),
				response.getStatus());

		// You wan't to an object from the server -- User
		// the entity to read is in the previous response's location header
		// User userRead =
		// target(response.getLocation().getPath()).request().get(User.class);

	}

}
