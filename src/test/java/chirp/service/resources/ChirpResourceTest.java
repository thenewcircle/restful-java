package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Test;

import chirp.service.representations.ChirpRepresentation;

public class ChirpResourceTest extends JerseyResourceTest {

	@Test
	public void createChirpSuccess() {
		Response response = addStudentUser();
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		response = target("/users/student/chirps").request().post(
				Entity.form(new Form().param("content", "test chirp")));

		assertNotNull(response);
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());
	}

	@Test
	public void readChirpSuccess() {
		Response response = addStudentUser();
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		response = target("/users/student/chirps").request().post(
				Entity.form(new Form().param("content", "test chirp")));

		assertNotNull(response);
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		ChirpRepresentation chirp = target(response.getLocation().getPath())
				.request().get(ChirpRepresentation.class);
	}
}
