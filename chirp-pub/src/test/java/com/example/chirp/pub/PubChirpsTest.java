package com.example.chirp.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class PubChirpsTest {

	@Test
	public void testDeserializePubChirp() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper(); 
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		
		URI selfLnk = URI.create("http://localhost/test/users/mickey.mouse/chirps");

		List<PubChirp> chirps = new ArrayList<>();
		chirps.add(new PubChirp(URI.create("http://localhost/test/chirps/1001"), 
				                "1001", "I like cheese"));
		chirps.add(new PubChirp(URI.create("http://localhost/test/chirps/1002"), 
				                "1002", "My shoes are black"));
		chirps.add(new PubChirp(URI.create("http://localhost/test/chirps/1003"), 
				                "1003", "Are we having fun yet?"));
		
		PubChirps oldChirps = new PubChirps(selfLnk, 0, 4, chirps);
		String json = objectMapper.writeValueAsString(oldChirps);

		System.out.println(json);
		System.out.println();
		System.out.println();
		
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

		String xml = xmlMapper.writeValueAsString(oldChirps);
		System.out.println(xml);
	}
}
