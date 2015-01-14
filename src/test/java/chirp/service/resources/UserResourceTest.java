package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class UserResourceTest extends JerseyResourceTest {
	
	@Test
	public void createUserWithPOSTSuccess() {
		
		Form newUser = new Form().param("realname", "Bob Student").param("username","student");
		Response response = target("/users").request().post(Entity.form(newUser));
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
		
	}

}
