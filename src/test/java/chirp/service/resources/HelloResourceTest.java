package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	@Test
	public void helloResourceWithQueryParameterNameMustSayHello() {
		String hello = target("/hello").queryParam("name", "Bob").request()
				.get(String.class);
		assertEquals("Hello Bob!", hello);
	}

	@Test
	public void helloResourceGreetsByName() {
		String hello = target("/hello/Cisco").request()
				.header("Accept", MediaType.TEXT_HTML_TYPE).get(String.class);
		assertEquals("<html><body><h1>Hello Cisco!</h1></body></html>", hello);
	}

	@Test
	public void helloResourceWithNameMustSayHello() {
		String hello = target("/hello").queryParam("name", "Xoom")
				.request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
		assertEquals("Hello Xoom!", hello);
	}

	@Test
	public void helloResourceWithPathNameMustSayHello() {
		String hello = target("/hello/Xoom").request(MediaType.TEXT_HTML_TYPE)
				.get(String.class);
		assertEquals("<html><body><h1>Hello Xoom!</h1></body></html>", hello);
	}

}
