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
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest {

	@Before
	public void clearUserRepository() {
		UserRepository.getInstance().clear();
	}

	private Response createAnyUser(String realname, String username) {
		Form user = new Form().param("realname", realname).param("username",
				username);

		return target("/users").request().post(Entity.form(user));

	}

	private Response createUserWithExpectedStatusCode(
			Response.Status expectedStatusCode) {
		Response response = createAnyUser("Bob Student", "student");
		assertEquals(expectedStatusCode.getStatusCode(), response.getStatus());
		return response;

	}

	@Test
	public void getUsers() {

		createAnyUser("Bob Student", "student");
		createAnyUser("Cole Student", "cole");
		createAnyUser("Jeff Student", "jeff");

		UserCollectionRepresentation ucr = target("/users").request()
				.accept(MediaType.APPLICATION_XML)
				.get(UserCollectionRepresentation.class);
		
		assertEquals(3, ucr.getUsers().size());

	}

	@Test
	public void createUser() {

		Response response = createUserWithExpectedStatusCode(Response.Status.CREATED);
		assertNotNull(response.getLocation());

	}

	@Test
	public void createDuplicateUserFailsWithForbidden() {

		createUserWithExpectedStatusCode(Response.Status.CREATED);
		createUserWithExpectedStatusCode(Response.Status.FORBIDDEN);

	}

	@Test
	public void getUserSuccess() {
		Response response = createUserWithExpectedStatusCode(Response.Status.CREATED);
		UserRepresentation ur = target(response.getLocation().getPath())
				.request().accept(MediaType.APPLICATION_XML_TYPE)
				.get(UserRepresentation.class);
		assertEquals("student", ur.getUsername());
	}

	@Test
	public void userNotFound() {

		Response response = target("/users/blah").request(
				MediaType.APPLICATION_XML_TYPE).get();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(),
				response.getStatus());

	}

}
