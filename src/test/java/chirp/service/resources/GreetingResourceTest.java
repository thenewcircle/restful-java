package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class GreetingResourceTest extends JerseyResourceTest {

	@Test
	public void greetingResourceWithName() {
		Response response = target("/greetings").queryParam("name", "John").request().get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("Hello John!", text);
	}

	@Test
	public void greetingResourceNoName() {
		Response response = target("/greetings").request().get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("Hello stranger!", text);
	}

	@Test
	public void greetingResourceWithNameAsPathParameter() {
		Response response = target("/greetings/John").request().get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("Hello John!", text);
	}

}
