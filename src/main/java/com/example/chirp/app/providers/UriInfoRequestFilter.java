package com.example.chirp.app.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Map;

@Component
@Provider
@PreMatching
@Priority(1)
public class UriInfoRequestFilter implements ContainerRequestFilter {

  public static final ThreadLocal<UriInfo> tlui = new ThreadLocal<>();

  @Override
  public void filter(ContainerRequestContext rc) throws IOException {

    tlui.set(rc.getUriInfo());

  }
}







