package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/hello")
public class HelloResource {

	// http://localhost:8080/hello?name=Cisco

	@GET
	public String getHello(@QueryParam("name") String otherName) {

		return "Hello " + otherName + "!";

	}
	
	// http://localhost:8080/hello/Cisco

	@Path("{name}")
	@GET
	public String getHelloOnPath(@PathParam("name") String otherName) {

		return "Hello " + otherName + "!";
	}

}
