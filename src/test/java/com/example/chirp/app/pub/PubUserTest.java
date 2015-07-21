package com.example.chirp.app.pub;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PubUserTest {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testTranslation() throws Exception {

		PubUser oldUser = new PubUser("mickey.mouse", "Mickey Mouse");
		String json = objectMapper.writeValueAsString(oldUser);
		PubUser newUser = objectMapper.readValue(json, PubUser.class);

		Assert.assertEquals(oldUser.getUsername(), newUser.getUsername());
		Assert.assertEquals(oldUser.getRealname(), newUser.getRealname());
	}
}
