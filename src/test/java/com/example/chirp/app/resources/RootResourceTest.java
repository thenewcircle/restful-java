package com.example.chirp.app.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootResourceTest extends ResourceTestSupport {

    @Test
    public void rootResourceTest() {
        Response response = target("/").request().get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testSayHello() {
        Response response = target("/greetings").request().get();
        Assert.assertEquals(200, response.getStatus());

        String text = response.readEntity(String.class);
        Assert.assertEquals("Hello dude!", text);
    }

    @Test
    public void testSayHelloWithQueryParam() {

        Response response = target("/greetings")
                .queryParam("name", "Tom")
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());

        String text = response.readEntity(String.class);
        Assert.assertEquals("Hello Tom!", text);
    }

    @Test
    public void testSayHelloWithPathParm() {

        // http://localhost:9998/grettings/Tom
        Response response = target("/greetings")
                .path("Tom")
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());

        String text = response.readEntity(String.class);
        Assert.assertEquals("Hello Tom!", text);
    }

    @Test
    public void testEchoHeader() {
        // SDK sdk = new SDK(target("/greetings"));
        Response response = target("/greetings")
                .request()
                .header("X-NewCircle-Echo", "Tom")
                .get();

        Assert.assertEquals(200, response.getStatus());

        String headerText = response.getHeaderString("X-NewCircle-Echo-Response");
        Assert.assertEquals("Tom, Tom, Tom", headerText);
    }
}










