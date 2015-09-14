package chirp.service.resources;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class RootResource {

	@GET
	public String getHello() {
		Date now = new Date();
		String when = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss").format(now);
		return String.format("As of %s, everything is OK.", when);
	}

}
