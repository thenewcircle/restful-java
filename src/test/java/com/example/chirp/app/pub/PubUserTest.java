package com.example.chirp.app.pub;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class PubUserTest {

	@Test
	public void testTranslation() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();

		PubUser oldUser = new PubUser(URI.create("http://whatever"), "mickey", "Mickey Mouse");

		String json = mapper.writeValueAsString(oldUser);
		// System.out.println(json);
		
		PubUser newUser = mapper.readValue(json, PubUser.class);

		Assert.assertEquals(oldUser.getUsername(), newUser.getUsername());
		Assert.assertEquals(oldUser.getRealName(), newUser.getRealName());
	}

	@Test
	public void testTranslationXml() throws Exception {
		
		XmlMapper mapper = new XmlMapper();

		PubUser oldUser = new PubUser(URI.create("http://whatever"), "mickey", "Mickey Mouse");

		String xml = mapper.writeValueAsString(oldUser);
		// System.out.println(xml);
		
		PubUser newUser = mapper.readValue(xml, PubUser.class);

		Assert.assertEquals(oldUser.getUsername(), newUser.getUsername());
		Assert.assertEquals(oldUser.getRealName(), newUser.getRealName());
	}
	
}
