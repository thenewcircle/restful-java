package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest {



	@Test
	public void addUserSuccess() {
		Response response = addStudentUser();
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());
		assertEquals("http://localhost:9998/users/student", response
				.getLocation().toString());
	}

	@Test
	public void addSameUserTwiceFails() {
		addUserSuccess();
		Response response = addStudentUser();
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),
				response.getStatus());
		assertNotNull(response.getEntity());
	}

	@Test
	public void getCreatedUser() {
		addStudentUser();
		UserRepresentation user = target("/users/student").request(
				MediaType.APPLICATION_JSON).get(UserRepresentation.class);
		assertNotNull(user);
		assertEquals("student", user.getUsername());
	}

}
