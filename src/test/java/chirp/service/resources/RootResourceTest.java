package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;

import org.junit.Test;

public class RootResourceTest extends JerseyResourceTest {

	@Test
	public void rootResourceMustReturnOk() {
		Response response = target("/").request().get();
		assertEquals(200, response.getStatus());
		String root = response.readEntity(String.class);
		assertTrue(root.matches("As of .*, everything is OK."));
	}

}
