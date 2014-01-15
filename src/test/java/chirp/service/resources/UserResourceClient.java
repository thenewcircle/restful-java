package chirp.service.resources;

import java.util.Collection;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;

import chirp.model.User;

public class UserResourceClient extends
		AbstractEntityClientImpl<UserResource, User> {

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
	public Collection<User> getAll(MediaType mediaType) {
		return getJerseyTest()
				.target(UriBuilder.fromResource(UserResource.class).build()
						.getPath()).request(mediaType)
				.get(new GenericType<Collection<User>>() {
				});
	}

}
