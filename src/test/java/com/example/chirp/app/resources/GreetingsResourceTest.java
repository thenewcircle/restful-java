package com.example.chirp.app.resources;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class GreetingsResourceTest extends ResourceTestSupport {

  @Test
  public void testSayHello() throws Exception {
    Response response = target("/greetings").request().get();
    
    int status = response.getStatus();
    Assert.assertEquals(200, status);
    
    String text = response.readEntity(String.class);
    Assert.assertEquals("Hello Dude!", text);
  }
  
  @Test
  public void testSayHelloWithQueryParam() throws Exception {
    Response response = target("/greetings")
        .queryParam("name", "Tom")
        .request()
        .get();
    
    int status = response.getStatus();
    Assert.assertEquals(200, status);
    
    String text = response.readEntity(String.class);
    Assert.assertEquals("Hello Tom!", text);
  }

  @Test
  public void testSayHelloWithPathParam() throws Exception {
    String name = "Tom";
    
    // Response response = target("/greetings/"+name)
    Response response = target("/greetings")
        .path(name) // .path("/some").path("this/and/that")
        // .queryParam("name", "Tom")
        .request()
        .get();
    
    int status = response.getStatus();
    Assert.assertEquals(200, status);
    
    String text = response.readEntity(String.class);
    Assert.assertEquals("Hello Tom!", text);
  }


  @Test
  public void testEchoHeader() throws Exception {
    String name = "Tom";
    
    // Response response = target("/greetings/"+name)
    Response response = target("/greetings")
        .request()
        .header("X-NewCircle-Echo", name)
        .get();
    
    int status = response.getStatus();
    Assert.assertEquals(200, status);
    
    String text = response.getHeaderString("X-NewCircle-Echo-Response");
    Assert.assertEquals("Tom Tom Tom", text);
  }

}




