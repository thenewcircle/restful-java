package com.example.chirp.providers;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@PreMatching
@Priority(Priorities.HEADER_DECORATOR)
@Provider
public class FileExtensionRequestFilter implements ContainerRequestFilter {

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

        String path = uriInfo.getRequestUri().getRawPath();
        int extIndex = path.lastIndexOf('.');
        if (extIndex == -1) {
            return;
        }

        String ext = path.substring(extIndex);
        String basePath = path.substring(0, extIndex);


        String mediaType = extMediaTypes.get(ext);
        if (mediaType == null)
        	return;
        
        rc.getHeaders().putSingle(HttpHeaders.ACCEPT, mediaType);
        rc.setRequestUri(uriInfo.getRequestUriBuilder().replacePath(basePath).build());
    }

}
