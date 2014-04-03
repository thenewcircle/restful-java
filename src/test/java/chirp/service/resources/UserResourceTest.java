package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest<UserResource> {

	private UserRepository userRepository = UserRepository.getInstance();

	@Before
	public void testInit() {
		userRepository.clear();
	}

	private void createUserSuccess(MediaType readAcceptHeader) {

		Form userForm = new Form().param("realname", "Gordon Force").param(
				"username", "gordonff");
		Response response = target("/user").request().post(
				Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());
		User actual = userRepository.getUser("gordonff");
		assertEquals("gordonff", actual.getUsername());

		// You wan't to an object from the server -- User
		// the entity to read is in the previous response's location header
		UserRepresentation userRead = target(response.getLocation().getPath())
				.request().accept(readAcceptHeader)
				.get(UserRepresentation.class);
		assertNotNull(userRead);

		// Temporary
		assertEquals("gordonff", userRead.getUsername());

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
		Form userForm = new Form().param("realname", "Gordon Force").param(
				"username", "gordonff");

		Response response = target("/user").request().post(
				Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		response = target("/user").request().post(Entity.form(userForm));
		assertEquals(Response.Status.FORBIDDEN.getStatusCode(),
				response.getStatus());

	}

	@Test
	public void readUserCollectionSuccess() {

		Form userForm = new Form().param("realname", "Gordon Force").param(
				"username", "gordonff");
		Response response = target("/user").request().post(
				Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		userForm = new Form().param("realname", "Cole Force").param("username",
				"colef");
		response = target("/user").request().post(Entity.form(userForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		UserCollectionRepresentation users = target("/user").request()
				.accept(MediaType.APPLICATION_JSON)
				.get(UserCollectionRepresentation.class);

		for (UserRepresentation user : users.getUsers()) {

			/*
			 * Verify using GET Requests UserRepresentation userRead = target(
			 * UriBuilder.fromUri(user.getSelf()).build().toString())
			 * .request().accept(MediaType.APPLICATION_JSON)
			 * .get(UserRepresentation.class);
			 * 
			 * if ((userRead.getUsername().equals("gordonff") == false) &&
			 * (userRead.getUsername().equals("colef") == false)) { fail("User "
			 * + userRead.getUsername() + "not expected"); }
			 */

			Response headResponse = target(
					UriBuilder.fromUri(user.getSelf()).build().toString())
					.request().head();
			
			assertEquals(200,headResponse.getStatus());

		}
	}

	/*
	 * target(uriPath) .request(mediaType).get();
	 * 
	 * assertStatusEquals(Status.OK, response);
	 * assertTrue(response.hasEntity());
	 * 
	 * // @SuppressWarnings("unchecked") final CP collection = (CP)
	 * response.readEntity(entityCollectionClass); assertNotNull(collection);
	 * assertNotNull(collection.getSelf()); assertEquals(uriPath,
	 * collection.getSelf().toString());
	 */
}
