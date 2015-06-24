package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UsersResourceTest extends JerseyResourceTest {
	
	@Before
	public void testSetup() {
		UserRepository.getInstance().clear();
	}
	
	@Test
	public void createUserSuccess() {
		
		Form form = new Form().param("realname", "Bob Student").param("username", "student");
		
		Response response = target("/users").request().post(Entity.form(form));
		
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("http://localhost:9998/users/student", response.getLocation().toString());
		
	}
	
	@Test
	public void createSameUserFails() {
		
		Form form = new Form().param("realname", "Bob Student").param("username", "student");
		
		Response response = target("/users").request().post(Entity.form(form));
		
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("http://localhost:9998/users/student", response.getLocation().toString());
		
		form = new Form().param("realname", "Bob Student").param("username", "student");	
		response = target("/users").request().post(Entity.form(form));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

		
	}

}
