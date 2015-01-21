package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import chirp.service.resprentations.ChirpRepresentation;

public class ChirpResourceTest extends JerseyResourceTest {

	private static String userNameFromURI(URI userURI) {
		String[] paths = userURI.getPath().split("\\/");
		assertEquals(3, paths.length);
		String username = paths[paths.length - 1];
		return username;
	}

	private Response createChirpForBobStudent() {
		Response response = createUserBobStudent(Response.Status.CREATED);

		String username = userNameFromURI(response.getLocation());
		Form chirpForm = new Form().param("content", "this is a test message");
		return target("chirps").path(username).request()
				.post(Entity.form(chirpForm));
	}

	@Test
	public void createChirpSuccess() {
		logger.info("Start: {}", testName.getMethodName());

		assertEquals(Response.Status.CREATED.getStatusCode(),
				createChirpForBobStudent().getStatus());

		logger.info("End: {}", testName.getMethodName());
	}

	@Test
	public void getChirpSuccess() {
		logger.info("Start: {}", testName.getMethodName());

		Response response = createChirpForBobStudent();
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		ChirpRepresentation chirp = readEntity(
				response.getLocation().getPath(),
				MediaType.APPLICATION_JSON_TYPE, ChirpRepresentation.class);

		assertNotNull(chirp);
		logger.info("End: {}", testName.getMethodName());

	}

	@Test
	public void verifySecondChirpGetRequestReturnsNotModified() {
		logger.info("Start: {}", testName.getMethodName());

		Response response = createChirpForBobStudent();
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		URI location = response.getLocation();
		response = getEntity(location,
				MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
		ChirpRepresentation chirp = readEntity(response, ChirpRepresentation.class);
		assertNotNull(chirp);

		getEntity(location, MediaType.APPLICATION_JSON_TYPE,
				response.getLastModified(), response.getEntityTag(),
				Response.Status.NOT_MODIFIED);

		logger.info("End: {}", testName.getMethodName());

	}

}
