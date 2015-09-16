package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.NoSuchEntityException;
import chirp.model.User;
import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	private static final String APPLICATION_URL = "http://localhost:9998";

	@Before
	public void resetAndSeedRepository() {
		UserRepository database = UserRepository.getInstance();
		database.clear();
		database.createUser("maul", "Darth Maul");
		database.createUser("luke", "Luke Skywaler");
		database.createUser("vader", "Darth Vader");
		database.createUser("yoda", "Master Yoda");
		database.getUser("yoda").createChirp("Do or do not.  There is no try.", "wars01");
		database.getUser("yoda").createChirp("Fear leads to anger, anger leads to hate, and hate leads to suffering.", "wars02");
		database.getUser("vader").createChirp("You have failed me for the last time.", "wars03");
	}

	@Test
	public void getUserAsText() {
		Response response = target("/users").path("yoda").request().header("Accept", MediaType.TEXT_PLAIN).get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("User [username=yoda]", text);
	}
	
	@Test
	public void getUserAsJson() {
		Response response = target("/users").path("yoda").request().header("Accept", MediaType.APPLICATION_JSON).get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());
		User yoda = response.readEntity(User.class);
		assertEquals("yoda", yoda.getUsername());
		assertEquals("Master Yoda", yoda.getRealname());
	}

	@Test
	public void createUserWithJsonPut() {
		
		// setup
		String username = "john";
		String realname = "John Doe";
		User john = new User(username,realname);
		Entity<User> entity = Entity.json(john);
		
		// act
		Response response = target("/users").path(username).request().accept(MediaType.APPLICATION_JSON).put(entity);

		// assert status code
		assertEquals(201, response.getStatus());

		// assert header location
		String location = response.getHeaderString("Location");
		assertEquals(APPLICATION_URL + "/users/" + username, location);

		// assert user actually created in the user repository
		UserRepository repository = UserRepository.getInstance();
		try {
			User user = repository.getUser(username);
			assertEquals(username, user.getUsername());
			assertEquals(realname, user.getRealname());
		} catch (NoSuchEntityException nsee) {
			fail("Should have found newly created user " + username);
		}
		
	}
	
	@Test
	public void createUserWithForm() {

		// setup
		String username = "john";
		String realname = "John Doe";
		Form form = new Form().param("realname", realname);
		Entity<Form> entity = Entity.form(form);

		// act
		Response response = target("/users").path(username).request().accept(MediaType.TEXT_PLAIN).put(entity);

		// assert status code
		assertEquals(201, response.getStatus());

		// assert header location
		String location = response.getHeaderString("Location");
		assertEquals(APPLICATION_URL + "/users/" + username, location);

		// assert user actually created in the user repository
		UserRepository repository = UserRepository.getInstance();
		try {
			User user = repository.getUser(username);
			assertEquals(username, user.getUsername());
			assertEquals(realname, user.getRealname());
		} catch (NoSuchEntityException nsee) {
			fail("Should have found newly created user " + username);
		}

	}

	@Test
	public void deleteUser() {
		Response response = target("/users").path("yoda").request().delete();
		assertEquals(204, response.getStatus());
	}

	@Test
	public void createDuplicateUser() {

		// setup
		String username = "john";
		String realname = "John Doe";
		Form form = new Form().param("realname", realname);
		Entity<Form> entity = Entity.form(form);

		// act
		Response response = target("/users").path(username).request().accept(MediaType.TEXT_PLAIN).put(entity);

		// assert status code
		assertEquals(201, response.getStatus());

		// assert header location
		String location = response.getHeaderString("Location");
		assertEquals(APPLICATION_URL + "/users/" + username, location);

		// assert user actually created in the user repository
		UserRepository repository = UserRepository.getInstance();
		try {
			User user = repository.getUser(username);
			assertEquals(username, user.getUsername());
			assertEquals(realname, user.getRealname());
		} catch (NoSuchEntityException nsee) {
			fail("Should have found newly created user " + username);
		}

		// act again
		Response response2 = target("/users").path(username).request().accept(MediaType.TEXT_PLAIN).put(entity);

		// assert status code
		assertEquals(403, response2.getStatus());

	}

	@Test
	public void deleteNonExistingUser() {
		Response response = target("/users").path("nobody").request().delete();
		assertEquals(404, response.getStatus());
	}

	@Test
	public void nonSupportedMethodShouldReturn405() {
		Response response = target("/users").path("yoda").request().trace();
		int status = response.getStatus();
		assertEquals(405, status);
		String body = response.readEntity(String.class);
		assertEquals("HTTP 405 Method Not Allowed", body);
	}

	@Test
	public void createUserWithWhitespacesShouldReturn400() {
		Response response = target("/users").path("  yoda  ").request().get();
		assertEquals(400, response.getStatus());
	}

	@Test
	public void createUserWithSpecialCharactersShouldReturn400() {
		Response response = target("/users").path("++$$yoda$$\n").request().get();
		assertEquals(400, response.getStatus());
	}

}
