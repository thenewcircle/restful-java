package com.example.chirp.app.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.app.providers.AuthenticationFilter;
import com.example.chirp.app.providers.CopyrightWriterInterceptor;
import com.example.chirp.app.providers.CsrfPreventionRequestFilter;
import com.example.chirp.kernel.Chirp;
import com.example.chirp.kernel.ChirpId;
import com.example.chirp.kernel.User;
import com.example.chirp.kernel.stores.UsersStoreUtils;
import com.example.chirp.pub.PubChirps;

public class UserResourceTest extends ResourceTestSupport {

	@Before
	public void beforeTest() {
		getUsersStore().clear();
	}

	@Test
	public void getChirpsForUser() {
		 UsersStoreUtils.resetAndSeedRepository(getUsersStore());
			
		Response response = target("users")
			  .path("vader")
			  .path("chirps")
			  .request()
			  .header("Accept", MediaType.APPLICATION_JSON)
			  .header("Authorization", AuthenticationFilter.DEFAULT)
			  .get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		
		PubChirps chirps = response.readEntity(PubChirps.class);
		Assert.assertNotNull(chirps);
		Assert.assertEquals(1, chirps.getChirps().size());
	}
	
	@Test
	public void testCreateChirp() {
		 UsersStoreUtils.resetAndSeedRepository(getUsersStore());
		
		String message = "I will KEEL you.";
		Response response = target("users")
			  .path("vader")
			  .path("chirps")
			  .request()
			  .header("Authorization", AuthenticationFilter.DEFAULT)
			  .header(CsrfPreventionRequestFilter.HEADER, "whatever")
			  .post(Entity.text(message));

		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		// After the update, get the user form the store
		User user = getUsersStore().getUser("vader");
		// There should be at least one chirp
		Assert.assertFalse(user.getChirps().isEmpty());
		// The first one should be the one we created
		Chirp chirp = user.getChirps().getFirst();
		// Get the last chrip's ID
		ChirpId id = chirp.getId();

		// Get the location header and validate it
		String location = response.getHeaderString("Location");
		Assert.assertEquals("http://localhost:9998/chirps/"+id, location);

		String deprecatedHeader = response.getHeaderString("Deprecated");
		Assert.assertNull(deprecatedHeader);
	}

	@Test
	public void testCreateChirpAtOtherUri() {
		 UsersStoreUtils.resetAndSeedRepository(getUsersStore());
		
		String message = "I will KEEL you.";
		Response response = target("chirps")
			  .path("vader")
			  .request()
			  .header("Authorization", AuthenticationFilter.DEFAULT)
			  .header(CsrfPreventionRequestFilter.HEADER, "whatever")
			  .post(Entity.text(message));

		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		// After the update, get the user form the store
		User user = getUsersStore().getUser("vader");
		// There should be at least one chirp
		Assert.assertFalse(user.getChirps().isEmpty());
		// The first one should be the one we created
		Chirp chirp = user.getChirps().getFirst();
		// Get the last chrip's ID
		ChirpId id = chirp.getId();

		// Get the location header and validate it
		String location = response.getHeaderString("Location");
		Assert.assertEquals("http://localhost:9998/chirps/"+id, location);

		String deprecatedHeader = response.getHeaderString("Deprecated");
		Assert.assertNotNull(deprecatedHeader);
	}

	@Test
	public void testGetUsers() {
		UsersStoreUtils.resetAndSeedRepository(getUsersStore());
		
		Response response = target("/users")
				.request()
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.header("Accept", MediaType.APPLICATION_JSON)
				.get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testGetUserName() {
		UsersStoreUtils.resetAndSeedRepository(getUsersStore());
		
		Response response = target("/users/vader")
				.request()
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.header("Accept", MediaType.TEXT_PLAIN)
				.get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String name = response.readEntity(String.class);
		Assert.assertEquals("Darth Vader", name);
	}
	
	@Test
	public void testGetUserJson() {
		UsersStoreUtils.resetAndSeedRepository(getUsersStore());
		
		Response response = target("/users/vader")
				.request()
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.header("Accept", MediaType.APPLICATION_JSON)
				.get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String json = response.readEntity(String.class);
		Assert.assertTrue(json.startsWith("{"));
		Assert.assertTrue(json.contains(CopyrightWriterInterceptor.JSON_COPYRIGHT));
		// System.out.println(json);
	}
	
	@Test
	public void testGetUserXml() {
		UsersStoreUtils.resetAndSeedRepository(getUsersStore());
		
		Response response = target("/users/vader")
				.request()
				.header("Accept", MediaType.APPLICATION_XML)
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String xml = response.readEntity(String.class);
		Assert.assertTrue(xml.startsWith("<"));
		Assert.assertTrue(xml.contains(CopyrightWriterInterceptor.XML_COPYRIGHT));
		// System.out.println(xml);
	}
	
	@Test
	public void testCreateUser() throws Exception {
		Form user = new Form()
				.param("username", "mickey.mouse")
				.param("realName", "Mickey Mouse");
		Response response = target("/users")
				.request()
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.put(Entity.form(user));

		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		Assert.assertNotNull(getUsersStore().getUser("mickey.mouse"));

		String location = response.getHeaderString("Location");
		Assert.assertEquals("http://localhost:9998/users/mickey.mouse", location);
	}
	
	
	@Test
	public void testCreateDuplicateUser() throws Exception {
		Form user = new Form()
				.param("username", "mickey.mouse")
				.param("realName", "Mickey Mouse");
		Response response = target("/users")
				.request()
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.put(Entity.form(user));
		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		response = target("/users")
				.request()
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.put(Entity.form(user));
		Assert.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());

// This is not working in Spring		
//		String msg = response.readEntity(String.class);
//		Assert.assertEquals("The record \"mickey.mouse\" already exists.", msg);
	}
}
