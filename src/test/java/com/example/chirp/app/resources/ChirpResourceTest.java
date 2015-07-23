package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.stores.UserStoreUtils;

public class ChirpResourceTest extends ResourceTestSupport {

	@Before
	public void before() {
		getUserStore().clear();
	}

	@Test
	public void testGetChirps() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());

		// *********************************************
		// Check with default values (no limit or offset)
		Response response = target("users").path("jarjar").path("chirps").request().get();
		PubChirps chirps = response.readEntity(PubChirps.class);

		Assert.assertEquals(4, chirps.getChirps().size());
		Assert.assertEquals("Ooh mooey mooey I love you!", chirps.getChirps().get(0).getContent());
		Assert.assertNull(chirps.getLinks());
		 
		Assert.assertEquals(4, chirps.getLimit());
		Assert.assertEquals(0, chirps.getOffset());
		
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=4&offset=0"), chirps.getSelf());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=4&offset=0"), chirps.getFirst());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=4&offset=4"), chirps.getNext());
		Assert.assertEquals(null, chirps.getPrevious());

		// *********************************************
		// Test with a limit of 8
		response = target("users").path("jarjar").path("chirps").queryParam("limit", 8).request().get();
		chirps = response.readEntity(PubChirps.class);

		Assert.assertEquals(8, chirps.getChirps().size());
		Assert.assertEquals("Ooh mooey mooey I love you!", chirps.getChirps().get(0).getContent());
		Assert.assertNull(chirps.getLinks());
		 
		Assert.assertEquals(8, chirps.getLimit());
		Assert.assertEquals(0, chirps.getOffset());
		
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=0"), chirps.getSelf());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=0"), chirps.getFirst());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=8"), chirps.getNext());
		Assert.assertEquals(null, chirps.getPrevious());

		// *********************************************
		// Test with a limit of 8, offset 8
		response = target("users").path("jarjar").path("chirps").queryParam("limit", 8).queryParam("offset", "8").request().get();
		chirps = response.readEntity(PubChirps.class);

		Assert.assertEquals(8, chirps.getChirps().size());
		Assert.assertEquals("Yoosa should follow me now, okeeday?", chirps.getChirps().get(0).getContent());
		Assert.assertNull(chirps.getLinks());
		 
		Assert.assertEquals(8, chirps.getLimit());
		Assert.assertEquals(8, chirps.getOffset());
		
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=8"), chirps.getSelf());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=0"), chirps.getFirst());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=16"), chirps.getNext());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=0"), chirps.getPrevious());

		// *********************************************
		// Test with a limit of 8, offset 8
		response = target("users").path("jarjar").path("chirps").queryParam("limit", 8).queryParam("offset", "8").request().get();
		chirps = response.readEntity(PubChirps.class);

		Assert.assertEquals(8, chirps.getChirps().size());
		Assert.assertEquals("Yoosa should follow me now, okeeday?", chirps.getChirps().get(0).getContent());
		Assert.assertNull(chirps.getLinks());
		 
		Assert.assertEquals(8, chirps.getLimit());
		Assert.assertEquals(8, chirps.getOffset());
		
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=8"), chirps.getSelf());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=0"), chirps.getFirst());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=16"), chirps.getNext());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=0"), chirps.getPrevious());

		// *********************************************
		// Test with a limit of 8, offset 16
		response = target("users").path("jarjar").path("chirps").queryParam("limit", 8).queryParam("offset", "16").request().get();
		chirps = response.readEntity(PubChirps.class);

		Assert.assertEquals(8, chirps.getChirps().size());
		Assert.assertEquals("Ohh, maxi big da Force. Well dat smells stinkowiff.", chirps.getChirps().get(0).getContent());
		Assert.assertNull(chirps.getLinks());
		 
		Assert.assertEquals(8, chirps.getLimit());
		Assert.assertEquals(16, chirps.getOffset());
		
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=16"), chirps.getSelf());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=0"), chirps.getFirst());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=24"), chirps.getNext());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=8"), chirps.getPrevious());

		// *********************************************
		// Test with a limit of 8, offset 24
		response = target("users").path("jarjar").path("chirps").queryParam("limit", 8).queryParam("offset", "24").request().get();
		chirps = response.readEntity(PubChirps.class);

		Assert.assertNull(chirps.getChirps());
		Assert.assertNull(chirps.getLinks());
		 
		Assert.assertEquals(8, chirps.getLimit());
		Assert.assertEquals(24, chirps.getOffset());
		
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=24"), chirps.getSelf());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=0"), chirps.getFirst());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=32"), chirps.getNext());
		Assert.assertEquals(URI.create("http://localhost:9998/users/jarjar/chirps?limit=8&offset=16"), chirps.getPrevious());
	}
	
	@Test
	public void testGetChirp() {
		UserStoreUtils.resetAndSeedRepository(getUserStore());

		Response response = target("chirps").path("wars01").request().get();
		Assert.assertEquals(200, response.getStatus());
		PubChirp chirp = response.readEntity(PubChirp.class);

		Assert.assertEquals("wars01", chirp.getId());
		Assert.assertEquals("Do or do not. There is no try.", chirp.getContent());

		Assert.assertEquals("http://localhost:9998/chirps/wars01", chirp.getSelf().toString());
		Assert.assertEquals("http://localhost:9998/users/yoda", chirp.getUserLink().toString());
		Assert.assertEquals("http://localhost:9998/users/yoda/chirps", chirp.getChirpsLink().toString());

		Assert.assertEquals("http://localhost:9998/users/yoda", response.getLink("user").getUri().toString());
		Assert.assertEquals("http://localhost:9998/users/yoda/chirps", response.getLink("chirps").getUri().toString());

		response = target("users").path("whatever").path("chirps").path("wars01").request().get();
		Assert.assertEquals(200, response.getStatus());
		PubChirp secondChirp = response.readEntity(PubChirp.class);

		Assert.assertEquals(chirp.getId(), secondChirp.getId());
	}

	@Test
	public void testGetBadChirp() {
		Response response = target("chirps").path("99999").request().get();
		Assert.assertEquals(404, response.getStatus());
	}
}
