package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/hello")
public class HelloResource {

	
	// http://localhost:8080/hello?name="Xoom"  -> Hello Xoom!
	
	@GET
	@Produces("text/plain")
	public String getHello(@QueryParam("name") String nameAsParam) {
		
		if (nameAsParam == null)
			return "Hello!";
		else
			return "Hello " + nameAsParam + "!";
	}
	
	// http://localhost:8080/hello/Xoom"  -> Hello Xoom!


	@GET
	@Path("{name}")
	@Produces("text/html")
	public String ISayHelloToo(@PathParam("name") String name) {

		return "<html><body><h1>Hello " + name + "!</h1></body></html>";
	}

}
