package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Test;

public class UserResourceTest extends JerseyResourceTest {

	@After
	public void tearDown() {
		clearUserRepository();
	}

	@Test
	public void createUserSuccess() {

		Response response = createUserBobStudent(Response.Status.CREATED);
		assertNotNull(response.getLocation());

		getHead(response.getLocation(), MediaType.APPLICATION_JSON_TYPE,
				Response.Status.OK);
	}

	@Test
	public void headUserDoesNotExistFails() {
		
		getHead("/users/doesnotexist", MediaType.APPLICATION_JSON_TYPE,
				Response.Status.NOT_FOUND);


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
