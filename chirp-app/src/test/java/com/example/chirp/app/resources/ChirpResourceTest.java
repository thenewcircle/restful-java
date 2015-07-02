package com.example.chirp.app.resources;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.app.providers.AuthenticationFilter;
import com.example.chirp.kernel.stores.UsersStoreUtils;
import com.example.chirp.pub.PubChirp;

public class ChirpResourceTest extends ResourceTestSupport {

	@Before
	public void beforeTest() {
		getUserStore().clear();
	}

	@Test
	public void getChirp() {
		UsersStoreUtils.resetAndSeedRepository(getUserStore());
			
		Response response = target("chirps")
			  .path("wars01")
			  .request()
			  .header("Accept", MediaType.APPLICATION_JSON)
			  .header("Authorization", AuthenticationFilter.DEFAULT)
			  .get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		
		PubChirp chirp = response.readEntity(PubChirp.class);
		Assert.assertNotNull(chirp);
		Assert.assertEquals("wars01", chirp.getId());
		Assert.assertEquals("Do or do not. There is no try.", chirp.getContent());
	}

	@Test
	public void getChirpByExtension() {
		UsersStoreUtils.resetAndSeedRepository(getUserStore());
			
		// the extension ".json" specified in the path will 
		// override the media type specified in the header.
		
		Response response = target("chirps")
			  .path("wars01.json") 
			  .request()
			  .header("Accept", MediaType.APPLICATION_XML)
			  .header("Authorization", AuthenticationFilter.DEFAULT)
			  .get();

		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		
		String json = response.readEntity(String.class);
		// I don't want to validate the whole JSON string... Instead,  
		// I can just make sure that it starts with a "{" and not a "<"
		Assert.assertTrue(json.startsWith("{"));
	}
}
