package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import chirp.model.User;
import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	@Before
	public void clearDatabase() {
		UserRepository.getInstance().clear();
		UserRepository.getInstance().prepopulate();
	}
	
	@Test
	public void testGetUserAsText() {
		WebTarget root = super.target();
		String realName = root.path("users").path("yoda").request().accept(MediaType.TEXT_PLAIN).get(String.class);
		Assert.assertEquals("Master Yoda", realName);
	}

	@Test
	public void testGetUserAsJSon() {
		WebTarget root = super.target();
		User user = root.path("users").path("yoda").request().accept(MediaType.APPLICATION_JSON).get(User.class);
		Assert.assertEquals("Master Yoda", user.getRealname());
	}

	@Test
	public void testCreateUser() {
		WebTarget root = super.target();
		Form form = new Form();
		form.param("username", "dbateman");
		form.param("realname", "Doug Bateman");
		Entity<Form> uploadData = Entity.form(form);
		Response response = root.path("users").request().post(uploadData);
		Assert.assertEquals(201, response.getStatus());
		String realName = root.path("users").path("dbateman").request().accept(MediaType.TEXT_PLAIN).get(String.class);
		Assert.assertEquals("Doug Bateman", realName);
	}
	
	@Test
	public void testGetUserNotFound() {
		WebTarget root = super.target();
		Response response = root.path("users").path("obiwan").request()
				.accept(MediaType.TEXT_PLAIN).get(Response.class);
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(),  response.getStatus());
	}

	@Test
	public void testCreateDuplicateUser() {
		WebTarget root = super.target();
		Form form = new Form();
		form.param("username", "dbateman");
		form.param("realname", "Doug Bateman");
		Entity<Form> uploadData = Entity.form(form);
		Response response1 = root.path("users").request().post(uploadData);
		Assert.assertEquals(201, response1.getStatus());
		Response response2 = root.path("users").request().post(uploadData);
		Assert.assertEquals(409, response2.getStatus());
		String realName = root.path("users").path("dbateman").request()
				.accept(MediaType.TEXT_PLAIN).get(String.class);
		Assert.assertEquals("Doug Bateman", realName);
	}

	@Test
	public void testSaveUser() {
		WebTarget root = super.target();
		Entity<String> uploadData = Entity.text("Doug Bateman");
		Response response = root.path("users").path("dbateman").request().put(uploadData);
		Assert.assertEquals(201, response.getStatus());
		String realName = root.path("users").path("dbateman").request().accept(MediaType.TEXT_PLAIN).get(String.class);
		Assert.assertEquals("Doug Bateman", realName);
	}
}

