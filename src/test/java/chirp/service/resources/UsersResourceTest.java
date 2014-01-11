package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Test;

import chirp.model.UserRepository;

public class UsersResourceTest extends JerseyResourceTest<UsersResource> {
	
	private UserRepository users = UserRepository.getInstance();

	@Test
	public void createUserWithPOSTSuccess() {
		Form user = new Form().param("realname","Gordon Force").param("username","gordonff");
		Response response = target("/users").request().post(Entity.form(user));
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertNotNull(users.getUser("gordonff"));
	}

}
