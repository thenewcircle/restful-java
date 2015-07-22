package com.example.chirp.app.pub;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class PubChirpTest {

	@Test
	public void testJsonTranslation() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		PubChirp oldChirp = new PubChirp("101", "This is my message", URI.create("http://whatever/a"), URI.create("http://whatever/b"),
				URI.create("http://whatever/c"));

		String json = objectMapper.writeValueAsString(oldChirp);
		PubChirp newChirp = objectMapper.readValue(json, PubChirp.class);

		Assert.assertEquals(oldChirp, newChirp);
	}

	@Test
	public void testXmlTranslation() throws Exception {

		XmlMapper objectMapper = new XmlMapper();

		PubChirp oldChirp = new PubChirp("101", "This is my message", URI.create("http://whatever/a"), URI.create("http://whatever/b"),
				URI.create("http://whatever/c"));

		String json = objectMapper.writeValueAsString(oldChirp);
		PubChirp newChirp = objectMapper.readValue(json, PubChirp.class);

		Assert.assertEquals(oldChirp, newChirp);
	}
}
