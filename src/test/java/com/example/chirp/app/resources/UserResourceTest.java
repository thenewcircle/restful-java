package com.example.chirp.app.resources;

import com.example.chirp.app.stores.UserStoreUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public void testGetUser() {
        UserStoreUtils.resetAndSeedRepository(getUserStore());

        Response response = target("/users/yoda").request()
                .accept(MediaType.TEXT_PLAIN).get();

        Assert.assertEquals(200, response.getStatus());

        String realname = response.readEntity(String.class);
        Assert.assertEquals("Master Yoda", realname);
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