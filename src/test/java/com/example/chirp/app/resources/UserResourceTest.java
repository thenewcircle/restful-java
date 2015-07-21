package com.example.chirp.app.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

		response = target(location.substring(21)).request().get();
		Assert.assertEquals("Bob Student", response.readEntity(String.class));
	}

	@Test
	public void testGetUser() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());

		Response response = target("/users").path("yoda").request().get();
		Assert.assertEquals(200, response.getStatus());

		String realname = response.readEntity(String.class);
		Assert.assertEquals("Master Yoda", realname);
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
}
