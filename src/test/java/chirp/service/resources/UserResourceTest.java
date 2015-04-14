package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

  private UserRepository users = UserRepository.getInstance();

  @Before
  public void before() {
    users.clear();
  }
  
  @Test 
  public void testCreateWithPost() {
    Form user = new Form().param("realname","Jacob Parr").param("username","jacobp");
    Response response = target("/users").request().post(Entity.form(user));
    
    Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    Assert.assertNotNull(users.getUser("jacobp"));
    
    String location = response.getHeaderString("Location");
    Assert.assertEquals("http://localhost:9998/users/jacobp", location);
  }

  @Test
  public void testCreateDuplicate() {
    Form user = new Form().param("realname","Jacob Parr").param("username","jacobp");
    Response response = target("/users").request().post(Entity.form(user));
    Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    
    response = target("/users").request().post(Entity.form(user));
    Assert.assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
  }
}
