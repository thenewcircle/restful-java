package chirp.service.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
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
		Client client = ClientBuilder.newClient();
		HttpClient
		String realname = "Luke Skywalker";
		String username = "luke";
		//Act (Do the action you want to test)
		Entity<String> body = Entity.entity(realname, "text/plain");
		target("users").path(username).request().put(body);
		String oauthTicket = "OAuth oauth_consumer_key=\"xvz1evFS4wEEPTGEFPHBog\"";
		String realname2 = target("/users")
				.register(HttpAuthenticationFeature.digest("doug", "bateman"))
				.path(username).request()
				.get(String.class);
		//Assert (Assert the correct outcome)
		Assert.assertEquals(realname, realname2);
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
	public void testCreatedDuplicateUser() {
		//Assemble (Create test data)
		String realname = "Luke Skywalker";
		String username = "luke";
		//Act (Do the action you want to test)
		Entity<String> body = Entity.entity(realname, "text/plain");
		Response response1 = target("users").path(username).request().put(body);
		Assert.assertEquals(Status.OK, response1.getStatusInfo());
		Response response2 = target("users").path(username).request().put(body);
		Assert.assertEquals(Status.CONFLICT, response2.getStatusInfo());
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
		String realname2 = target("users").path(username).request()
				.accept("text/plain").get(String.class);
		//Assert (Assert the correct outcome)
		Assert.assertEquals(realname, realname2);
	}
	
	@Test
	public void testGetUserAsXML() {
		//Assemble (Create test data)
		String realname = "Luke Skywalker";
		String username = "luke";
		UserRepository database = UserRepository.getInstance();
		database.createUser(username, realname);
		//Act (Do the action you want to test)
		UserRepresentation user = target("users").path(username).request()
				.accept("text/xml").get(UserRepresentation.class);
		//Assert (Assert the correct outcome)
		Assert.assertEquals(realname, user.getRealName());
	}
	
}
