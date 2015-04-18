package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest {

	@Test
	public void createUserWithPOSTSuccess() {
		createUserBobStudent(Response.Status.CREATED);
	}

	@Test
	public void createSameUserTwiceWithPOSTFailure() {
		createUserBobStudent(Response.Status.CREATED);

		Response response = createUserBobStudent(Response.Status.BAD_REQUEST);

		String message = readEntity(response, String.class);

		assertEquals("User student already exists.", message);
	}

	@Test
	public void getCreatedUserSuccess() {
		Response response = createUserBobStudent(Response.Status.CREATED);

		response = getHead(response.getLocation().getPath(),
				MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);

		verifyLinkHeaderExists("self", MediaType.APPLICATION_JSON_TYPE,
				response);
	}

	@Test
	public void verifyNotModifiedReturnedOnSecondGet() {
		Response response = createUserBobStudent(Response.Status.CREATED);

		URI location = response.getLocation();
		response = getEntity(location, MediaType.APPLICATION_JSON_TYPE,
				Response.Status.OK);
		
		UserRepresentation userRep = readEntity(response, UserRepresentation.class);
		assertEquals("student", userRep.getUsername());
		
		getEntity(location, MediaType.APPLICATION_JSON_TYPE, response.getLastModified(), response.getEntityTag(),
				Response.Status.NOT_MODIFIED);
	}

	@Test
	public void getTwoUsers() {
		createUserBobStudent(Response.Status.CREATED);

		Form otherUserForm = new Form().param("username", "other").param(
				"realname", "Joe Other");
		postFormData("/users", otherUserForm, Response.Status.CREATED);

		UserCollectionRepresentation users = target("/users").request().get(
				UserCollectionRepresentation.class);
		assertNotNull(users);
		assertEquals(2, users.getUsers().size());

	}

	@Test
	public void verifyNonExistentStudentFailsWithNotFound() {
		createUserBobStudent(Response.Status.CREATED);

		Response response = getEntity("/users/otherstudent",
				MediaType.APPLICATION_JSON_TYPE, Response.Status.NOT_FOUND);
		String message = readEntity(response, String.class);
		assertEquals("User otherstudent does not exist.", message);
	}
}
