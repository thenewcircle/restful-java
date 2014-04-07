package com.example.chirp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.example.chirp.model.Post;
import com.example.chirp.model.User;
import com.example.util.dao.GuidRepository;
public class ConfigurationServiceTest {

	@Test
	public void testFreezeThaw() throws IOException {
		File file = ConfigurationService.getRepositoryFile();
		File temp = File.createTempFile("ChirpState", ".bin");
		try {
			//Setup
			temp.deleteOnExit();
			ConfigurationService.setRepositoryFile(temp);
			GuidRepository guidRepository1 = ConfigurationService.getGuidRepository();
			ChirpRepository chirpRepository1 = ConfigurationService.getChirpRepository();
			chirpRepository1.clear();
			guidRepository1.clear();
			User obiwan1 = chirpRepository1.createUser("obiwan", "Obiwan Kenobi");
			assertEquals("obiwan", obiwan1.getUsername());
			Post post1 = chirpRepository1.createPost("obiwan", "Use the force Luke.");
			String guid1 = post1.getGuid();
			
			//Business Logic
			ConfigurationService.freezeRepositories();
			chirpRepository1.clear();
			guidRepository1.clear();
			assertFalse(chirpRepository1.isExistingUser("obiwan"));
			assertFalse(chirpRepository1.isExistingPost(guid1));
			assertFalse(guidRepository1.isExistingGuid(guid1));
			ConfigurationService.thawRepositories();
			
			//Verify
			GuidRepository guidRepository2 = ConfigurationService.getGuidRepository();
			ChirpRepository chirpRepository2 = ConfigurationService.getChirpRepository();
			assertTrue(chirpRepository2.isExistingUser("obiwan"));
			assertTrue(chirpRepository2.isExistingPost(guid1));
			assertTrue(guidRepository2.isExistingGuid(guid1));
			User obiwan2 = chirpRepository2.getUser("obiwan");
			Post post2 = chirpRepository2.getPost(guid1);
			Post post3 = obiwan2.getPosts().iterator().next();
			assertNotSame(post1, post2);
			assertEquals(post1, post2);
			assertSame(post2, post3);
			assertEquals("obiwan", post3.getUser().getUsername());
		} finally {
			//Cleanup
			ConfigurationService.setRepositoryFile(file);
		}
	}
	
}
