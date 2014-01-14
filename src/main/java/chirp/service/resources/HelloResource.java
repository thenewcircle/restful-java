package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/greeting")
public class HelloResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getHello() {

		return "Hello!";
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getHelloWithName(@PathParam("name") String name) {
		return "Hello " + name + "!";
	}

	@GET
	@Path("/withName")
	@Produces(MediaType.TEXT_PLAIN)
	public String getHelloWithNameQueryParam(@QueryParam("name") String name) {
		return "Hello " + name + "!";
	}
}
