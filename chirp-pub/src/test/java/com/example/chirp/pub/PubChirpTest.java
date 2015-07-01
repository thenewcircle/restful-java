package com.example.chirp.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PubChirpTest {

	@Test
	public void testDeserialization() throws Exception {
		URI userLnk = URI.create("http://localhost/test/users/mickey.mouse");
		List<PubChirp> chirps = new ArrayList<>();
		
		URI self = URI.create("http://localhost/test/chirps/1001");
		chirps.add(new PubChirp(self, userLnk, "1001", "I like cheese"));

		self = URI.create("http://localhost/test/chirps/1002");
		chirps.add(new PubChirp(self, userLnk, "1002", "I have big feet"));
		
		self = URI.create("http://localhost/test/user/mickey.mouse/chirps");
		PubChirps oldChirps = new PubChirps(self, chirps);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(oldChirps);
		
		PubChirps newChirps = objectMapper.readValue(json, PubChirps.class);
		
		Assert.assertEquals(oldChirps.getSelf(), newChirps.getSelf());
		Assert.assertEquals(oldChirps.getChirps().size(), newChirps.getChirps().size());
	
	}
	
	@Test
	public void testFormatPubChirp() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper(); 
		
		URI selfLnk = URI.create("http://localhost/test/chirps/123");
		URI userLnk = URI.create("http://localhost/test/users/mickey.mouse");

		PubChirp oldChirp = new PubChirp(selfLnk, userLnk, "123", "I like you a lot");
		String json = objectMapper.writeValueAsString(oldChirp);
		
		// {"self":"http://localhost/test/chirps/123","user":"http://localhost/test/users/mickey.mouse","id":"123","content":"I like you a lot"}
		
		PubChirp newChirp = objectMapper.readValue(json, PubChirp.class);
		
		Assert.assertEquals(oldChirp.getId(), newChirp.getId());
		Assert.assertEquals(oldChirp.getContent(), newChirp.getContent());
		Assert.assertEquals(oldChirp.getSelf(), newChirp.getSelf());
	}
}
