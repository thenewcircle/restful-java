package chirp.service.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	@Before
	public void resetAndSeedRepository() {
		UserRepository database = UserRepository.getInstance();
		database.clear();
		database.createUser("maul", "Darth Maul");
		database.createUser("luke", "Luke Skywaler");
		database.createUser("vader", "Darth Vader");
		database.createUser("yoda", "Master Yoda");
		database.getUser("yoda").createChirp("Do or do not.  There is no try.", "wars01");
		database.getUser("yoda").createChirp("Fear leads to anger, anger leads to hate, and hate leads to suffering.","wars02");
		database.getUser("vader").createChirp("You have failed me for the last time.", "wars03");
	}
	
	@Test
	public void getUserAsText() {
		Response response = target("/users").path("yoda").request().header("Accept", MediaType.TEXT_PLAIN).get();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		String text = response.readEntity(String.class);
		assertEquals("User [username=yoda]", text);
	}

}
