package com.example.chirp.app.providers;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Component
@Provider
@PreMatching
@Priority(Priorities.HEADER_DECORATOR)
public class FileExtensionRequestFilter implements ContainerRequestFilter {
  
	@Resource(name="fileExtensionMap")
	private Map<String, String> extMediaTypes;
  
	public FileExtensionRequestFilter() {
	}

  @Override public void filter(ContainerRequestContext rc) throws IOException {
	  UriInfo uriInfo = rc.getUriInfo();
	  String path = uriInfo.getRequestUri().getRawPath();
	  int extIndex = path.lastIndexOf('.');
	  if (extIndex == -1) { 
		  return; // no extension, just return.
	  }

	  // http://localhost:8080/chirp-app/users/yoda.xml
	  
	  String ext = path.substring(extIndex).toLowerCase();
	  // .xml

	  String basePath = path.substring(0, extIndex);
	  // http://localhost:8080/chirp-app/users/yoda

	  
	  String mediaType = extMediaTypes.get(ext);
	  if (mediaType == null) { 
		  return; // not a know type, just return. 
	  }

	  rc.getHeaders().putSingle(HttpHeaders.ACCEPT, mediaType);
	  rc.setRequestUri(uriInfo.getRequestUriBuilder().replacePath(basePath).build());
	}
  }
  
  
  