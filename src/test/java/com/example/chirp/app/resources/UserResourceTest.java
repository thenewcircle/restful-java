package com.example.chirp.app.resources;

import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.stores.UserStoreUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

public class UserResourceTest extends ResourceTestSupport {

    @Before
    public void before() {
        getUserStore().clear();
    }

    @Test
    public void testCreateWithBadName() {
        Form user = new Form().param("realName", "Minnie Mouse");
        Entity entity = Entity.form(user);

        Response response = target("/users").path("minnie mouse").request().put(entity);

        Assert.assertEquals(400, response.getStatus());
    }


    @Test
    public void testCreateUser() {
        String username = "student";
        String realName = "Bob Student";

        Form user = new Form().param("realName", realName);
        Entity entity = Entity.form(user);

        Response response = target("/users").path(username).request().put(entity);

        Assert.assertEquals(201, response.getStatus());
        Assert.assertNotNull(getUserStore().getUser(username));

        String location = response.getHeaderString("Location");
        Assert.assertEquals("http://localhost:9998/users/student", location);

        String shortLocation = location.substring(21);
        Assert.assertEquals("/users/student", shortLocation);
        response = target(shortLocation).request().accept(MediaType.TEXT_PLAIN).get();
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetWrongUser() {
        Response response = target("/users/donald.duck").request()
                .accept(MediaType.TEXT_PLAIN).get();

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void testGetUserJson() {
        UserStoreUtils.resetAndSeedRepository(getUserStore());

        Response response = target("/users/yoda").request().accept(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(200, response.getStatus());

        PubUser pubUser = response.readEntity(PubUser.class);
        Assert.assertEquals("Master Yoda", pubUser.getRealName());
        Assert.assertEquals("yoda", pubUser.getUsername());

        URI selfLink = URI.create("http://localhost:9998/users/yoda");
        Assert.assertEquals(selfLink, pubUser.getLinks().get("self"));
        Assert.assertEquals(selfLink, response.getLink("self").getUri());

        URI chirpsLink = URI.create("http://localhost:9998/users/yoda/chirps");
        Assert.assertEquals(chirpsLink, pubUser.getLinks().get("chirps"));
        Assert.assertEquals(chirpsLink, response.getLink("chirps").getUri());
    }

    @Test
    public void testGetUserPlain() {
        UserStoreUtils.resetAndSeedRepository(getUserStore());

        Response response = target("/users/yoda").request().accept(MediaType.TEXT_PLAIN).get();
        Assert.assertEquals(200, response.getStatus());

        String realName = response.readEntity(String.class);
        Assert.assertEquals("Master Yoda", realName);
    }

    @Test
    public void testCreateDuplicateUser() {
        Form user = new Form().param("realName", "Mickey Mouse");
        Entity entity = Entity.form(user);

        Response response = target("/users").path("mickey").request().put(entity);
        Assert.assertEquals(201, response.getStatus());

        response = target("/users").path("mickey").request().put(entity);
        Assert.assertEquals(403, response.getStatus());
    }
}