package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest<UsersResource> {

	@Before
	public void setup() {
		UserRepository.getInstance().clear();
	}
	
	private Response createUser(String username, String realname,
			Response.Status expectedResponse) {

		Form userForm = new Form().param("realname", realname).param(
				"username", username);
		
		logger.info("Created user {} with realname {} via POST", username, realname);

		return postFormData("users", userForm, expectedResponse);

	}

	private void createUserSuccess(MediaType readAcceptHeader) {

		Response response = createUser("gordonff", "Gordon Force",
				Response.Status.CREATED);

		// You wan't to an object from the server -- User
		// the entity to read is in the previous response's location header
		
		response = getEntity(response.getLocation(),
				readAcceptHeader, Response.Status.OK);

		logger.info("Read user entity from the resposne");
		User user = readEntity(response, User.class);

		assertNotNull(user);
		assertEquals("gordonff", user.getUsername());
		assertEquals("Gordon Force", user.getRealname());

	}

	@Test
	public void createUserSuccessWithVerify() {
		logger.info("Test: create user success with JSON Verify");
		createUserSuccess(MediaType.APPLICATION_JSON_TYPE);
	}

	@Test
	public void createDuplicateUserFail() {
		logger.info("Test: Adding the same user twice should fail with a FORBIDDEN (403) on the second add request.");
		createUser("gordonff", "Gordon Force", Response.Status.CREATED);
		createUser("gordonff", "Gordon Force", Response.Status.FORBIDDEN);
	}

	@Test
	public void getAllExistingUsers() {
		createUserSuccess(MediaType.APPLICATION_JSON_TYPE);
		createUser("test", "Test User", Response.Status.CREATED);

		@SuppressWarnings("unchecked")
		Collection<User> users = (Collection<User>) target("/users").request(MediaType.APPLICATION_JSON).get(
				Collection.class);

		assertNotNull(users);
		assertEquals(2, users.size());
	}

}
