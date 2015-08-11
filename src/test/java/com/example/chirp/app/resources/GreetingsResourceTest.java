package com.example.chirp.app.resources;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class GreetingsResourceTest extends ResourceTestSupport {

	@Test
	public void testSayHelloDefault() {
		Response response = target("/greetings").request().get();
		String value = response.readEntity(String.class);
		Assert.assertEquals("Hello Mr!", value);
	}
	
	@Test
	public void testSayHelloQueryParam() {
		Response response = target("/greetings")
				.queryParam("name", "Tom")
				.request()
				.get();
		
		String value = response.readEntity(String.class);
		Assert.assertEquals("Hello Tom!", value);
	}
	
	@Test
	public void testSayHelloPathParam() {
		Response response = target("/greetings/Mike")
				.request()
				.get();
		
		String value = response.readEntity(String.class);
		Assert.assertEquals("Hello Mike!", value);
	}
	
	@Test
	public void echoHeader() {
		Response response = target("/greetings")
				.request()
				.header("X-NewCircle-Echo", "Tom")
				.get();

		String value = response.getHeaderString("X-NewCircle-Echo-Response");
		Assert.assertEquals("Tom", value);
	}
}
