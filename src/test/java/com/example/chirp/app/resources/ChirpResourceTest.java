package com.example.chirp.app.resources;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.pub.PubChirp;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

public class ChirpResourceTest extends ResourceTestSupport {

    @Test
    public void testCreateChirp() {

        Entity<String> entity = Entity.text("Test I will.");
        Response response = target("/users/yoda/chirps").request().post(entity);

        Assert.assertEquals(201, response.getStatus());

        Chirp chirp = getUserStore().getUser("yoda").getChirps().getFirst();
        Assert.assertEquals("Test I will.", chirp.getContent());

        PubChirp pubChirp = response.readEntity(PubChirp.class);

        URI selfLink = URI.create("http://localhost:9998/chirps/" + chirp.getId());
        Assert.assertEquals(selfLink, pubChirp.getLinks().get("self"));
        Assert.assertEquals(selfLink, response.getLink("self").getUri());

        URI chirpsLink = URI.create("http://localhost:9998/users/yoda/chirps");
        Assert.assertEquals(chirpsLink, pubChirp.getLinks().get("chirps"));
        Assert.assertEquals(chirpsLink, response.getLink("chirps").getUri());

        URI userLink = URI.create("http://localhost:9998/users/yoda");
        Assert.assertEquals(userLink, pubChirp.getLinks().get("user"));
        Assert.assertEquals(userLink, response.getLink("user").getUri());


        // next follow the location to make sure it is right


        String location = response.getHeaderString("Location");
        Assert.assertEquals("http://localhost:9998/chirps/"+chirp.getId(), location);

        String shortLocation = location.substring(21);
        response = target(shortLocation).request().get();
        pubChirp = response.readEntity(PubChirp.class);
        Assert.assertEquals("Test I will.", pubChirp.getContent());

        selfLink = URI.create("http://localhost:9998/chirps/" + chirp.getId());
        Assert.assertEquals(selfLink, pubChirp.getLinks().get("self"));
        Assert.assertEquals(selfLink, response.getLink("self").getUri());

        chirpsLink = URI.create("http://localhost:9998/users/yoda/chirps");
        Assert.assertEquals(chirpsLink, pubChirp.getLinks().get("chirps"));
        Assert.assertEquals(chirpsLink, response.getLink("chirps").getUri());

        userLink = URI.create("http://localhost:9998/users/yoda");
        Assert.assertEquals(userLink, pubChirp.getLinks().get("user"));
        Assert.assertEquals(userLink, response.getLink("user").getUri());
    }

    @Test
    public void testGetChirp() {

        Response response = target("/chirps/wars01").request().accept(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());

        PubChirp pubChirp = response.readEntity(PubChirp.class);

        Assert.assertEquals(pubChirp.getId(), "wars01");
        Assert.assertEquals(pubChirp.getContent(), "Do or do not. There is no try.");

        URI selfLink = URI.create("http://localhost:9998/chirps/wars01");
        Assert.assertEquals(selfLink, pubChirp.getLinks().get("self"));
        Assert.assertEquals(selfLink, response.getLink("self").getUri());

        URI chirpsLink = URI.create("http://localhost:9998/users/yoda/chirps");
        Assert.assertEquals(chirpsLink, pubChirp.getLinks().get("chirps"));
        Assert.assertEquals(chirpsLink, response.getLink("chirps").getUri());

        URI userLink = URI.create("http://localhost:9998/users/yoda");
        Assert.assertEquals(userLink, pubChirp.getLinks().get("user"));
        Assert.assertEquals(userLink, response.getLink("user").getUri());
    }
}
