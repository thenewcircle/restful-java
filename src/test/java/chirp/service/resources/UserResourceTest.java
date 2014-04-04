package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest<UserResource> {

	private UserRepository userRepository = UserRepository.getInstance();

	@Before
	public void testInit() {
		userRepository.clear();
	}

	private Response createUser(String username, String realname,
			Response.Status expectedResponse) {

		Form userForm = new Form().param("realname", realname).param(
				"username", username);

		return postFormData("user", userForm, expectedResponse);

	}

	private void createUserSuccess(MediaType readAcceptHeader) {

		Response response = createUser("gordonff", "Gordon Force",
				Response.Status.CREATED);

		// You wan't to an object from the server -- User
		// the entity to read is in the previous response's location header

		UserRepresentation user = readEntity(response.getLocation().getPath(),
				MediaType.APPLICATION_JSON_TYPE, UserRepresentation.class);

		assertNotNull(user);
		assertEquals("gordonff", user.getUsername());
		assertEquals("Gordon Force", user.getRealname());
	}

	@Test
	public void createUserSuccessWithJSONVerify() {
		createUserSuccess(MediaType.APPLICATION_JSON_TYPE);
	}

	@Test
	public void createUserSuccessWithXMLVerify() {
		createUserSuccess(MediaType.APPLICATION_XML_TYPE);
	}

	@Test
	public void createDuplicateUserFail() {
		createUser("gordonff", "Gordon Force", Response.Status.CREATED);
		createUser("gordonff", "Gordon Force", Response.Status.FORBIDDEN);
	}

	@Test
	public void readUserCollectionSuccess() {

		createUser("gordonff", "Gordon Force", Response.Status.CREATED);
		createUser("colef", "Cole Force", Response.Status.CREATED);

		for (UserRepresentation user : readEntity("/user",
				MediaType.APPLICATION_JSON_TYPE,
				UserCollectionRepresentation.class).getUsers()) {

			getHead(UriBuilder.fromUri(user.getSelf()).build().toString(),
					MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
		}
	}

}
