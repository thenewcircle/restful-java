package chirp.service.resources;

import javax.ws.rs.client.Entity;

import org.junit.Assert;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	@Test
	public void testCreateUser() {
		//Assemble (Create test data)
		String realname = "Luke Skywalker";
		String username = "luke";
		//Act (Do the action you want to test)
		Entity<String> body = Entity.entity(realname, "text/plain");
		target("users").path(username).request().put(body);
		//Assert (Assert the correct outcome)
		UserRepository database = UserRepository.getInstance();
		Assert.assertEquals(realname, database.getUser(username).getRealname());
	}
	
}
