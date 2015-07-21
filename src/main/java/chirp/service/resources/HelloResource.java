package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

	// http://localhost:8080/hello?name=Cisco
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getHello(@QueryParam("name") String uriNameParam) {
		StringBuffer sb = new StringBuffer("Hello");
		if (uriNameParam != null)
			sb.append(" ").append(uriNameParam);
		sb.append('!');
		return sb.toString();
	}

	// http://localhost:8080/hello/Cisco -> Hello Cisco!
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("{name}")
	public String anotherGetHello(@PathParam("name") String name) {

		return String.format("<html><body><h1>Hello %s!</h1></body></html>",
				name);
	}

}
