package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest<HelloResource> {

	@Test
	public void helloResourceMustSayHello() {
		String hello = target("/hello").request().get(String.class);
		assertEquals("Hello!", hello);
	}
	
	@Test
	public void helloResourceMustSayHelloWithName() {
		String hello = target("/hello/Cisco").request().get(String.class);
		assertEquals("Hello Cisco!", hello);
	}


}
