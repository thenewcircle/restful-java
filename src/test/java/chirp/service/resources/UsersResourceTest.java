package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import chirp.service.representations.UserRepresentation;

public class UsersResourceTest extends JerseyResourceTest {


	@Test
	public void createUserSuccess() {

		Response response = createBobStudent(Response.Status.CREATED);
		assertEquals("http://localhost:9998/users/student", response
				.getLocation().toString());

	}

	@Test
	public void createSameUserFails() {

		createBobStudent(Response.Status.CREATED);
		createBobStudent(Response.Status.BAD_REQUEST);
	}

	private void createAndGetUserSuccess(String mediaType) {
		createBobStudent(Response.Status.CREATED);

		Response response = target("/users").path("student").request()
				.accept(mediaType).get();
		assertNotNull(response);
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		
		UserRepresentation user = response.readEntity(UserRepresentation.class);
		assertEquals("student", user.getUsername());
	}

	@Test
	public void createAndGetUserSuccessAsXML() {
		createAndGetUserSuccess(MediaType.APPLICATION_XML);
	}

	@Test
	public void createAndGetUserSuccessAsJSON() {
		createAndGetUserSuccess(MediaType.APPLICATION_JSON);

	}

}