package com.example.chirp.app.resources;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class GreetingsResourceTest extends ResourceTestSupport {

    @Test
    public void testSayHello() {
        Response response = target("/greetings").request().get();
        Assert.assertEquals(200, response.getStatus());

        String text = response.readEntity(String.class);
        Assert.assertEquals("Hello dude!", text);
    }

    @Test
    public void testSayHelloWithQueryParam() {
        Response response = target("/greetings").queryParam("name", "Tom").request().get();
        Assert.assertEquals(200, response.getStatus());

        String text = response.readEntity(String.class);
        Assert.assertEquals("Hello Tom!", text);
    }

    @Test
    public void testSayHelloWithPathParam() {
        // Response response = target("/greetings").path("Mary").request().get();
        // Response response = target("/greetings/Mary").request().get();
        Response response = target().path("greetings").path("Mary").request().get();

        Assert.assertEquals(200, response.getStatus());

        String text = response.readEntity(String.class);
        Assert.assertEquals("Hello Mary!", text);
    }
}

















