package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	@Test
	public void helloResourceMustSayHello() {
		String hello = target("/greeting").request().get(String.class);
		assertEquals("Hello!", hello);
	}

	/**
	 * Test http://localhost:8080/greeting?name=Doug returns "Hello Doug!"
	 */
	@Test
	public void helloResourceWithName() {
		String hello = target("/greeting?name=Doug").request().get(String.class);
//		String hello = target("/greeting").queryParam("name", "Doug").request().get(String.class);
		assertEquals("Hello, Doug!", hello);
	}

	/**
	 * Test http://localhost:8080/greeting/Doug returns "Hello, Doug!"
	 */
	@Test
	public void helloSubresource() {
		String hello = target("/greeting").path("Doug").request().get(String.class);
		assertEquals("Hello, Doug!!", hello);
	}

}
