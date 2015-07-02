package com.example.chirp.app.resources;


import org.junit.Assert;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.example.chirp.app.providers.AuthenticationFilter;


public class HelloResourceTest extends ResourceTestSupport {

	@Test
	public void testHello() throws Exception {
		Response response = target("/hello").request().header("Authorization", AuthenticationFilter.DEFAULT).get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("Hello!", response.readEntity(String.class));
	}

	@Test
	public void testHelloWithNameA() throws Exception {
		Response response = target("/hello/Jacob").request().header("Authorization", AuthenticationFilter.DEFAULT).get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("Hello Jacob!", response.readEntity(String.class));
	}

	@Test
	public void testHelloWithNameAge() throws Exception {
		Response response = target("/hello/Jacob/40").request().header("Authorization", AuthenticationFilter.DEFAULT).get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("Hello Jacob, welcome to 40", response.readEntity(String.class));
	}

	@Test
	public void testHelloWithNameB() throws Exception {
		Response response = target("/hello").queryParam("name", "Jacob").request().header("Authorization", AuthenticationFilter.DEFAULT).get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("Hello Jacob welcome to 0", response.readEntity(String.class));

		response = target("/hello")
				.queryParam("name", "Jacob")
				.queryParam("age", 40)
				.request()
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals("Hello Jacob welcome to 40", response.readEntity(String.class));

		response = target("/hello")
				.queryParam("name", "Jacob")
				.queryParam("age", "40x")
				.request()
				.header("Authorization", AuthenticationFilter.DEFAULT)
				.get();
		Assert.assertEquals(400, response.getStatus());
		Assert.assertEquals("I need an int silly", response.readEntity(String.class));
	}
}
