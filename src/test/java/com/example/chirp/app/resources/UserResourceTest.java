package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.stores.UserStoreUtils;

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
  public void testGetUserPlain() {
    getUserStore().createUser("mickey.mouse", "Mickey Mouse");
    
    Response response = target("/users")
        .path("mickey.mouse")
        .request()
        .accept(MediaType.TEXT_PLAIN)
        .get();
    
    Assert.assertEquals(200, response.getStatus());
    String username = response.readEntity(String.class);
    Assert.assertEquals("Mickey Mouse", username);
  }

  @Test
  public void testGetUserJson() {
    getUserStore().createUser("mickey.mouse", "Mickey Mouse");
    
    Response response = target("/users")
        .path("mickey.mouse")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();
    
    Assert.assertEquals(200, response.getStatus());

    PubUser user = response.readEntity(PubUser.class);
    Assert.assertEquals("mickey.mouse", user.getUsername());
    Assert.assertEquals("Mickey Mouse", user.getRealName());

    URI expectedLink = URI.create("http://asdf.com:999/users/mickey.mouse");
    URI actualLink = response.getLink("self").getUri();
    Assert.assertEquals(expectedLink.getPath(), actualLink.getPath());

    expectedLink = URI.create("http://asdf.com:999/users/mickey.mouse/chirps");
    actualLink = response.getLink("chirps").getUri();
    Assert.assertEquals(expectedLink.getPath(), actualLink.getPath());
  }

  @Test
  public void testGetUserXml() {
    getUserStore().createUser("mickey.mouse", "Mickey Mouse");
    
    Response response = target("/users")
        .path("mickey.mouse")
        .request()
        .accept(MediaType.APPLICATION_XML)
        .get();
    
    Assert.assertEquals(200, response.getStatus());

    PubUser user = response.readEntity(PubUser.class);
    Assert.assertEquals("mickey.mouse", user.getUsername());
    Assert.assertEquals("Mickey Mouse", user.getRealName());

    URI expectedLink = URI.create("http://asdf.com:999/users/mickey.mouse");
    URI actualLink = response.getLink("self").getUri();
    Assert.assertEquals(expectedLink.getPath(), actualLink.getPath());

    expectedLink = URI.create("http://asdf.com:999/users/mickey.mouse/chirps");
    actualLink = response.getLink("chirps").getUri();
    Assert.assertEquals(expectedLink.getPath(), actualLink.getPath());
  }

  @Test
  public void testGetUserXmlExt() {
    getUserStore().createUser("mickey.mouse", "Mickey Mouse");
    
    Response response = target("/users")
        .path("mickey.mouse.xml")
        .request()
        .accept("image/png")
        .get();
    
    Assert.assertEquals(200, response.getStatus());
    
    String contentType = response.getHeaderString("Content-Type");
    Assert.assertEquals(MediaType.APPLICATION_XML, contentType);
  }

  @Test
  public void testGetUserJsonExt() {
    getUserStore().createUser("mickey.mouse", "Mickey Mouse");
    
    Response response = target("/users")
        .path("mickey.mouse.json")
        .request()
        .accept("image/png")
        .get();
    
    Assert.assertEquals(200, response.getStatus());
    
    String contentType = response.getHeaderString("Content-Type");
    Assert.assertEquals(MediaType.APPLICATION_JSON, contentType);
  }

  @Test
  public void testGetUserPlainExt() {
    getUserStore().createUser("mickey.mouse", "Mickey Mouse");
    
    Response response = target("/users")
        .path("mickey.mouse.txt")
        .request()
        .accept("image/png")
        .get();
    
    Assert.assertEquals(200, response.getStatus());
    
    String contentType = response.getHeaderString("Content-Type");
    Assert.assertEquals(MediaType.TEXT_PLAIN, contentType);
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

  @Test
  public void testGetChirps() {
    UserStoreUtils.resetAndSeedRepository(getUserStore());
    
    String username = "jarjar";
    getUserStore().getUser(username);
    
    Response response = target("/users")
        .path(username)
        .path("chirps")
        .request()
        .get();
    
    Assert.assertEquals(200, response.getStatus());
    
    PubChirps pubChirps = response.readEntity(PubChirps.class);
    Assert.assertEquals(5, pubChirps.getItems().size());

    URI actualSelf = response.getLink("self").getUri();
    URI expectedSelf = URI.create("http://whatever.com/users/jarjar/chirps");
    Assert.assertEquals(expectedSelf.getPath(), actualSelf.getPath());

    URI actualUser = response.getLink("user").getUri();
    URI expectedUser = URI.create("http://whatever.com/users/jarjar");
    Assert.assertEquals(expectedUser.getPath(), actualUser.getPath());
  }
}







