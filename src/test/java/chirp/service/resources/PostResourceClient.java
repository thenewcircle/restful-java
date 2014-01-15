package chirp.service.resources;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;

import chirp.service.representations.PostRepresentation;
import chirp.service.representations.UserRepresentation;

public class PostResourceClient extends
		AbstractEntityClientImpl<PostResource, PostRepresentation> {

	private int count = 1;
	private UserRepresentation user;

	public PostResourceClient(JerseyTest jt) {
		super(jt);
	}

	private UserRepresentation getUser() {
		if (this.user == null)
			this.user = new UserResourceClient(this.getJerseyTest())
					.createWithGetLocationVerify(MediaType.APPLICATION_XML_TYPE);

		return this.user;
	}

	private URI postBase() {
		return UriBuilder.fromPath("/posts").path(getUser().getUsername())
				.build();
	}

	@Override
	public Response createWithStatus(Status expectedStatus) {

		final Form form = new Form().param("content",
				String.format("Posting message # %d", count++));
		final Response response = createWithStatusInternal(form, postBase(),
				expectedStatus);

		return response;
	}

	@Override
	public Collection<PostRepresentation> getAll(MediaType mediaType) {
		return getJerseyTest().target(postBase().getPath()).request(mediaType)
				.get(new GenericType<Collection<PostRepresentation>>() {});
	}

}
