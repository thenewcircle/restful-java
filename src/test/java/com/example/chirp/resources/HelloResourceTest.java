package com.example.chirp.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.WebTarget;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	/** Test /greeting */
	@Test
	public void helloResourceMustSayHelloWorld() {
//		Client client = ClientBuilder.newClient();
//		WebTarget root = client.target("http://localhost:8080/");
		WebTarget root = super.target();
		String hello = root.path("/greeting").request().get(String.class);
		assertEquals("Hello World!", hello);
	}

	/** Test /greeting?name=Doug */
	@Test
	public void helloResourceWithQueryMustSayHelloName() {
		WebTarget root = super.target("/greeting").queryParam("name", "Doug");
		String hello = root.request().get(String.class);
		assertEquals("Hello Doug!", hello);
	}

	/** Test /greeting/Doug */
	@Test
	public void helloResourceWithPathParamMustSayHelloName() {
		WebTarget root = super.target("/greeting").path("/Doug");
		String hello = root.request().get(String.class);
		assertEquals("Hello Doug!", hello);
	}

}
