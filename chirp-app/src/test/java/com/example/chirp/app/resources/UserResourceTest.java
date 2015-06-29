package com.example.chirp.app.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.store.memory.InMemoryUsersStore;

public class UserResourceTest extends ResourceTestSupport {

	@Before
	public void beforeTest() {
		MemoryStoreUtil.usersStore.clear();
	}
	
	@Test
	public void testCreateUser() throws Exception {
		Form user = new Form()
				.param("username", "mickey.mouse")
				.param("realName", "Mickey Mouse");
		Response response = target("/users").request().post(Entity.form(user));

		Assert.assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
		Assert.assertNotNull(MemoryStoreUtil.usersStore.getUser("mickey.mouse"));
    }
	
}
