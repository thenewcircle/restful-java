package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/greetings")
public class HelloResource {

	@GET
	public String getHello(@QueryParam("name") String name) {
	  return name == null ? "Hello!" : name;
	}

}
