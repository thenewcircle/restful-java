package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {
	
	@Before 
	public void clearUserRepository() {
		UserRepository.getInstance().clear();
	}
	
	@Test
	public void createUser() {
		
		// create a form
		Form user = new Form().param("realname", "Bob Student").param("username", "student");
		
		// send the form as post
		Response response = target("/users").request().post(Entity.form(user));
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertNotNull(response.getLocation());
		
	}
	
	@Test
	public void createDuplicateUserFailsWithForbidden() {
		
	}

}
