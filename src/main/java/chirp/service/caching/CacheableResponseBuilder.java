package chirp.service.caching;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Use this class to build a response object for cacheable resources.
 * 
 */
public class CacheableResponseBuilder {

	/**
	 * Implement this interface to create Links for insertion to the response
	 * only when required. Keeping all declarations within then execute method
	 * avoid unnecessary object creation when a link header is not required for
	 * a response.
	 */
	public interface AddLinksCommand {
		public Link[] execute();
	}

	private Request request;
	private CacheControl cacheControl;
	private AddLinksCommand addLinksCommand;
	private CacheableResource cacheableResource;

	public CacheableResponseBuilder() {

		// defaults
		cacheControl = new CacheControl();
		cacheControl.setMaxAge(300); // how long the client should wait before
										// asking for this entity again
		cacheControl.setNoStore(true); // don't store on disk
		cacheControl.setPrivate(true); // make it public or is this a good idea?
	}

	public CacheableResponseBuilder addLinksCommand(
			AddLinksCommand addLinksCommand) {
		this.addLinksCommand = addLinksCommand;
		return this;
	};

	public CacheableResponseBuilder request(Request request) {
		this.request = request;
		return this;
	}

	public CacheableResponseBuilder cacheControl(CacheControl cacheControl) {
		this.cacheControl = cacheControl;
		return this;
	}

	public CacheableResponseBuilder cacheableResource(
			CacheableResource cacheableResource) {
		this.cacheableResource = cacheableResource;
		return this;
	}

	public Response build(Request request, CacheableResource cacheableResource) {
		return this.request(request).cacheableResource(cacheableResource)
				.build();
	}

	public Response build() {

		Response response = null;
		String httpMethod = request.getMethod();
		switch (httpMethod) {
		case HttpMethod.GET:
		case HttpMethod.HEAD:
			ResponseBuilder rb = (cacheableResource == null) ? null : request
					.evaluatePreconditions(
							cacheableResource.getLastModificationTime(),
							cacheableResource.getEntityTag());

			if (rb == null) {
				rb = (request.getMethod().equals(HttpMethod.GET)) ? Response
						.ok(cacheableResource) : Response.ok();
				rb.tag(cacheableResource.getEntityTag());

				if (addLinksCommand != null)
					rb.links(addLinksCommand.execute());

				if (cacheControl != null)
					rb.cacheControl(cacheControl);

			}

			rb.lastModified(cacheableResource.getLastModificationTime());

			response = rb.build();
			break;

		default:
			throw new IllegalArgumentException(String.format(
					"This %s method is not cacheable", httpMethod));
		}

		return response;

	}

}
