package com.example.chirp.app.pub;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.kernel.User;

public class PubUtils {
  public static PubUser toPubUser(UriInfo uriInfo, User user) {

    Map<String,URI> links = new LinkedHashMap<>();

    links.put("self", uriInfo.getBaseUriBuilder()
                             .path("users")
                             .path(user.getUsername())
                             .build());

    links.put("chirps", uriInfo.getBaseUriBuilder()
      .path("users")
      .path(user.getUsername())
      .path("chirps").build());

    return new PubUser(links, 
                       user.getUsername(), 
                       user.getRealName());
  }
}

