package chirp.service.resources;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class PostResourceTest extends JerseyResourceTest{

	final private UserRepository userRepository = UserRepository.getInstance();
	
	public PostResourceTest() {
		super(PostResource.class);
	}

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
		testResourceClient.createWithHeadLocationVerify(getDefaultMediaType());
	}		
	
	/**
	 * Use this method to verify one can create a post in an empty repository
	 * and read it back using an XML representation.
	 */
	@Test
	public void getAsXML() {
		testResourceClient.createWithGetLocationVerify(MediaType.APPLICATION_XML_TYPE);
	}
	
	/*
	 * Use this method to verify one can create a post in an empty repository
	 * and read it back using an JSON representation.
	 * 
	 * Currently a no-op as executing causes a circular dependency.
	 */
	@Test
	public void getAsJSON() {
		testResourceClient.createWithGetLocationVerify(MediaType.APPLICATION_JSON_TYPE);
	}

}
