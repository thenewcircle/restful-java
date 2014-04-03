package chirp.service.resources;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;
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
		// assertEquals("gordonff", userRead.getUsername());

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

}
