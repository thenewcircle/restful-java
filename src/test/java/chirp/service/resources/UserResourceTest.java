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
	
	private Response createUserWithExpectedStatusCode(Response.Status expectedStatusCode) {
		// create a form
		Form user = new Form().param("realname", "Bob Student").param("username", "student");
		
		// send the form as post
		Response response = target("/users").request().post(Entity.form(user));
		assertEquals(expectedStatusCode.getStatusCode(), response.getStatus());
		return response;
	
	}
	
	@Test
	public void createUser() {
		
		Response response = createUserWithExpectedStatusCode(Response.Status.CREATED);
		assertNotNull(response.getLocation());
		
	}
	
	@Test
	public void createDuplicateUserFailsWithForbidden() {
		
		createUserWithExpectedStatusCode(Response.Status.CREATED);
		createUserWithExpectedStatusCode(Response.Status.FORBIDDEN);
		
	}

}
