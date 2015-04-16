package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.representations.ChirpRep;

public class ChirpsResourceTest extends JerseyResourceTest {

  private UserRepository users = UserRepository.getInstance();

  @Before
  public void before() {
    users.clear();
  }

  @Test
  public void testCreateChirp() throws Exception {

    String msg = "Hello peoples.";

    // Create message without a user
    Response response = target("/users/jacobp/chirps").request().post(Entity.text(msg));
    Assert.assertEquals(response.getStatus(), 404);

    // Create the user
    Form user = new Form().param("realname","Jacob Parr").param("username","jacobp");
    response = target("/users").request().post(Entity.form(user));
    Assert.assertEquals(response.getStatus(), 201);

    // Create the user's chirp
    response = target("/users/jacobp/chirps").request().post(Entity.text(msg));
    Assert.assertEquals(response.getStatus(), 201);
    String location = response.getHeaderString("Location");
    Assert.assertTrue(location.startsWith("http://localhost:9998/chirps/"));

    // Now fetch the chirp we just created and verify it's message
    location = location.substring(21);
    ChirpRep chirp = target(location).request().get(ChirpRep.class);
    Assert.assertEquals(chirp.getContent(), msg);
  }
}