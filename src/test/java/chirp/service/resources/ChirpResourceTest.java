package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.resprentations.ChirpRepresentation;

public class ChirpResourceTest extends JerseyResourceTest {
	
	private static String userNameFromURI(URI userURI) {
		String[] paths = userURI.getPath().split("\\/");
		assertEquals(3, paths.length);
		String username = paths[paths.length - 1];
		return username;
	}
	
	private Response createChirpForBobStudent() {
		Response response = createUserBobStudent();
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		
		String username = userNameFromURI(response.getLocation());
		Form chirpForm = new Form().param("content","this is a test message");
		return target("chirps").path(username).request().post(Entity.form(chirpForm));
	}
	
	@Test
	public void createChirpSuccess() {
		assertEquals(Response.Status.CREATED.getStatusCode(), createChirpForBobStudent().getStatus());
	}

	
	@Test
	public void getChirpSuccess() {
		Response response = createChirpForBobStudent();
		assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
		
		ChirpRepresentation chirp = target(response.getLocation().getPath()).request().get(ChirpRepresentation.class);
		
		assertNotNull(chirp);
	}
}
