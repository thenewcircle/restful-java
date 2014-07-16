package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest<HelloResource> {

	@Test
	public void helloResourceMustSayHello() {
		
		String hello = target("/hello").request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
		assertEquals("Hello!", hello);
	}
	
	@Test
	public void helloResourceWithNameMustSayHello() {
		String hello = target("/greeting").queryParam("name", "Cisco").request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
		assertEquals("Hello Cisco!", hello);
	}
	
	@Test
	public void helloResourceWithEmptyNameMustSayHello() {
		String hello = target("/greeting").request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
		assertEquals("Hello!", hello);
	}

	@Test
	public void helloResourceWithPathParameterMustSayHello() {
		String hello = target("/greeting/NewCircle").request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
		assertEquals("Hello NewCircle!", hello);
	}


}
