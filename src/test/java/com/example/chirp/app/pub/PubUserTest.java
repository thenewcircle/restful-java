package com.example.chirp.app.pub;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class PubUserTest {

	private ObjectMapper objectMapper = new ObjectMapper();
	private XmlMapper xmlMapper = new XmlMapper();

	@Test
	public void testTranslationJson() throws Exception {
		PubUser oldUser = new PubUser("mickey.mouse", "Mickey Mouse");
		String json = objectMapper.writeValueAsString(oldUser);
		PubUser newUser = objectMapper.readValue(json, PubUser.class);

		Assert.assertEquals(oldUser.getUsername(), newUser.getUsername());
		Assert.assertEquals(oldUser.getRealname(), newUser.getRealname());
	}

	@Test
	public void testTranslationXml() throws Exception {

		PubUser oldUser = new PubUser("mickey.mouse", "Mickey Mouse");
		String json = xmlMapper.writeValueAsString(oldUser);
		PubUser newUser = xmlMapper.readValue(json, PubUser.class);

		Assert.assertEquals(oldUser.getUsername(), newUser.getUsername());
		Assert.assertEquals(oldUser.getRealname(), newUser.getRealname());
	}
}
