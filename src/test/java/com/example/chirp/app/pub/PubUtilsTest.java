package com.example.chirp.app.pub;

import com.example.chirp.app.PubUtils;
import com.example.chirp.app.kernel.User;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;

public class PubUtilsTest {
    @Test
    public void testToPubUser() {

        User user = new User("mickey", "Mickey Mouse");
        PubUser pubUser = PubUtils.toPubUser(new MockUriInfo(), user, null, "0");

        Assert.assertEquals(user.getUsername(), pubUser.getUsername());
        Assert.assertEquals(user.getRealName(), pubUser.getRealName());

        Assert.assertEquals(URI.create("http://mock.com/users/mickey"), pubUser.getLinks().get("self"));

        Assert.assertEquals(URI.create("http://mock.com/users/mickey/chirps"), pubUser.getLinks().get("chirps"));
    }
}
