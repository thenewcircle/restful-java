package chirp.service.providers;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  // Load one time from the file system, spring, DB, System Properties, wherver.
  private final java.util.List<String> valid = Arrays.asList("Basic amFjb2JwOnNlY3JldA==");
  
  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    String authHeader = requestContext.getHeaderString("Authorization");

    if (valid.contains(authHeader) == false) {
      throw new NotAuthorizedException("BASIC");
    }
  }
}
