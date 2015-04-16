package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Test;

public class UserResourceTest extends JerseyResourceTest {

	@After
	public void tearDown() {
		clearUserRepository();
	}

	@Test
	public void createUserSuccess() {

		Response response = createUserBobStudent(Response.Status.CREATED);
		assertNotNull(response.getLocation());

		getHead(response.getLocation(), MediaType.APPLICATION_JSON_TYPE,
				Response.Status.OK);
	}

	@Test
	public void headUserDoesNotExistFails() {
		
		getHead("/users/doesnotexist", MediaType.APPLICATION_JSON_TYPE,
				Response.Status.NOT_FOUND);


	}

	@Test
	public void getUserDoesNotExistFails() {
		getEntity("/users/doesnotexist", MediaType.APPLICATION_JSON_TYPE,
				Response.Status.NOT_FOUND);
	}

	@Test
	public void createUserTwiceFails() {
		createUserBobStudent(Response.Status.CREATED);
		createUserBobStudent(Response.Status.BAD_REQUEST);
	}
	
	@Test
	public void createUserWithNullRealnameFails() {
		Form form = new Form().param("username", "doesnotexist");
		postFormData("/users", form, Response.Status.BAD_REQUEST);
	
	}
}
