package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	@Test
	public void helloResourceWithPathParameterNameMustSayHello() {
		String hello = target("/hello").path("Cisco").request().get(String.class);
		assertEquals("Hello Cisco!", hello);
	}
	
	@Test
	public void helloResourceWithQueryParameterNameMustSayHello() {
		String hello = target("/hello").queryParam("name", "Bob").request().get(String.class);
		assertEquals("Hello Bob!", hello);
	}

}
