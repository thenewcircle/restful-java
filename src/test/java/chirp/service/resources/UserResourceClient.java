package chirp.service.resources;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;

import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

public class UserResourceClient extends
		AbstractEntityClientImpl<UserResource, UserRepresentation, UserCollectionRepresentation> {

	public UserResourceClient(JerseyTest jt) {
		super(jt);
	}

	@Override
	public Response createWithStatus(Status expectedStatus) {
		final Form form = new Form().param("username", "gordonff").param(
				"realname", "Gordon Force");
		return createWithStatusInternal(form, expectedStatus);
	}

	@Override
	protected String createEntityCollectionURIPath() {
		return UriBuilder.fromResource(UserResource.class).build()
		.getPath();
	}


}
