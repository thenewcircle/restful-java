package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("hello")
	public String getHello() {

		return "Hello!";
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("greeting")
	public String getHelloNameFromQueryParameter(@QueryParam("name") String name) {
		if (name == null)
			return "Hello!";
		else
			return String.format("Hello %s!", name);
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("greeting/{one}/{two}")
	public String getHelloNameFromPathParameter(@PathParam("one") String one, @PathParam("two") String two) {
		return String.format("Hello %s %s!", one, two);
	}



}
