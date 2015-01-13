package chirp.service.resources;

import javax.ws.rs.client.Entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	@Before
	public void clearDatabase() {
		UserRepository database = UserRepository.getInstance();
		database.clear();
	}
	
	@Test
	public void testCreateAndGetUser() {
		//Assemble (Create test data)
		String realname = "Luke Skywalker";
		String username = "luke";
		//Act (Do the action you want to test)
		Entity<String> body = Entity.entity(realname, "text/plain");
		target("users").path(username).request().put(body);
		//Assert (Assert the correct outcome)
		String username2 = target("/users").path(username).request().get(String.class);
		Assert.assertEquals(realname, username2);
	}

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

	@Test
	public void testGetUser() {
		//Assemble (Create test data)
		String realname = "Luke Skywalker";
		String username = "luke";
		UserRepository database = UserRepository.getInstance();
		database.createUser(username, realname);
		//Act (Do the action you want to test)
		String realname2 = target("users").path(username).request().get(String.class);
		//Assert (Assert the correct outcome)
		Assert.assertEquals(realname, realname2);
	}
	
}
