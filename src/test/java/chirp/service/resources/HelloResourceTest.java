package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	@Test
	public void helloResourceMustSayHello() {
		String hello = target("/hello").request().get(String.class);
		assertEquals("Hello!", hello);
	}

}
