package chirp.service.resources;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class UserResourceTest extends JerseyResourceTest {

	@Test
	public void testCreateUser() {
//		Client client = ClientBuilder.newClient();
//		WebTarget root = client.target("http://localhost:8090/");
		WebTarget root = super.target();
		
		Form form = new Form();
		form.param("username", "dbateman");
		form.param("realname", "Doug Bateman");
		Entity<?> uploadData = Entity.form(form);
		Response response = root.path("users").request().post(uploadData);
		Assert.assertEquals(204, response.getStatus());
	}

}
