package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;

public class UsersResourceTest extends JerseyResourceTest {

	private Response createBobStudent(Response.Status expectedStatus) {
		Response response = target("/users").request().post(
				Entity.form(new Form().param("realname", "Bob Student").param(
						"username", "student")));

		assertEquals(expectedStatus.getStatusCode(), response.getStatus());

		return response;
	}

	@Before
	public void testSetup() {
		UserRepository.getInstance().clear();
	}

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

		User user = target("/users").path("student").request()
				.accept(mediaType).get(User.class);
		assertNotNull(user);
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