package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.ChirpCollectionRepresentation;
import chirp.service.representations.ChirpRepresentation;

public class ChirpResourceTest extends JerseyResourceTest {

	private UserRepository userRepository = UserRepository.getInstance();

	@Before
	public void testInit() {
		userRepository.clear();
	}

	private Response createChirp(String username, String messageContent) {
		Form chirpForm = new Form().param("content", messageContent);
		return postFormData("/chirps/" + username, chirpForm,
				Response.Status.CREATED);
	}

	@Test
	public void createChirpSuccess() {

		log.info("Test: Create a chirp for the bob user and verify its content");

		// need to create without server to honor resource isolation in
		// test framework.
		userRepository.createUser("bob", "Bob Student");

		Response response = createChirp("bob", "unit test success?");

		response = getEntity(response.getLocation(),
				MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);

		ChirpRepresentation chirpRead = readEntity(response,
				ChirpRepresentation.class);

		assertNotNull(chirpRead); // make sure the GET response contains an
									// entity.

		String content = chirpRead.getContent();

		assertEquals("unit test success?", content); // validate
														// the

		verifyLinkHeaderExists("up", MediaType.APPLICATION_JSON_TYPE, response);
		verifyLinkHeaderExists("self", MediaType.APPLICATION_JSON_TYPE,
				response);
		verifyLinkHeaderExists("related", MediaType.APPLICATION_JSON_TYPE,
				response);

	}

	@Test
	public void readChirpCollectionSuccess() {

		log.info("Test: Creating a collection of three chirps for one user should succeed with verification of structural self link location.");

		userRepository.createUser("bob", "Bob Student");
		userRepository.createUser("cole", "Cole Student");

		createChirp("bob", "fishing with cole");

		createChirp("cole", "bored waiting for a fish");

		createChirp("bob", "cole caught a fish");

		createChirp("cole", "fish guts are gross");

		createChirp("bob", "eatting a fish dinner with cole");

		for (ChirpRepresentation chirp : readEntity("/chirps/bob",
				MediaType.APPLICATION_JSON_TYPE,
				ChirpCollectionRepresentation.class).getChirps()) {

			getHead(chirp.getSelf(), MediaType.APPLICATION_JSON_TYPE,
					Response.Status.OK);
		}

	}

	@Test
	public void verifyChirpCollectionLinkHeaders() {

		log.info("Test: Creating a collection of three chirps for one user should succeed with link header verification on location only.");

		userRepository.createUser("bob", "Bob Student");

		createChirp("bob", "fishing with cole");

		createChirp("bob", "cole caught a fish");

		createChirp("bob", "eatting a fish dinner with cole");

		Response response = getHead("/chirps/bob",
				MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
		verifyLinkHeaderExists("self", MediaType.APPLICATION_JSON_TYPE,
				response);
		verifyLinkHeaderExists("first", MediaType.APPLICATION_JSON_TYPE,
				response);
		verifyLinkHeaderExists("last", MediaType.APPLICATION_JSON_TYPE,
				response);

	}

}
