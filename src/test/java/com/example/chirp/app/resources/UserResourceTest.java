package com.example.chirp.app.resources;

import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.ExceptionInfo;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.stores.UserStoreUtils;
import org.junit.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

public class UserResourceTest extends ResourceTestSupport {

    @Test
    public void testCreateUser() {
        String username = "student";
        String realName = "Bob Student";

        Form userForm = new Form().param("realName", realName);
        Entity entity = Entity.form(userForm);

        Response response = target("users")
                .path(username)
                .request()
                .put(entity);

        Assert.assertEquals(201, response.getStatus());

        User user = getUserStore().getUser(username);
        Assert.assertNotNull(user);

        URI location = response.getLocation();
        Assert.assertEquals("/users/student", location.getPath());

        Link link = response.getLink("self");
        Assert.assertEquals("/users/student", link.getUri().getPath());

        response = target(link.getUri().getPath())
                .request()
                .accept(MediaType.TEXT_PLAIN)
                .get();

        Assert.assertEquals(200, response.getStatus());
        String name = response.readEntity(String.class);
        Assert.assertEquals(realName, name); // Bob Student
    }

    @Test
    public void testGetUserPlain() {
        getUserStore().clear();
        getUserStore().createUser("mickey", "Mickey Mouse");

        Response response = target("/users")
                .path("mickey")
                .request()
                .accept(MediaType.TEXT_PLAIN)
                .get();

        Assert.assertEquals(200, response.getStatus());
        String name = response.readEntity(String.class);
        Assert.assertEquals("Mickey Mouse", name); // Bob Student

        Link link = response.getLink("self");
        Assert.assertEquals("/users/mickey", link.getUri().getPath());
    }

    @Test
    public void testGetUserJson() {
        getUserStore().clear();
        getUserStore().createUser("mickey", "Mickey Mouse");

        Response response = target("/users")
                .path("mickey")
                .request()
                // .accept(MediaType.TEXT_PLAIN)
                .accept(MediaType.APPLICATION_JSON)
                .get();

        Assert.assertEquals(200, response.getStatus());

        String content = response.getHeaderString("Content-Type");
        Assert.assertEquals(MediaType.APPLICATION_JSON, content);

        PubUser pubUser = response.readEntity(PubUser.class);
        Assert.assertEquals("Mickey Mouse", pubUser.getRealName());
        Assert.assertEquals("mickey", pubUser.getUsername());

        Link link = response.getLink("self");
        Assert.assertEquals("/users/mickey", link.getUri().getPath());

        URI uri = pubUser.getLinks().get("self");
        Assert.assertEquals("/users/mickey", uri.getPath());

        uri = pubUser.getLinks().get("chirps");
        Assert.assertEquals("/users/mickey/chirps", uri.getPath());
    }

    @Test
    public void testGetUserXml() {
        getUserStore().createUser("mickey", "Mickey Mouse");

        Response response = target("/users")
                .path("mickey")
                .request()
                // .accept(MediaType.TEXT_PLAIN)
                .accept(MediaType.APPLICATION_XML)
                .get();

        Assert.assertEquals(200, response.getStatus());

        String content = response.getHeaderString("Content-Type");
        Assert.assertEquals(MediaType.APPLICATION_XML, content);

        PubUser pubUser = response.readEntity(PubUser.class);
        Assert.assertEquals("Mickey Mouse", pubUser.getRealName());
        Assert.assertEquals("mickey", pubUser.getUsername());

        Link link = response.getLink("self");
        Assert.assertEquals("/users/mickey", link.getUri().getPath());

        URI uri = pubUser.getLinks().get("self");
        Assert.assertEquals("/users/mickey", uri.getPath());

        uri = pubUser.getLinks().get("chirps");
        Assert.assertEquals("/users/mickey/chirps", uri.getPath());
    }

    @Test
    public void testGetWrongUser() {
        // getUserStore().createUser("mickey", "Mickey Mouse");

        Response response = target("/users")
                .path("goofy")
                .request()
                .accept(MediaType.TEXT_PLAIN)
                .get();

        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void testCreateDuplicateUser() {
        getUserStore().createUser("donald", "Donald Duck");

        Form userForm = new Form().param("realName", "Whatever");
        Entity entity = Entity.form(userForm);

        Response response = target("users")
                .path("donald")
                .request()
                .put(entity);

        Assert.assertEquals(403, response.getStatus());
    }

    @Test
    public void testCreateWithBadName() {
        // getUserStore().createUser("donald", "Donald Duck");

        Form userForm = new Form().param("realName", "Daisy Duck");
        Entity entity = Entity.form(userForm);

        Response response = target("users")
                .path("daisy duck")
                .request()
                .put(entity);

        Assert.assertEquals(400, response.getStatus());

        ExceptionInfo info = response.readEntity(ExceptionInfo.class);
        Assert.assertEquals("The specified username must not contain spaces.", info.getMessage());
    }

    @Test
    public void testGetUserTxtExt() {
        UserStoreUtils.resetAndSeedRepository(getUserStore());

        Response response = target("/users/yoda.txt")
                .request().accept("image/png").get();

        Assert.assertEquals(200, response.getStatus());

        MediaType type = response.getMediaType();
        Assert.assertEquals(MediaType.TEXT_PLAIN_TYPE, type);
    }

    @Test
    public void testGetUserJsonExt() {
        UserStoreUtils.resetAndSeedRepository(getUserStore());

        Response response = target("/users/yoda.json")
                .request().accept("image/png").get();

        Assert.assertEquals(200, response.getStatus());

        MediaType type = response.getMediaType();
        Assert.assertEquals(MediaType.APPLICATION_JSON_TYPE, type);
    }

    @Test
    public void testGetUserXmlExt() {
        UserStoreUtils.resetAndSeedRepository(getUserStore());

        Response response = target("/users/yoda.xml")
                .request().accept("image/png").get();

        Assert.assertEquals(200, response.getStatus());

        MediaType type = response.getMediaType();
        Assert.assertEquals(MediaType.APPLICATION_XML_TYPE, type);
    }
}