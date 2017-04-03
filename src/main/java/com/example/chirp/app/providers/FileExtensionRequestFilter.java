package com.example.chirp.app.providers;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Provider
@PreMatching
@Priority(Priorities.HEADER_DECORATOR)
public class FileExtensionRequestFilter implements ContainerRequestFilter {

    public Map<String, String> extMediaTypes = new HashMap<>();

    public FileExtensionRequestFilter() {
        extMediaTypes.put(".xml", "application/xml");
        extMediaTypes.put(".json", "application/json");
        extMediaTypes.put(".txt", "text/plain");
    }

    @Override
    public void filter(ContainerRequestContext rc) throws IOException {
        UriInfo uriInfo = rc.getUriInfo();
        String path = uriInfo.getRequestUri().getRawPath();
        int extIndex = path.lastIndexOf('.');
        if (extIndex == -1) { return; } // no extension, just return.

        String ext = path.substring(extIndex).toLowerCase();
        String basePath = path.substring(0, extIndex);
        String mediaType = extMediaTypes.get(ext);
        if (mediaType == null) { return; } // not a know type, just return.

        rc.getHeaders().putSingle(HttpHeaders.ACCEPT, mediaType);
        rc.setRequestUri(uriInfo.getRequestUriBuilder().replacePath(basePath).build());
    }
}

