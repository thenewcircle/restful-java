package com.example.chirp.app.resources;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class RootResourceTest extends ResourceTestSupport {

	@Test
	public void rootResourceTest() {
		Response response = target("/").request().get();
		Assert.assertEquals(200, response.getStatus());
		String test = response.readEntity(String.class);
	}

}