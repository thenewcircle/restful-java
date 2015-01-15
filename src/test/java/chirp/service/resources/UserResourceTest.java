package chirp.service.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import chirp.service.resprentations.UserCollectionRepresentation;
import chirp.service.resprentations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest {


	@Test
	public void createUserWithPOSTSuccess() {

		logger.info("Start: {}", testName.getMethodName());

		createUserBobStudent(Response.Status.CREATED);

		logger.info("End: {}", testName.getMethodName());

	}

	@Test
	public void createSameUserTwiceWithPOSTFailure() {

		logger.info("Start: {}", testName.getMethodName());

		createUserBobStudent(Response.Status.CREATED);
		
		Response response = createUserBobStudent(Response.Status.FORBIDDEN);
		
		String message = readEntity(response, String.class);
		
		assertEquals("User student already exists.", message);

		logger.info("End: {}", testName.getMethodName());

	}

	@Test
	public void getCreatedUserSuccess() {
		logger.info("Start: {}", testName.getMethodName());

		Response response = createUserBobStudent(Response.Status.CREATED);

		UserRepresentation user = target(response.getLocation().getPath())
				.request().get(UserRepresentation.class);
		assertEquals("student", user.getUsername());

		logger.info("End: {}", testName.getMethodName());
	}
	
	@Test
	public void getTwoUsers() {
		createUserBobStudent(Response.Status.CREATED);
		
		Form otherUserForm = new Form().param("username","other").param("realname", "Joe Other");
		postFormData("/users", otherUserForm, Response.Status.CREATED);
		
		UserCollectionRepresentation users = target("/users").request().get(UserCollectionRepresentation.class);
		assertNotNull(users);
		assertEquals(2, users.getUsers().size());
		
	}
	
	@Test
	public void verifyNonExistentStudentFailsWithNotFound() {
		createUserBobStudent(Response.Status.CREATED);
		
		Response response = getEntity("/users/otherstudent", MediaType.APPLICATION_JSON_TYPE, Response.Status.NOT_FOUND);
		String message = readEntity(response,String.class);
		assertEquals("User otherstudent does not exist.", message);
		
	}


}
