package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.PostRepresentation;

public class PostResourceTest extends JerseyResourceTest<PostResource> {

	private UserRepository userRepository = UserRepository.getInstance();

	@Before
	public void testInit() {
		userRepository.clear();
	}

	@Test
	public void createChirpSuccess() {
		
		// need to create without server to honor resource isolation in
		// test framework.
		userRepository.createUser("gordonff", "Gordon Force");

		Form chirpForm = new Form().param("content", "hi unit test");
		Response response = target("/posts/gordonff").request().post(
				Entity.form(chirpForm));
		assertEquals(Response.Status.CREATED.getStatusCode(),
				response.getStatus());

		// You wan't to an object from the server -- User
		// the entity to read is in the previous response's location header
		PostRepresentation postRead = target(response.getLocation().getPath())
				.request().accept(MediaType.APPLICATION_JSON)
				.get(PostRepresentation.class);
		assertEquals("hi unit test", postRead.getContent());

	}

}
