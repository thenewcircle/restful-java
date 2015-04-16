package chirp.service.providers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.xml.bind.DatatypeConverter;

import com.sun.research.ws.wadl.Application;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    System.out.println("In filter");
    
    String authHeader = requestContext.getHeaderString("Authorization");

    // Basic 32od49872d309488s
    
    if (authHeader == null || authHeader.startsWith("Basic ") == false) {
      throw new NotAuthorizedException("Basic");
    } else {
      authHeader = authHeader.substring(6);
    }

    // 32od49872d309488s
    
    byte[] bytes = DatatypeConverter.parseBase64Binary(authHeader);
    String basicAuth = new String(bytes, StandardCharsets.UTF_8);

    // jacobp:secret
    
    int pos = basicAuth.indexOf(":");

    String apiKey;
    String apiPassword;

    if (pos < 0) {
      throw new NotAuthorizedException("API");
    }
    
    // jacobp
    apiKey = basicAuth.substring(0, pos);

    // secret
    apiPassword = basicAuth.substring(pos+1);

    if ("jacobp".equals(apiKey) == false || "secret".equals(apiPassword) == false) {
      throw new NotAuthorizedException("BASIC");
    }
  }
}
