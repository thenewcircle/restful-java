package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;


@Path("/hello")
public class HelloResource {

	// http://localhost:8080/hello?name=Cisco
	@GET
	public String getHello(@QueryParam("name") String nameFromUrl) {

		if (nameFromUrl == null) {
			return "Hello!";
		} else {
			return "Hello " + nameFromUrl + "!";
		}

	}

	// http://localhost:8080/hello/{pathname}. For example http://localhost:8080/hello/Cisco
	@GET
	@Path("{pathname}")
	public String getHelloFromPath(@PathParam("pathname") String nameFromUrl) {

		if (nameFromUrl == null) {
			return "Hello!";
		} else {
			return "Hello " + nameFromUrl + "!";
		}

	}
}
