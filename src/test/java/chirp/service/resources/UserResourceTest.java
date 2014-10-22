package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;
import org.junit.Test;

public class UserResourceTest extends JerseyResourceTest {
	
	@Test
	public void createUser() {
		
		// create a form
		Form user = new Form().param("realname", "Bob Student").param("username", "student");
		
		// send the form as post
		Response response = target("/users").request().post(Entity.form(user));
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertNotNull(response.getLocation());
		
	}

}
