package com.example.chirp.app.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

		Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

		Assert.assertNotNull(getUserStore().getUser("student"));
	}
}
