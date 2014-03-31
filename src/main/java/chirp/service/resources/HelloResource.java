package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/greeting")
public class HelloResource {

	@GET
	public String getHello(@QueryParam("name") String nm) {
		if (nm == null || nm.isEmpty()) {
			nm = "World";
		}
		return "Hello " + nm + "!";
	}

	@GET
	@Path("/{name}")
	public String getHelloPath(@PathParam("name") String nm) {
		return "Hello " + nm + "!";
	}

}
