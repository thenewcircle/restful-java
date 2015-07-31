package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import chirp.service.representations.ChirpRepresentation;

public class ChirpResourceTest extends JerseyResourceTest {

	private static String userNameFromURI(URI userURI) {
		String[] paths = userURI.getPath().split("\\/");
		assertEquals(3, paths.length);
		String username = paths[paths.length - 1];
		return username;
	}

	private Response createDeafultChirp(String username) {
		logger.debug("Creating default chirp for {}", username);

		return target(String.format("/users/%s/chirps", username)).request()
				.post(Entity.form(new Form().param("content",
						"this is a test message")));
	}

	private Response createChirpForBobStudent() {
		logger.debug("Creating default user student and default chirp message");

		Response response = createUserBobStudent(Response.Status.CREATED);

		return createDeafultChirp(userNameFromURI(response.getLocation()));
	}

	@Test
	public void createChirpSuccess() {
		assertEquals(Response.Status.CREATED.getStatusCode(),
				createChirpForBobStudent().getStatus());
	}

	@Test
	public void getChirpSuccess() {

		Response response = createChirpForBobStudent();
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		ChirpRepresentation chirp = readEntity(
				response.getLocation().getPath(),
				MediaType.APPLICATION_JSON_TYPE, ChirpRepresentation.class);

		assertNotNull(chirp);
	}

	@Test
	public void verifySecondChirpGetRequestReturnsNotModified() {

		Response response = createChirpForBobStudent();
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		URI location = response.getLocation();
		response = getEntity(location, MediaType.APPLICATION_JSON_TYPE,
				Response.Status.OK);
		ChirpRepresentation chirp = readEntity(response,
				ChirpRepresentation.class);
		assertNotNull(chirp);

		getEntity(location, MediaType.APPLICATION_JSON_TYPE,
				response.getLastModified(), response.getEntityTag(),
				Response.Status.NOT_MODIFIED);

	}

	@Test
	public void createChirpTwiceSuccess() {
		createChirpForBobStudent();
		createDeafultChirp("bob");
	}

	@Test
	public void headChirpDoesNotExistFails() {
		createUserBobStudent(Response.Status.CREATED);
		getHead("/users/bob/chirps/doesnotexist",
				MediaType.APPLICATION_JSON_TYPE, Response.Status.NOT_FOUND);
	}

	@Test
	public void getChirpDoesNotExistFails() {
		createUserBobStudent(Response.Status.CREATED);
		getEntity("/users/bob/chirps/doesnotexist",
				MediaType.APPLICATION_JSON_TYPE, Response.Status.NOT_FOUND);
	}

	@Test
	public void createChirpWithNoContentFails() {
		createUserBobStudent(Response.Status.CREATED);
		postFormData("/users/bob/chirps", null, Response.Status.BAD_REQUEST);
	}

}
