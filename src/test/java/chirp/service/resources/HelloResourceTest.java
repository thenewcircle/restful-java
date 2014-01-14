package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest<HelloResource> {

	@Test
	public void helloResourceMustSayHello() {
		String hello = target("/greeting").request().get(String.class);
		assertEquals("Hello!", hello);
	}
	
	@Test
	public void helloResourceMustSayHelloWithName() {
		String hello = target("/greeting/Cisco").request().get(String.class);
		assertEquals("Hello Cisco!", hello);
	}
	
	@Test
	public void helloResourceMustSayHelloWithNameQueryParam() {
		String hello = target("/greeting/withName").queryParam("name", "Siene").request().get(String.class);
		assertEquals("Hello Siene!", hello);
	}


}
