package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/greetings")
public class HelloResource {

  // http://localhost/greetings
  
  // This mapps to http://localhost/greetings?name=Jacob
	@GET
	public String getHello(@QueryParam("name") String name) {
	  return name == null ? "Hello!" : name;
	}

	// This mapps to http://localhost/greetings/Jacob
  @GET
  @Path("/{name}")
  public String getHelloByPath(@PathParam("name") String nameASD) {
    return nameASD;
  }

}
