package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.model.UserRepository;
import chirp.service.resprentations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest {

	// Thanks to Muthu for finding this Junit 4.7 and greater feature returning
	// the name of the current method.
	@Rule
	public TestName testName = new TestName();

	@Test
	public void createUserWithPOSTSuccess() {

		logger.info("Start: {}", testName.getMethodName());

		assertEquals(Response.Status.CREATED.getStatusCode(),
				createUserBobStudent().getStatus());

		logger.info("End: {}", testName.getMethodName());

	}

	@Test
	public void createSameUserTwiceWithPOSTFailure() {

		logger.info("Start: {}", testName.getMethodName());

		assertEquals(Response.Status.CREATED.getStatusCode(),
				createUserBobStudent().getStatus());

		Response response = createUserBobStudent();
		assertEquals(Response.Status.FORBIDDEN.getStatusCode(),
				response.getStatus());
		// Object entityContent = response.getEntity();
		// assertTrue(entityContent instanceof String);
		// String entityString = (String)entityContent;
		// assertEquals("User student already exists.", entityString);

		logger.info("End: {}", testName.getMethodName());

	}

	@Test
	public void getCreatedUserSuccess() {
		logger.info("Start: {}", testName.getMethodName());

		Response response = createUserBobStudent();
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		UserRepresentation user = target(response.getLocation().getPath())
				.request().get(UserRepresentation.class);
		assertEquals("student", user.getUsername());

		logger.info("End: {}", testName.getMethodName());
	}

}
