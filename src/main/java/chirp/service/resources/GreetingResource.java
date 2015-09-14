package chirp.service.resources;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/greetings")
public class GreetingResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String greet(@DefaultValue("stranger") @QueryParam("name") String name) {
		if ( name == null ) {
			return "Hello!";
		} else {
			return "Hello " + name + "!";
		}
	}
	
	@GET
	@Path("/{someName}")
	@Produces(MediaType.TEXT_PLAIN)
	public String greetWithPath(@PathParam("someName") String name) {
		if ( name == null ) {
			return "Hello!";
		} else {
			return "Hello " + name + "!";
		}		
	}

}
