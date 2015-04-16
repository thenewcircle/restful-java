package chirp.service.resources;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class ChirpsResourceTest extends JerseyResourceTest {

	private Response createDefaultChirp(String username,
			Response.Status expectedStatus) {
		Form chirp = new Form().param("content", "yep, I am a chirp!");

		return postFormData(String.format("/users/%s/chirps", username), chirp,
				expectedStatus);
	}

	private Response createBobDefaultChirp(Response.Status expectedStatus) {
		createUserBobStudent(Response.Status.CREATED);
		return createDefaultChirp("student", expectedStatus);
	}

	@Test
	public void createChirpSuccess() {

		createBobDefaultChirp(Response.Status.CREATED);

	}

	@Test
	public void headUserDoesNotExistFails() {
		createUserBobStudent(Response.Status.CREATED);
		getHead("/users/bob/chirps/doesnotexist",
				MediaType.APPLICATION_JSON_TYPE, Response.Status.NOT_FOUND);
	}

	@Test
	public void getUserDoesNotExistFails() {
		createUserBobStudent(Response.Status.CREATED);
		getEntity("/users/bob/chirps/doesnotexist",
				MediaType.APPLICATION_JSON_TYPE, Response.Status.NOT_FOUND);
	}

	@Test
	public void createChirpTwiceSucess() {
		createBobDefaultChirp(Response.Status.CREATED);
		createDefaultChirp("student", Response.Status.CREATED);
	}

	@Test
	public void createChirpWithNoContentFails() {
		postFormData("/users", null, Response.Status.BAD_REQUEST);

	}
}
