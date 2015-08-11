package com.example.chirp.app.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.stores.UserStoreUtils;

public class UserResourceTest extends ResourceTestSupport {

	@Before
	  public void before() {
	    getUserStore().clear();
	  }
	  
	  @Test
	  public void testCreateUser() {
	    String username = "student";
	    String realName = "Bob Student";

	    Form user = new Form().param("realName", realName);
	    Entity entity = Entity.form(user);

	    Response response = target("/users").path(username).request().put(entity);

	    Assert.assertEquals(201, response.getStatus());
	    Assert.assertNotNull(getUserStore().getUser(username));
	    String location = response.getHeaderString("Location");
	    Assert.assertEquals("http://localhost:9998/users/student", 
	    		location);

	    String shortLocation = location.substring(21);
	    Assert.assertEquals("/users/student", shortLocation);
	    
	    response = target(shortLocation).request().accept(MediaType.TEXT_PLAIN).get();
	    Assert.assertEquals(200, response.getStatus());
	    String actualName = response.readEntity(String.class);
	    Assert.assertEquals(realName, actualName);
	  }
	
	  @Test
	  public void testGetUserPlain() {
	    UserStoreUtils.resetAndSeedRepository(getUserStore());

	    Response response = target("/users/yoda").request()
	      .accept(MediaType.TEXT_PLAIN).get();

	    Assert.assertEquals(200, response.getStatus());

	    String realname = response.readEntity(String.class);
	    Assert.assertEquals("Master Yoda", realname);
	  }
		
	  @Test
	  public void testGetUserJson() {
		    UserStoreUtils.resetAndSeedRepository(getUserStore());

		    Response response = target("/users/yoda").request()
		      .accept(MediaType.APPLICATION_JSON).get();

		    Assert.assertEquals(200, response.getStatus());

		    PubUser user = response.readEntity(PubUser.class);
		    Assert.assertEquals(user.getUsername(), "yoda");
		    Assert.assertEquals(user.getRealName(), "Master Yoda");
	  }
		
	  @Test
	  public void testGetUserXml() {
		    UserStoreUtils.resetAndSeedRepository(getUserStore());

		    Response response = target("/users/yoda").request()
		      .accept(MediaType.APPLICATION_XML).get();

		    Assert.assertEquals(200, response.getStatus());

		    PubUser user = response.readEntity(PubUser.class);
		    Assert.assertEquals(user.getUsername(), "yoda");
		    Assert.assertEquals(user.getRealName(), "Master Yoda");
	  }
	  
	  @Test
	  public void testCreateDuplicateUser() {
	    Form user = new Form().param("realName", "Mickey Mouse");
	    Entity entity = Entity.form(user);

	    Response response = target("/users").path("mickey").request().put(entity);
	    Assert.assertEquals(201, response.getStatus());

	    response = target("/users").path("mickey").request().put(entity);
	    Assert.assertEquals(403, response.getStatus());
	  }
	
	  @Test
	  public void testGetWrongUser() {
	    Response response = target("/users/donald").request()
	      .accept(MediaType.TEXT_PLAIN).get();

	    Assert.assertEquals(404, response.getStatus());
	  }

	  @Test
	  public void testCreateWithBadName() {
	    Form user = new Form().param("realName", "Minnie Mouse");
	    Entity entity = Entity.form(user);

	    Response response = target("/users").path("minnie mouse").request().put(entity);

	    Assert.assertEquals(400, response.getStatus());
	  }
}