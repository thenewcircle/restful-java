package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getHello(@QueryParam("name") String uriNameParam) {

		if (uriNameParam != null)
			return "Hello " + uriNameParam + "!";
		else
			return "Hello!";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{name}")
	public String anotherGetHello(@PathParam("name") String name) {
		
		StringBuilder builder = new StringBuilder("<html><body><h1>Hello");
		
		if (name.length() > 0) {
			builder.append(" ").append(name);
		}
		
		builder.append("!</h1></body></html>");
		
		return builder.toString();
	}

}
