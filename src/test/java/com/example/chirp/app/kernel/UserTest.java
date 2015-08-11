package com.example.chirp.app.kernel;

import org.junit.Assert;
import org.junit.Test;

import com.example.chirp.app.pub.PubUser;

public class UserTest {

	@Test
	public void testToPubUser() {
		
		User user = new User("mickey", "Mickey Mouse");
		PubUser pubUser = user.toPubUser();
		
		Assert.assertEquals(user.getUsername(), pubUser.getUsername());
		Assert.assertEquals(user.getRealName(), pubUser.getRealName());
		
	}
	
}
