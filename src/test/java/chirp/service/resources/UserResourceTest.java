package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest<UsersResource> {
	
	@Before
	public void setup() {
		UserRepository.getInstance().clear();
	}

	@Test
	public void createUser() {
		
		Form form = new Form().param("username","gordonff").param("realname","Gordon Force");
		Response response = target("users").request().post(Entity.form(form));
		assertNotNull(response);
		assertEquals(Status.CREATED.getStatusCode(),response.getStatus());
		assertEquals("http://localhost:9998/users/gordonff", response.getHeaderString("Location"));
		assertNotNull(UserRepository.getInstance().getUser("gordonff"));
	}
	
	@Test
	public void createSameUserTwiceFails() {
		createUser();
		
		Form form = new Form().param("username","gordonff").param("realname","Gordon Force");
		Response response = target("users").request().post(Entity.form(form));
		assertNotNull(response);
		assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
		
	}

}
