package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.PostCollectionRepresentation;
import chirp.service.representations.PostRepresentation;

public class PostResourceTest extends JerseyResourceTest {

	private UserRepository userRepository = UserRepository.getInstance();

	@Before
	public void testInit() {
		userRepository.clear();
	}

	private Response createChirp(String username, String messageContent) {
		Form chirpForm = new Form().param("content", messageContent);
		return postFormData("/posts/" + username, chirpForm,
				Response.Status.CREATED);
	}

	@Test
	public void createChirpSuccess() {

		log.info("Test: Create a post for the gordonff user and verify its content");

		// need to create without server to honor resource isolation in
		// test framework.
		userRepository.createUser("gordonff", "Gordon Force");

		Response response = createChirp("gordonff", "unit test success?");

		response = getEntity(response.getLocation(),
				MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);

		PostRepresentation postRead = readEntity(response,
				PostRepresentation.class);

		assertNotNull(postRead); // make sure the GET response contains an
									// entity.

		assertEquals("unit test success?", postRead.getContent()); // validate
																	// the

		verifyLinkHeaderExists("up", MediaType.APPLICATION_JSON_TYPE, response);
		verifyLinkHeaderExists("self", MediaType.APPLICATION_JSON_TYPE,
				response);
		verifyLinkHeaderExists("related", MediaType.APPLICATION_JSON_TYPE,
				response);

	}

	@Test
	public void readPostCollectionSuccess() {

		log.info("Test: Creating a collection of three posts for one user should succeed with verification of structural self link location.");

		userRepository.createUser("gordonff", "Gordon Force");
		userRepository.createUser("colef", "Cole Force");

		createChirp("gordonff", "fishing with cole");

		createChirp("colef", "bored waiting for a fish");

		createChirp("gordonff", "cole caught a fish");

		createChirp("colef", "fish guts are gross");

		createChirp("gordonff", "eatting a fish dinner with cole");

		for (PostRepresentation post : readEntity("/posts/gordonff",
				MediaType.APPLICATION_JSON_TYPE,
				PostCollectionRepresentation.class).getPosts()) {

			getHead(post.getSelf(), MediaType.APPLICATION_JSON_TYPE,
					Response.Status.OK);
		}

	}

	@Test
	public void verifyPostCollectionLinkHeaders() {

		log.info("Test: Creating a collection of three posts for one user should succeed with link header verification on location only.");

		userRepository.createUser("gordonff", "Gordon Force");

		createChirp("gordonff", "fishing with cole");

		createChirp("gordonff", "cole caught a fish");

		createChirp("gordonff", "eatting a fish dinner with cole");

		Response response = getHead("/posts/gordonff",
				MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
		verifyLinkHeaderExists("self", MediaType.APPLICATION_JSON_TYPE,
				response);
		verifyLinkHeaderExists("first", MediaType.APPLICATION_JSON_TYPE,
				response);
		verifyLinkHeaderExists("last", MediaType.APPLICATION_JSON_TYPE,
				response);

	}

}
