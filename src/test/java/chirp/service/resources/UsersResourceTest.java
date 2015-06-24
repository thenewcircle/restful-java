package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

import org.junit.Test;

public class UsersResourceTest extends JerseyResourceTest {
	
	@Test
	public void createUserSuccess() {
		
		Form form = new Form().param("realname", "Bob Student").param("username", "student");
		
		Response response = target("/users").request().post(Entity.form(form));
		
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("http://localhost:9998/users/student", response.getLocation().toString());
		
	}
}
