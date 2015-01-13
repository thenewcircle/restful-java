package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/greeting")
public class HelloResource {

	@GET
	@Path("/plain")
	public String getHello() {
		return "Goodbye!";
	}

	@GET
	public String getHello(@QueryParam("name") String name) {
		if (name == null) {
			return "Hello!";
		} else {
			return "Hello " + name + "!";
		}
	}

}
