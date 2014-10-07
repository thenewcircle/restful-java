package chirp.service.resources;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class UsersResourceTest extends JerseyResourceTest<UsersResource> {
	
	@Test
	public void creatUserWithPOSTSucess() {
		Form user = new Form().param("realname","Gordon Force").param("username", "gordonff");
		Response response = target("/users").request().post(Entity.form(user));
		// add validation code here
	}

}
