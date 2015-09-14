package chirp.service.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/greetings")
public class GreetingResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response greet(@DefaultValue("stranger") @QueryParam("name") String name) {
		String response = "Hello" + ((name == null ) ? "!" : " " + name + "!");
		return Response.ok().entity(response).build();
	}
	
	@GET
	@Path("/{someName}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response greetWithPath(@PathParam("someName") String name) {
		String response = "Hello" + ((name == null ) ? "!" : " " + name + "!");
		return Response.ok().entity(response).build();
	}

}
