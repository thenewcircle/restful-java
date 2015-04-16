package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {
	
	@After
	public void tearDown() {
		UserRepository.getInstance().clear();
	}

	@Test
	public void createUserSuccess() {
		Form userForm = new Form().param("username", "bob").param("realname",
				"Bob Smith");
		Response response = target("/users").request().post(
				Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());
		assertNotNull(response.getLocation());

		// a subsequent head request should return a 200
		response = target(response.getLocation().getPath()).request().head();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void headUserDoesNotExistFails() {
		Response response = target("/users/doesnotexist").request().head();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

	}

	@Test
	public void getUserDoesNotExistFails() {
		Response response = target("/users/doesnotexist").request().get();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(),
				response.getStatus());
		assertNotNull(response.getEntity());

	}

	@Test
	public void createUserTwiceFails() {
		Form userForm = new Form().param("username", "bob").param("realname",
				"Bob Smith");
		Response response = target("/users").request().post(
				Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());
		assertNotNull(response.getLocation());

		response = target("/users").request().post(Entity.form(userForm)); // do
																			// it
																			// again
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());
	}
}
