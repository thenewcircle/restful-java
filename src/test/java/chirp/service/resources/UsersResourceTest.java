package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;

public class UsersResourceTest extends JerseyResourceTest<UsersResource> {
	
	@Before 
	public void testSetup() {
		UserRepository.getInstance().clear();
	}

	@Test
	public void createUserWithPOSTSucess() {
		Form user = new Form().param("realname", "Gordon Force").param(
				"username", "gordonff");
		Response response = target("/users").request().post(Entity.form(user));

		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		assertEquals(
				target("/users/").getUriBuilder().path("gordonff").build(),
				response.getLocation());
	}
	
	
	@Test
	public void createUserSameUserWithPOSTFailure() {
		Form user = new Form().param("realname", "Gordon Force").param(
				"username", "gordonff");
		Response response = target("/users").request().post(Entity.form(user));

		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		assertEquals(
				target("/users/").getUriBuilder().path("gordonff").build(),
				response.getLocation());
		
		response = target("/users").request().post(Entity.form(user));

		assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());

	}
	
	@Test
	public void getUserAsJSON() {
		
		Form userAsForm = new Form().param("realname", "Gordon Force").param(
				"username", "gordonff");
		Response response = target("/users").request().post(Entity.form(userAsForm));

		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		User user = target(response.getLocation().getPath()).request().get(User.class);
		
		assertNotNull(user);
		
	
	}


}
