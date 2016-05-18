package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserResourceTest extends ResourceTestSupport {

  @Before
  public void before() {
    getUserStore().clear();
  }

  @Test
  public void testCreateUser() {
    String username = "student";
    String realName = "Bob Student";

    Form user = new Form().param("realName", realName);
    Entity entity = Entity.form(user);

    Response response = target("/users")
                        .path(username)
                        .request()
                        .put(entity);

    String location = response.getHeaderString("Location");
    String msg = "Found " + location;
    Assert.assertTrue(msg, location.endsWith("/users/student"));
    
    Assert.assertEquals(201, response.getStatus());
    Assert.assertNotNull(getUserStore().getUser(username));

    URI uri = URI.create(location);
    String path = uri.getPath();
    
    response = target(path)
        .request()
        .get();
    
    Assert.assertEquals(200, response.getStatus());
    
  
  }

  @Test
  public void testCreateWithBadName() {
    String username = "minnie mouse";
    String realName = "Minnie Mouse";

    Form user = new Form().param("realName", realName);
    Entity entity = Entity.form(user);

    Response response = target("/users")
                        .path(username)
                        .request()
                        .put(entity);

    Assert.assertEquals(400, response.getStatus());
  }

  @Test
  public void testGetUser() {
    getUserStore().createUser("mickey.mouse", "Mickey Mouse");
    
    Response response = target("/users")
        .path("mickey.mouse")
        .request()
        .get();
    
    Assert.assertEquals(200, response.getStatus());
    String username = response.readEntity(String.class);
    Assert.assertEquals("Mickey Mouse", username);
  }

  @Test
  public void testGetNonExistingUser() {
    Response response = target("/users")
        .path("donald.duck")
        .request()
        .get();
    
    Assert.assertEquals(404, response.getStatus());
  }

  @Test
  public void testCreateDuplicateUser() {
    String username = "student";
    String realName = "Bob Student";

    Form user = new Form().param("realName", realName);
    Entity entity = Entity.form(user);

    Response response = target("/users")
                        .path(username)
                        .request()
                        .put(entity);
    Assert.assertEquals(201, response.getStatus());
    
    response = target("/users")
        .path(username)
        .request()
        .put(entity);
    Assert.assertEquals(403, response.getStatus());
  }
}







