package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.PostCollectionRepresentation;
import chirp.service.representations.PostRepresentation;

public class PostResourceTest extends JerseyResourceTest<PostResource> {

	private UserRepository userRepository = UserRepository.getInstance();

	@Before
	public void testInit() {
		userRepository.clear();
	}

	private Response createChirp(String username, String messageContent) {
		Form chirpForm = new Form().param("content", messageContent);
		return postFormData("/post/" + username, chirpForm,
				Response.Status.CREATED);
	}

	@Test
	public void createChirpSuccess() {

		// need to create without server to honor resource isolation in
		// test framework.
		userRepository.createUser("gordonff", "Gordon Force");
		
		Response response = createChirp("gordonff","hi unit test");

		PostRepresentation postRead = readEntity(response.getLocation()
				.getPath(), MediaType.APPLICATION_JSON_TYPE,
				PostRepresentation.class);

		assertNotNull(postRead); // make sure the GET response contains an
									// entity.
		assertEquals("hi unit test", postRead.getContent()); // validate the

	}

	@Test
	public void readPostCollectionSuccess() {
		userRepository.createUser("gordonff", "Gordon Force");
		userRepository.createUser("colef", "Cole Force");

		createChirp("gordonff","fishing with cole");

		createChirp("gordonff","cole caught a fish");

		createChirp("gordonff","eatting a fish dinner with cole");

		for (PostRepresentation post : readEntity("/post/gordonff",
				MediaType.APPLICATION_JSON_TYPE,
				PostCollectionRepresentation.class).getPosts()) {

			getHead(UriBuilder.fromUri(post.getSelf()).build().toString(),
					MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
		}

	}

}
