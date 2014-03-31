package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.WebTarget;

import org.junit.Test;

public class HelloResourceTest extends JerseyResourceTest {

	@Test
	public void helloResourceMustSayHello() {
//		Client client = ClientBuilder.newClient();
//		WebTarget root = client.target("http://localhost:8080/");
		WebTarget root = super.target("/hello");
		String hello = root.request().get(String.class);
		assertEquals("Hello!", hello);
	}

}
