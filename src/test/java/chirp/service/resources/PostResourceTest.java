package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.PostRepresentation;
import chirp.service.representations.UserRepresentation;

public class PostResourceTest extends JerseyResourceTest<PostsResource> {

	@Before
	public void setup() {
		UserRepository.getInstance().clear();
	}
	
	private Response createChirp(String username, String content)
	{
		Form chirpForm = new Form().param("content", content);
		
		logger.info("Creating chirp from={} with content={} via POST", username, content);

		return postFormData("/posts/"+username, chirpForm, Response.Status.CREATED);

	}

	@Test
	public void createChirpSuccess() {

		UserRepository.getInstance().createUser("gordonff", "Gordon Force");
		
		Response response = createChirp("gordonff", "test message chirp");

		// You wan't to an object from the server -- User
		// the entity to read is in the previous response's location header
		
		response = getEntity(response.getLocation(),
				MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
		
		logger.info("Read user entity from the resposne");
		PostRepresentation post = readEntity(response, PostRepresentation.class);

		assertNotNull(post);
		assertEquals("test message chirp", post.getContent());

	}

	
}
