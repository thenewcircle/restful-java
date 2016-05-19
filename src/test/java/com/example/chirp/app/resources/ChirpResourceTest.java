package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.pub.PubChirp;

public class ChirpResourceTest extends ResourceTestSupport {

  @Before
  public void before() {
    // getUserStore().clear();
  }

  @Test
  public void testGetChirpJSON() throws Exception {
    testGetChirp(MediaType.APPLICATION_JSON);
  }

  @Test
  public void testGetChirpXML() throws Exception {
    testGetChirp(MediaType.APPLICATION_XML);
  }

  private void testGetChirp(String mediaType) throws Exception {
    
    Response response = target("chirps")
        .path("wars01")
        .request()
        .accept(mediaType)
        .get();
    
    Assert.assertEquals(200, response.getStatus());
    Assert.assertEquals(mediaType, response.getHeaderString("Content-Type"));
    
    PubChirp chirp = response.readEntity(PubChirp.class);
    Assert.assertEquals("wars01", chirp.getId());
    Assert.assertEquals("Do or do not. There is no try.", chirp.getContent());
  
    URI selfLink = URI.create("http://asdf.com/chirps/wars01");
    Assert.assertEquals(selfLink.getPath(), chirp.getLinks().get("self").getPath());
    Assert.assertEquals(selfLink.getPath(), response.getLink("self").getUri().getPath());

    URI chirpsLink = URI.create("http://asdf.com/users/yoda/chirps");
    Assert.assertEquals(chirpsLink.getPath(), chirp.getLinks().get("chirps").getPath());
    Assert.assertEquals(chirpsLink.getPath(), response.getLink("chirps").getUri().getPath());

    URI userLink = URI.create("http://asdf.com/users/yoda");
    Assert.assertEquals(userLink.getPath(), chirp.getLinks().get("user").getPath());
    Assert.assertEquals(userLink.getPath(), response.getLink("user").getUri().getPath());    
  }

  @Test
  public void testCreateChirp() {
    
    Entity<String> entity = Entity.text("Test I will.");
    Response response = target("/users/yoda/chirps")
        .request()
        .post(entity);
    
    Assert.assertEquals(201, response.getStatus());
    
    PubChirp pubChirp = response.readEntity(PubChirp.class);
    
    String id = pubChirp.getId();
    URI actualLocation = response.getLocation();
    URI expectedLocation = URI.create("http://asdf.com/chirps/" + id);
    Assert.assertEquals(expectedLocation.getPath(), actualLocation.getPath());

    Chirp chirp = getUserStore().getChirp(id);
    Assert.assertNotNull(chirp);
    
    Assert.assertEquals("Test I will.", chirp.getContent());

    URI selfLink = URI.create("http://asdf.com/chirps/" + id);
    Assert.assertEquals(selfLink.getPath(), pubChirp.getLinks().get("self").getPath());
    Assert.assertEquals(selfLink.getPath(), response.getLink("self").getUri().getPath());

    URI chirpsLink = URI.create("http://asdf.com/users/yoda/chirps");
    Assert.assertEquals(chirpsLink.getPath(), pubChirp.getLinks().get("chirps").getPath());
    Assert.assertEquals(chirpsLink.getPath(), response.getLink("chirps").getUri().getPath());

    URI userLink = URI.create("http://asdf.com/users/yoda");
    Assert.assertEquals(userLink.getPath(), pubChirp.getLinks().get("user").getPath());
    Assert.assertEquals(userLink.getPath(), response.getLink("user").getUri().getPath());    
  }
}






