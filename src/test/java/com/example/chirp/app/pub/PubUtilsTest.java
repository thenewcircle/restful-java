package com.example.chirp.app.pub;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;

import com.example.chirp.app.kernel.User;

public class PubUtilsTest {

  @Test  
  public void testToPubUser() {
    User user = new User("mickey", "Mickey Mouse");
    PubUser pubUser = PubUtils.toPubUser(new MockUriInfo(), user);

    Assert.assertEquals(user.getUsername(), pubUser.getUsername());
    Assert.assertEquals(user.getRealName(), pubUser.getRealName());

    Assert.assertEquals(URI.create("http://mock.com/users/mickey"),
                        pubUser.getLinks().get("self"));

    Assert.assertEquals(URI.create("http://mock.com/users/mickey/chirps"),
                        pubUser.getLinks().get("chirps"));
  }
}