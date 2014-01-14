package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class UserResourceTest extends JerseyResourceTest<UserResource> {

	@Test
	public void createUserSuccess() {

		Form form = new Form().param("username", "gordonff").param("realname",
				"Gordon Force");
		Response response = target("/users").request().post(Entity.form(form));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());
	}

}
