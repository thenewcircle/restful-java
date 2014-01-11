package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest<HelloResource> {

	@Test
	public void testGetHello() {
		String hello = target("/greeting").request().get(String.class);
		assertEquals("Hello!", hello);
	}
	
	@Test
	public void testGetHelloSomebody() {
		String hello = target("/greeting/Cisco").request().get(String.class);
		assertEquals("Hello, Cisco!", hello);
	}

}
