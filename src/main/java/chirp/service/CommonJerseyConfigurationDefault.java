package chirp.service;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class CommonJerseyConfigurationDefault implements
		CommonJerseyConfiguration {

	@Override
	public ResourceConfig configure() {
		
		// Jersey uses java.util.logging - bridge to slf4
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		final ResourceConfig rc = new ResourceConfig().packages(
				"chirp.service.resources;chirp.service.providers").register(JacksonFeature.class);

		return rc;
	}

}
