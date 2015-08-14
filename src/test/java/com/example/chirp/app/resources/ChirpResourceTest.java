package com.example.chirp.app.resources;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.stores.UserStore;
import com.example.chirp.app.stores.UserStoreUtils;

public class ChirpResourceTest extends ResourceTestSupport {

	@Autowired
	protected UserStore userStore;

	@Before
	  public void before() {
		userStore.clear();
	  }

	@Test
	public void testGetChirp() {
	    UserStoreUtils.resetAndSeedRepository(userStore);

	    Response response = target("chirps").path("wars01").request().get();
	    Assert.assertEquals(200, response.getStatus());

	    PubChirp chirp = response.readEntity(PubChirp.class);
	    Assert.assertEquals("wars01", chirp.getId());
	    // test content
	    // test self link
	    // test chirps link
	    // test user link
	}
	
}





