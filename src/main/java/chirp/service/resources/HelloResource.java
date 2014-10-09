package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/greeting")
public class HelloResource {

	// http://localhost:8080/hello?name=<somevalue>
	@GET
	public String getHelloWithName(@QueryParam("name") String helloName) {
		
		if (helloName == null) {
			return "Hello!";
		}
		else {
			return "Hello " + helloName + "!";
		}
	}

	// http://localhost:8080/hello/<helloName>
	@GET
	@Path("{helloName}")
	public String getHelloWithPathName(@PathParam("helloName") String helloName) {
		
		if (helloName == null)
			throw new NullPointerException("path param not valid");

		return "Hello " + helloName + "!";
	}

}
