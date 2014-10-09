package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest {

	private UserRepository userRepository = UserRepository.getInstance();
	
	@Before
	public void testInit() {
		userRepository.clear();
	}

	private Response createUser(String username, String realname,
			Response.Status expectedResponse) {

		Form userForm = new Form().param("realname", realname).param(
				"username", username);
		
		log.info("Created user {} with realname {} via POST", username, realname);

		return postFormData("users", userForm, expectedResponse);

	}

	private void createUserSuccess(MediaType readAcceptHeader) {

		Response response = createUser("gordonff", "Gordon Force",
				Response.Status.CREATED);

		// You wan't to an object from the server -- User
		// the entity to read is in the previous response's location header
		
		response = getEntity(response.getLocation(),
				readAcceptHeader, Response.Status.OK);
		
		verifyLinkHeaderExists("up", readAcceptHeader, response);
		verifyLinkHeaderExists("self", readAcceptHeader, response);
		verifyLinkHeaderExists("related", readAcceptHeader, response); // no posts created yet

		log.info("Read user entity from the resposne");
		UserRepresentation user = readEntity(response, UserRepresentation.class);

		assertNotNull(user);
		assertEquals("gordonff", user.getUsername());
		assertEquals("Gordon Force", user.getRealname());

	}

	@Test
	public void createUserSuccessWithJSONVerify() {
		log.info("Test: create user success with JSON Verify");
		createUserSuccess(MediaType.APPLICATION_JSON_TYPE);
	}

	@Test
	public void createUserSuccessWithXMLVerify() {
		log.info("Test: create user success with XML Verify");
		createUserSuccess(MediaType.APPLICATION_XML_TYPE);
	}

	@Test
	public void createDuplicateUserFail() {
		log.info("Test: Adding the same user twice should fail with a FORBIDDEN (403) on the second add request.");
		createUser("gordonff", "Gordon Force", Response.Status.CREATED);
		createUser("gordonff", "Gordon Force", Response.Status.FORBIDDEN);
	}

	@Test
	public void createUserCollectionSuccess() {

		log.info("Test: Creating a collection of two unique users should succeed.");
		createUser("gordonff", "Gordon Force", Response.Status.CREATED);
		createUser("colef", "Cole Force", Response.Status.CREATED);

		for (UserRepresentation user : readEntity("/users",
				MediaType.APPLICATION_JSON_TYPE,
				UserCollectionRepresentation.class).getUsers()) {

			getHead(user.getSelf(), MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
		}
	}

	@Test
	public void verifyUserCollectionLinkHeaders() {

		log.info("Test: Creating a collection of three users should succeed with link header verification on location only.");
		
		createUser("gordonff", "Gordon Force", Response.Status.CREATED);
		createUser("colef", "Cole Force", Response.Status.CREATED);
		createUser("maddief", "Maddie Force", Response.Status.CREATED);

		Response response = getHead("/users", MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
		verifyLinkHeaderExists("self", MediaType.APPLICATION_JSON_TYPE, response);
		verifyLinkHeaderExists("first", MediaType.APPLICATION_JSON_TYPE, response);
		verifyLinkHeaderExists("last", MediaType.APPLICATION_JSON_TYPE, response);

	}

}
