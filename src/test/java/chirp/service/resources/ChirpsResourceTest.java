package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Test;

import chirp.service.representations.ChirpRepresentation;

public class ChirpsResourceTest extends JerseyResourceTest {

	private Response createTestChirp(Response.Status expected) {
		final Form form = new Form().param("content", "test message");
		final Response response = target("/chirps").path("student").request()
				.post(Entity.form(form));
		assertEquals(expected.getStatusCode(), response.getStatus());
		assertTrue(response.getLocation().toString()
				.contains("/chirps/student"));
		return response;
	}

	@Test
	public void createChirpSuccess() {
		createBobStudent(Response.Status.CREATED);
		createTestChirp(Response.Status.CREATED);
	}

	@Test
	public void getChirpSuccess() {
		createBobStudent(Response.Status.CREATED);
		Response response = createTestChirp(Response.Status.CREATED);
		ChirpRepresentation chirp = target(response.getLocation().getPath())
				.request().get(ChirpRepresentation.class);
		assertNotNull(chirp);
	}

}