package com.example.chirp.app;

import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubUser;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class PubUtils {

    public static PubUser toPubUser(UriInfo uriInfo, User user) {

        Map<String, URI> links = new LinkedHashMap<>();
        links.put("self", toUserLink(uriInfo, user.getUsername()));
        links.put("chirps", toChirpLink(uriInfo, user.getUsername()));

        return new PubUser(links, user.getUsername(), user.getRealName());
    }

    public static URI toChirpLink(UriInfo uriInfo, String username) {
        return uriInfo.getBaseUriBuilder()
                      .path("users")
                      .path(username)
                      .path("chirps")
                      .build();
    }

    public static URI toUserLink(UriInfo uriInfo, String username) {
        return uriInfo.getBaseUriBuilder()
                      .path("users")
                      .path(username)
                      .build();
    }


    public static Response.ResponseBuilder addLinks(Response.ResponseBuilder builder, Map<String, URI> links) {
      for (Map.Entry<String, URI> entry : links.entrySet()) {
        builder.link(entry.getValue(), entry.getKey());
      }
      return builder;
    }
}