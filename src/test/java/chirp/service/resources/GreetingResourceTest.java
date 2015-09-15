package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class GreetingResourceTest extends JerseyResourceTest {

	public static String ECHO_VALUE = "Kilroy was here.";

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
		Response response = target("/greetings").request().header("Accept",  MediaType.TEXT_PLAIN).get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("Hello stranger!", text);
	}

	@Test
	public void greetingResourceWithNameAsPathParameter() {
		Response response = target("/greetings").path("John").request().header("Accept",  MediaType.TEXT_PLAIN).get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("Hello John!", text);
	}

	@Test
	public void echoHeaderShouldReturnTheSameHeaderEchoResponse() {
		Response response = target("/greetings").path("John").request().header("Accept",  MediaType.TEXT_PLAIN).header("X-NewCircle-Echo", ECHO_VALUE).get();
		String echoResponse = response.getHeaderString("X-NewCircle-Echo-Response");
		assertEquals(ECHO_VALUE, echoResponse);
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("Hello John!", text);
	}

	@Test
	public void greetingResourceAsHtml() {
		Response response = target("/greetings").path("John").request().header("Accept",  MediaType.TEXT_HTML).get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_HTML_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("<html><body><h1>Hello John!</h1></body></html>", text);
	}
}
