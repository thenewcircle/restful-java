package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.app.UserResource;
import com.example.chirp.app.pub.ExceptionInfo;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.stores.UserStoreUtils;

public class UserResourceTest extends ResourceTestSupport {

	@Before
	public void before() {
		getUserStore().clear();
	}

	@Test
	public void testCreateUser() {

		String username = "student";

		Form user = new Form().param("realname", "Bob Student");

		Response response = target("/users").path(username).request().put(Entity.form(user));

		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		Assert.assertNotNull(getUserStore().getUser("student"));

		String location = response.getHeaderString("Location");
		Assert.assertEquals("http://localhost:9998/users/student", location);

		response = target(location.substring(21)).request().accept(MediaType.APPLICATION_JSON).get();
		PubUser pubUser = response.readEntity(PubUser.class);
		Assert.assertEquals("Bob Student", pubUser.getRealname());
	}

	@Test
	public void testGetUser() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());

		Response response = target("/users").path("yoda").request().accept(MediaType.TEXT_PLAIN).get();
		Assert.assertEquals(200, response.getStatus());

		String realname = response.readEntity(String.class);
		Assert.assertEquals("Master Yoda", realname);
	}

	@Test
	public void testGetGetUserJson() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());

		// "standard" no chirps
		// http://localhost:8080/chirp-app/users/yoda

		// "full" with all
		// http://localhost:8080/chirp-app/users/yoda?variant=full
		// http://localhost:8080/chirp-app/users/yoda (header: variant=full)

		// Header Link to alter(stand)
		// Header Link to alter(full)
		// Header Link to alter(id)
		// Header Link to alter(links)

		Response response = target("/users").path("yoda").queryParam("variant", UserResource.Variant.STANDARD).request().accept(MediaType.APPLICATION_JSON)
				.get();
		Assert.assertEquals(200, response.getStatus());

		// String text = response.readEntity(String.class);
		// Assert.assertTrue(text.startsWith("{"));
		// Assert.assertTrue(text.endsWith("}"));

		PubUser pubUser = response.readEntity(PubUser.class);
		Assert.assertEquals("yoda", pubUser.getUsername());
		Assert.assertEquals("Master Yoda", pubUser.getRealname());

		URI selfLink = URI.create("http://localhost:9998/users/yoda");
		Assert.assertEquals(selfLink, pubUser.getSelf());

		URI chirpsLink = URI.create("http://localhost:9998/users/yoda/chirps");
		Assert.assertEquals(chirpsLink, pubUser.getChirpsLink());

		Link link = response.getLink("chirps");
		Assert.assertEquals(chirpsLink, link.getUri());

		// List<PubChirp> chirps = pubUser.getChirps();
		// Assert.assertEquals(0, chirps.size());
		//
		// List<URI> chirpLinks = pubUser.getChirpLinks();
		// Assert.assertEquals(0, chirpLinks.size());
	}

	@Test
	public void testGetGetUserJsonFull() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());
		Response response = target("/users").path("yoda").queryParam("variant", UserResource.Variant.FULL).request().accept(MediaType.APPLICATION_JSON).get();
		Assert.assertEquals(200, response.getStatus());
		PubUser pubUser = response.readEntity(PubUser.class);

		// List<PubChirp> chirps = pubUser.getChirps();
		// Assert.assertEquals(2, chirps.size());
	}

	@Test
	public void testGetGetUserJsonLinks() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());
		Response response = target("/users").path("yoda").queryParam("variant", UserResource.Variant.LINKS).request().accept(MediaType.APPLICATION_JSON).get();
		Assert.assertEquals(200, response.getStatus());
		PubUser pubUser = response.readEntity(PubUser.class);

		// List<URI> chirps = pubUser.getChirpLinks();
		// Assert.assertEquals(2, chirps.size());
	}

	@Test
	public void testGetGetUserXml() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());

		Response response = target("/users").path("yoda").request().accept(MediaType.APPLICATION_XML).get();
		Assert.assertEquals(200, response.getStatus());

		String text = response.readEntity(String.class);
		Assert.assertTrue(text.startsWith("<"));
		Assert.assertTrue(text.endsWith(">"));
	}

	@Test
	public void testCreateDuplicate() {
		String username = "student";
		Form user = new Form().param("realname", "Bob Student");

		Response response = target("/users").path(username).request().put(Entity.form(user));
		Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

		response = target("/users").path(username).request().put(Entity.form(user));
		Assert.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
	}

	@Test
	public void testGetWrongUser() throws Exception {
		Response response = target("users").path("mickey.mouse").request().get();
		Assert.assertEquals(404, response.getStatus());

		ExceptionInfo info = response.readEntity(ExceptionInfo.class);
		Assert.assertEquals(404, info.getStatus());
		Assert.assertEquals("User \"mickey.mouse\" was not found.", info.getMessage());
	}

	@Test
	public void testCreateWithBadName() {
		String username = "mickey mouse";
		Form user = new Form().param("realname", "Bob Student");

		Response response = target("/users").path(username).request().put(Entity.form(user));
		Assert.assertEquals(400, response.getStatus());

		ExceptionInfo info = response.readEntity(ExceptionInfo.class);
		Assert.assertEquals(400, info.getStatus());
		Assert.assertEquals("That sucked.", info.getMessage());
	}

	@Test
	public void testGetUserTxtExt() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());
		Response response = target("/users").path("yoda.txt").request().get();
		Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void testGetUserJsonExt() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());
		Response response = target("/users").path("yoda.json").request().get();
		Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void testGetUserXmlExt() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());
		Response response = target("/users").path("yoda.xml").request().get();
		Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void testGetUserAviExt() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());
		Response response = target("/users").path("yoda.avi").request().get();
		Assert.assertEquals(406, response.getStatus());
	}

	// @Test
	// public void testGetUserAvi() {
	// UserStoreUtils.resetAndSeedRepository(getUserStore());
	// Response response =
	// target("/users").path("yoda").request().accept("application/avi").get();
	// Assert.assertEquals(200, response.getStatus());
	// }

	@Test
	public void testCreateChirp() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());

		String newMessage = "I don't like Jar Jar Binks.";
		Entity<String> entity = Entity.entity(newMessage, MediaType.TEXT_PLAIN);
		Response response = target("/users").path("yoda").path("chirps").request().post(entity);
		Assert.assertEquals(201, response.getStatus());

		String location = response.getHeaderString("Location");
		location = location.substring(21);
		// Assert.assertEquals("http://localhost:9998/chirps/1437598712438",
		// location);

		response = target(location).request().get();
		PubChirp chirp = response.readEntity(PubChirp.class);
		Assert.assertEquals(newMessage, chirp.getContent());
	}
}
