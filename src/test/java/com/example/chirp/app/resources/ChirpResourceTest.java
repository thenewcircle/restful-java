package com.example.chirp.app.resources;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import com.example.chirp.app.pub.PubChirp;

public class ChirpResourceTest extends ResourceTestSupport {

	@Test
	public void testGetChirp() {

		Response response = target("chirps").path("wars01").request().get();
		Assert.assertEquals(200, response.getStatus());
		PubChirp chirp = response.readEntity(PubChirp.class);

		Assert.assertEquals("wars01", chirp.getId());
		Assert.assertEquals("Do or do not. There is no try", chirp.getContent());

		Assert.assertEquals("x", chirp.getSelf());
		Assert.assertEquals("x", chirp.getUserLink());
		Assert.assertEquals("x", chirp.getChirpsLink());

		Assert.assertEquals("x", response.getLink("user"));
		Assert.assertEquals("x", response.getLink("chirps"));
	}
}
