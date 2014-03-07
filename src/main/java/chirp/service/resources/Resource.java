package chirp.service.resources;

import java.net.URI;

import javax.ws.rs.core.Response;

public interface Resource<E,P,CP> {
	
	// public E create();
	
	// public P get();
	
	public Response createMethodRepsonse(URI location);
	
	public CP getAll();

}
