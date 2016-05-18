package com.example.chirp.app.resources;

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
}






