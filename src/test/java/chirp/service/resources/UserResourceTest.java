package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;

public class UserResourceTest extends JerseyResourceTest {

	@Before
	public void clearDatabase() {
		UserRepository.getInstance().clear();
	}
	
	@Test
	public void testGetUser() {
		WebTarget root = super.target();
		UserRepository.getInstance().createUser("jdoe", "John Doe");
		String realName = root.path("users").path("jdoe").request().get(String.class);
		Assert.assertEquals("John Doe", realName);
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
		String realName = root.path("users").path("dbateman").request().get(String.class);
		Assert.assertEquals("Doug Bateman", realName);
	}

	@Test
	public void testSaveUser() {
		WebTarget root = super.target();
		Entity<String> uploadData = Entity.text("Doug Bateman");
		Response response = root.path("users").path("dbateman").request().put(uploadData);
		Assert.assertEquals(201, response.getStatus());
		String realName = root.path("users").path("dbateman").request().get(String.class);
		Assert.assertEquals("Doug Bateman", realName);
	}
}

