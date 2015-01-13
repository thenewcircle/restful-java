package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.junit.Ignore;
import org.junit.Test;

public class HelloLiveServerTest {

	@Test
	@Ignore
	public void helloResourceMustSayHello() {
		Client client = ClientBuilder.newClient();
		String hello = client.target("http://localhost:8080/greeting").request()
				.get(String.class);
		assertEquals("Hello!", hello);
	}

}
