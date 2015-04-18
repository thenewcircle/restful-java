package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	@Test
	public void helloResourceMustSayHello() {
		String hello = target("/hello").request()
				.header("Accept", MediaType.TEXT_PLAIN_TYPE).get(String.class);
		assertEquals("Hello!", hello);
	}

	@Test
	public void helloResourceGreetsByName() {
		String hello = target("/hello/Cisco").request()
				.header("Accept", MediaType.TEXT_HTML_TYPE).get(String.class);
		assertEquals("<html><body><h1>Hello Cisco!</h1></body></html>", hello);
	}

}
