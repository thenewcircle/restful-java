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

        //                                  | start here
        // http://whatever.com:999/chirp-app/users/tom
        links.put("self", toUserLink(uriInfo, user.getUsername()));

        //                                  | start here
        // http://whatever.com:999/chirp-app/users/tom/chirps
        links.put("chirps", uriInfo.getBaseUriBuilder()
                                   .path("users")
                                   .path(user.getUsername())
                                   .path("chirps").build());

        return new PubUser(links, user.getUsername(), user.getRealName());
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