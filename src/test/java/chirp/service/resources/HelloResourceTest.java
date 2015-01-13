package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	@Test
	public void helloResourceMustSayHello() {
		String hello = target("/hello").request().get(String.class);
		assertEquals("Hello!", hello);
	}

	@Test
	public void helloResourceMustSayHelloToPathParam() {
		String hello = target("/hello/Cisco").request().get(String.class);
		assertEquals("Hello Cisco!", hello);
	}

	@Test
	public void helloResourceMustSayHelloToQueryParam() {
		String hello = target("/hello").queryParam("name", "Cisco").request().get(String.class);
		assertEquals("Hello Cisco!", hello);
	}
}
