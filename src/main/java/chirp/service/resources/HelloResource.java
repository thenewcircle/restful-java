package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/hello")
public class HelloResource {

	@GET
	public String getHello() {

		return "Hello!";
	}

	
	@GET
	@Path("{name}")
	public String getHelloWithName(@PathParam("name") String name) {

		String printVal = (name != null) ? name : "";
		return "Hello " + printVal + "!";
	}

}
