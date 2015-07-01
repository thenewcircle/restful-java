package com.example.chirp.app.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.kernel.Chirp;
import com.example.chirp.kernel.ChirpId;
import com.example.chirp.kernel.User;
import com.example.chirp.kernel.stores.UsersStoreUtils;
import com.example.chirp.pub.PubChirps;
import com.example.chirp.store.memory.InMemoryUsersStore;

public class UserResourceTest extends ResourceTestSupport {

	@Before
	public void beforeTest() {
		getUserStore().clear();
	}

	@Test
	public void getChirpsForUser() {
		 UsersStoreUtils.resetAndSeedRepository(getUserStore());
			
		Response response = target("users")
			  .path("vader")
			  .path("chirps")
			  .request()
			  .get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		
		PubChirps chirps = response.readEntity(PubChirps.class);
		Assert.assertNotNull(chirps);
		Assert.assertEquals(2, chirps.getChirps().size());
	}
	
	@Test
	public void testCreateChirp() {
		 UsersStoreUtils.resetAndSeedRepository(getUserStore());
		
		String message = "I will KEEL you.";
		Response response = target("users")
			  .path("vader")
			  .path("chirps")
			  .request()
			  .post(Entity.text(message));

		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		// After the update, get the user form the store
		User user = getUserStore().getUser("vader");
		// There should be at least one chirp
		Assert.assertFalse(user.getChirps().isEmpty());
		// The first one should be the one we created
		Chirp chirp = user.getChirps().getFirst();
		// Get the last chrip's ID
		ChirpId id = chirp.getId();

		// Get the location header and validate it
		String location = response.getHeaderString("Location");
		Assert.assertEquals("http://localhost:9998/chirps/"+id, location);
	}
	
	@Test
	public void testGetUsers() {
		UsersStoreUtils.resetAndSeedRepository(getUserStore());
		
		Response response = target("/users")
				.request()
				.header("Accept", MediaType.APPLICATION_JSON)
				.get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetUserName() {
		UsersStoreUtils.resetAndSeedRepository(getUserStore());
		
		Response response = target("/users/vader")
				.request()
				.header("Accept", MediaType.TEXT_PLAIN)
				.get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String name = response.readEntity(String.class);
		Assert.assertEquals("Darth Vader", name);
	}
	
	@Test
	public void testGetUserJson() {
		UsersStoreUtils.resetAndSeedRepository(getUserStore());
		
		Response response = target("/users/vader")
				.request()
				.header("Accept", MediaType.APPLICATION_JSON)
				.get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String json = response.readEntity(String.class);
//		String expected = "{\"self\":\"http://localhost:9998/users/vader\",\"username\":\"vader\",\"realName\":\"Darth Vader\"}";
//		Assert.assertEquals(expected, json);
		Assert.assertTrue(json.startsWith("{"));
	}
	
	@Test
	public void testGetUserXml() {
		UsersStoreUtils.resetAndSeedRepository(getUserStore());
		
		Response response = target("/users/vader")
				.request()
				.header("Accept", MediaType.APPLICATION_XML)
				.get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String xml = response.readEntity(String.class);
		Assert.assertTrue(xml.startsWith("<"));
	}
	
	@Test
	public void testCreateUser() throws Exception {
		Form user = new Form()
				.param("username", "mickey.mouse")
				.param("realName", "Mickey Mouse");
		Response response = target("/users").request().put(Entity.form(user));

		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		Assert.assertNotNull(getUserStore().getUser("mickey.mouse"));

		String location = response.getHeaderString("Location");
		Assert.assertEquals("http://localhost:9998/users/mickey.mouse", location);
	}
	
	
	@Test
	public void testCreateDuplicateUser() throws Exception {
		Form user = new Form()
				.param("username", "mickey.mouse")
				.param("realName", "Mickey Mouse");
		Response response = target("/users").request().put(Entity.form(user));
		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		response = target("/users").request().put(Entity.form(user));
		Assert.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertEquals("The record \"mickey.mouse\" already exists.", msg);
	}
}
