package chirp.service.resources.client;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;

import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;
import chirp.service.resources.UserResource;

public class UserResourceClient extends
		AbstractEntityClientImpl<UserResource, UserRepresentation, UserCollectionRepresentation> {

	public UserResourceClient(JerseyTest jerseyTest) {
		super(jerseyTest);
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
