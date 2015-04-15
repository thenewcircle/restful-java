package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserResourceTest extends JerseyResourceTest {
	
	@Test
	public void createUserSuccess() {
		Form userForm = new Form().param("username","bob").param("realname", "Bob Smith");
		Response response = target("/users").request().post(Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		assertNotNull(response.getLocation());
		
		// a subsequent head request should return a 200
		response = target(response.getLocation().getPath()).request().head();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

}
