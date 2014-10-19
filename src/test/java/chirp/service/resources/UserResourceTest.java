package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	private UserRepository users = UserRepository.getInstance();

	@Before
	public void clearRepository() {
		users.clear();
	}

	private Response createUser() {
		return target("/users").request().post(
				Entity.form(new Form().param("realname", "Bob Student").param(
						"username", "student")));
	}

	private void verifyCreatedUser() {
		assertEquals(Response.Status.CREATED.getStatusCode(), createUser()
				.getStatus());
		assertNotNull(users.getUser("student"));
	}

	@Test
	public void createUserWithPOSTSuccess() {
		verifyCreatedUser();
	}

	@Test
	public void createDuplicateUserFailsWithFORBIDDEN() {
		verifyCreatedUser();
		assertEquals(Response.Status.FORBIDDEN.getStatusCode(), createUser()
				.getStatus());
	}

}
