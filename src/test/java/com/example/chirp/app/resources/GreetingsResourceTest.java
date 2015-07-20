package com.example.chirp.app.resources;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class GreetingsResourceTest extends ResourceTestSupport {

	@Test
	public void testSayHello() {
		Response response = target("/greetings").request().get();
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String text = response.readEntity(String.class);
		Assert.assertEquals("Hello!", text);
	}
}
