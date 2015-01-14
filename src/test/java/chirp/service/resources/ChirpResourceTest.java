package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class ChirpResourceTest extends JerseyResourceTest {

	@Test
	public void testCreateAndReceiveChirpAsXML() {
		testCreateAndReceiveChirp(MediaType.APPLICATION_XML);
	}
	
	@Test
	public void testCreateAndReceiveChirpAsJSON() {
		testCreateAndReceiveChirp(MediaType.APPLICATION_JSON);
	}
	
	public void testCreateAndReceiveChirp(String mediaType) {
		String username = "Doug";
		String realname = "Doug Bateman";
		String message = "REST rocks!";
		
		//1. Create user
		Response response1 = target("users").path(username).request()
				.put(Entity.text(realname));
		//2. Create chirp
		Response response2 = target("chirps").path(username).request()
				.post(Entity.text(message));
		URI chirpLocation = response2.getLocation();
		//3. GET the new Chirp
		Response response3 = client().target(chirpLocation).request()
				.accept(mediaType).get();
		ChirpRepresentation chirp = response3.readEntity(ChirpRepresentation.class);

		Assert.assertEquals(mediaType, response3.getMediaType().toString());
		Assert.assertEquals(username, chirp.getUsername());
		Assert.assertEquals(message, chirp.getMessage());
	}
	
}
