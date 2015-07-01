package com.example.chirp.pub;

import java.net.URI;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class PubUsersTest {

	@Test
	public void testDeserializeUsers() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper(); 
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		URI userLnk = URI.create("http://localhost/test/users/mickey.mouse");
		URI parent = URI.create("http://localhost/test/users");
		
		PubUser user = new PubUser(userLnk, parent, "mickey.mouse", "Mickey Mouse");
		String json = objectMapper.writeValueAsString(user);
		System.out.println(json);

		user = new PubUser(userLnk, parent, "mickey.mouse", "Mickey Mouse", 
				URI.create("http://localhost/chirps/1001"),
				URI.create("http://localhost/chirps/1002"),
				URI.create("http://localhost/chirps/1003"));
		json = objectMapper.writeValueAsString(user);
		System.out.println(json);

		
		user = new PubUser(userLnk, parent, "mickey.mouse", "Mickey Mouse", 
				new PubChirp(URI.create("http://localhost/chirps/1001"), userLnk, "1001", "I like cheese"),
				new PubChirp(URI.create("http://localhost/chirps/1002"), userLnk, "1002", "My fee are black"),
				new PubChirp(URI.create("http://localhost/chirps/1003"), userLnk, "1003", "Goofy is my friend"));
		json = objectMapper.writeValueAsString(user);
		System.out.println(json);
	}
}
