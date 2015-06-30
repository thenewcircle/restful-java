package com.example.chirp.app.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.kernel.stores.UsersStoreUtils;
import com.example.chirp.store.memory.InMemoryUsersStore;

public class UserResourceTest extends ResourceTestSupport {

	@Before
	public void beforeTest() {
		getUserStore().clear();
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
		String expected = "{\"self\":\"http://localhost:9998/users/vader\",\"username\":\"vader\",\"realName\":\"Darth Vader\"}";
		Assert.assertEquals(expected, json);
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
