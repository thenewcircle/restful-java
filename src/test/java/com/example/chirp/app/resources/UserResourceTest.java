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

		Response response = target("/users").path("yoda").request().accept(MediaType.APPLICATION_JSON).get();
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
	public void testGetWrongUser() {
		Response response = target("users").path("mickey.mouse").request().get();
		Assert.assertEquals(404, response.getStatus());

		String msg = response.readEntity(String.class);
		Assert.assertEquals("This entity does not exist", msg);
	}

	@Test
	public void testCreateWithBadName() {
		String username = "mickey mouse";
		Form user = new Form().param("realname", "Bob Student");

		Response response = target("/users").path(username).request().put(Entity.form(user));
		Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
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
}
