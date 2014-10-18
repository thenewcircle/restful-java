package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	@Test
	public void testGetHello() {
		String hello = target("/greetings").request().get(String.class);
		assertEquals("Hello!", hello);
	}

	@Test
	public void testGetHelloSomebodyAsParameter() {
		String hello = target("/greetings").queryParam("name", "NewCircle")
				.request().get(String.class);
		assertEquals("Hello, NewCircle!", hello);
	}

	@Test
	public void testGetHelloSomebodyAsPath() {
		String hello = target("/greetings/NewCircle").request().get(
				String.class);
		assertEquals("Hello, NewCircle!", hello);
	}

}
