package com.example.chirp.app.resources;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.stores.UserStoreUtils;

public class ChirpResourceTest extends ResourceTestSupport {

	@Before
	public void before() {
		getUserStore().clear();
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