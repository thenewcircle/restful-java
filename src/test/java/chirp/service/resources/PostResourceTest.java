package chirp.service.resources;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import chirp.model.Post;
import chirp.model.UserRepository;

public class PostResourceTest extends JerseyResourceTest{

	final private UserRepository userRepository = UserRepository.getInstance();
	final private EntityClient<Post> pc = new PostResourceClient(this);

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
		pc.createWithHeadLocationVerify(MediaType.APPLICATION_XML_TYPE);
		Collection<Post> posts = pc.getAll(MediaType.APPLICATION_XML_TYPE);
		assertEquals(1,posts.size());
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
	@Test
	public void getAsJSON() {
		pc.createWithGetLocationVerify(MediaType.APPLICATION_JSON_TYPE);
	}
	 */

}
