package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.PostCollectionRepresentation;
import chirp.service.representations.PostRepresentation;

public class PostResourceTest extends JerseyResourceTest{

	final private UserRepository userRepository = UserRepository.getInstance();
	final private EntityClient<PostRepresentation,PostCollectionRepresentation> pc = new PostResourceClient(this);

	/**
	 * Execute this method before every <em>@Test</em> method to insure the user
	 * repository is empty (expected state).
	 */
	@Before
	public void clearRepository() {
		userRepository.clear();
	}

	/**
	 * Use this method to verify one can create a post in an empty repository.
	 * 
	 */
	@Test
	public void createAPost() {
		pc.createWithHeadLocationVerify(getDefaultMediaType());
		PostCollectionRepresentation posts = pc.getAll(getDefaultMediaType());
		assertEquals(1,posts.getPosts().size());
	}		
	
	/**
	 * Use this method to verify one can create a post in an empty repository
	 * and read it back using an XML representation.
	 */
	@Test
	public void getAsXML() {
		pc.createWithGetLocationVerify(MediaType.APPLICATION_XML_TYPE);
	}
	
	/*
	 * Use this method to verify one can create a post in an empty repository
	 * and read it back using an JSON representation.
	 * 
	 * Currently a no-op as executing causes a circular dependency.
	 */
	@Test
	public void getAsJSON() {
		pc.createWithGetLocationVerify(MediaType.APPLICATION_JSON_TYPE);
	}

}
