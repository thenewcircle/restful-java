package chirp.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

@Path("/greetings")
public class HelloResource {

    // http://localhost:8080/hello?name=<somevalue>
    @GET
    public String getHello(@QueryParam("name") String name) {
        return (name == null) ? "Hello!" : String.format("Hello, %s!", name);
    }

    @GET
    @Path("{name}")
    public String getHelloAsPath(@PathParam("name") String name) {

        return String.format("Hello, %s!", name);
    }

}
