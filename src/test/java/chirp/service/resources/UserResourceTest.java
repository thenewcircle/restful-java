package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserResourceTest extends JerseyResourceTest<UserResource> {

	@Test
	public void createUserSuccess() {
		Form userForm = new Form().param("realname","Gordon Force").param("username", "gordonff");
		Response response = target("/user").request().post(Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		
		// validate user was created by a head or get against the location uri
	}
	

}
