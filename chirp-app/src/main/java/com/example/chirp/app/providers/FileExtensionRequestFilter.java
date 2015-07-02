package com.example.chirp.app.providers;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.*;

@PreMatching
@Priority(Priorities.HEADER_DECORATOR)
@Provider
public class FileExtensionRequestFilter implements ContainerRequestFilter {

	// Create a map of file extensions to media type
	public static final Map<String, String> extMediaTypes;
	static {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put(".txt", "text/plain");
		map.put(".xml", "application/xml");
		map.put(".json", "application/json");
		extMediaTypes = Collections.unmodifiableMap(map);
	}
	
    @Override
    public void filter(ContainerRequestContext rc) throws IOException {
        UriInfo uriInfo = rc.getUriInfo();

        // Get the requested path
        String path = uriInfo.getRequestUri().getRawPath();

        // Find out if the path ends in a period
        int extIndex = path.lastIndexOf('.');
        if (extIndex == -1) {
            return; // no extension, just return.
        }

        // Extract the extension from the path
        String ext = path.substring(extIndex).toLowerCase();

        // Recreate the path without the extension
        String basePath = path.substring(0, extIndex);

        // Look for the new media type based on the provided extension
        String mediaType = extMediaTypes.get(ext);
        
        if (mediaType == null) {
        	return; // not a know type, just return.
        }
        
        // Tweak the headers to set the media type according to the value from the map
        rc.getHeaders().putSingle(HttpHeaders.ACCEPT, mediaType);
        
        // Change the URI that the call will be mapped to (the one without an extension)
        rc.setRequestUri(uriInfo.getRequestUriBuilder().replacePath(basePath).build());
    }
}