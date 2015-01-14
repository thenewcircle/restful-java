package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.UserRepository;
import chirp.service.resprentations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Response createBobStudentUser() {
		Form newUser = new Form().param("realname", "Bob Student").param("username","student");
		return target("/users").request().post(Entity.form(newUser));
	}
	
	@After
	public void clearUserRepository() {
		UserRepository.getInstance().clear(); // remove data between test method invocations
	}
	
	@Test
	public void createUserWithPOSTSuccess() {
		
		logger.info("Start: createUserWithPOSTSuccess");
		
		assertEquals(Response.Status.CREATED.getStatusCode(), createBobStudentUser().getStatus());

		logger.info("End: createUserWithPOSTSuccess");
		
	}
	
	@Test
	public void createSameUserTwiceWithPOSTFailure() {
		
		logger.info("Start: createSameUserTwiceWithPOSTFailure");
		
		assertEquals(Response.Status.CREATED.getStatusCode(), createBobStudentUser().getStatus());

		Response response = createBobStudentUser();
		assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
		//Object entityContent = response.getEntity();
		// assertTrue(entityContent instanceof String);
		// String entityString = (String)entityContent;
		// assertEquals("User student already exists.", entityString);
		
		
		logger.info("End: createSameUserTwiceWithPOSTFailure");
		
	}
	
	@Test
	public void getCreatedUserSuccess() {
		logger.info("Start: getCreatedUserSuccess");
		
		Response response = createBobStudentUser();
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		UserRepresentation user = target(response.getLocation().getPath()).request().get(UserRepresentation.class);
		assertEquals("student", user.getUsername());
		
		logger.info("End: getCreatedUserSuccess");
	}


}
