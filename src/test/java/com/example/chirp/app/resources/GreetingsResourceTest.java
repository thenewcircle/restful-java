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
		Assert.assertEquals("Hello dude!", text);
	}

	@Test
	public void testSayHelloWithQueryParam() {
		Response response = target("/greetings").queryParam("name", "Tom").request().get();
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String text = response.readEntity(String.class);
		Assert.assertEquals("Hello Tom!", text);
	}

	// @Test
	// public void testSayHelloWithPathParam() {
	// Response response = target("/greetings").path("Tom").request().get();
	// Assert.assertEquals(Response.Status.OK.getStatusCode(),
	// response.getStatus());
	// String text = response.readEntity(String.class);
	// Assert.assertEquals("Hello Tom!", text);
	// }

	@Test
	public void testEchoHeader() {
		Response response = target("/greetings").request().header("X-NewCircle-Echo", "Tom").get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		String text = response.getHeaderString("X-NewCircle-Echo-Response");
		Assert.assertEquals("Tom, Tom, Tom", text);
	}

	// @Test
	// public void testEchoHttpHeaders() {
	// Response response =
	// target("/greetings").path("Tom").request().header("X-NewCircle-Echo",
	// "Tom").get();
	//
	// Assert.assertEquals(Response.Status.OK.getStatusCode(),
	// response.getStatus());
	// String text = response.getHeaderString("X-NewCircle-Echo-Response");
	// Assert.assertEquals("Tom, Tom, Tom", text);
	// }
}
