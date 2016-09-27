package com.example.chirp.app.resources;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class RootResourceTest extends ResourceTestSupport {

  @Test
  public void rootResourceTest() {
    Response response = target().request().get();
    assertEquals(200, response.getStatus());
  }
}
