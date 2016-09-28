package com.example.chirp.app;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubUser;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class PubUtils {

    public static PubChirp toPubChirp(UriInfo uriInfo, Chirp chirp) {
      User user = chirp.getUser();
      Map<String,URI> links = new LinkedHashMap<>();

      links.put("self", toChirpLink(uriInfo, chirp.getId().getId()));
      links.put("chirps", toChirpsLink(uriInfo, user.getUsername()));
      links.put("user", toUserLink(uriInfo, user.getUsername()));

      return new PubChirp(links, chirp.getId().getId(), chirp.getContent());
    }

    public static URI toChirpLink(UriInfo uriInfo, String chirpId) {
        return uriInfo.getBaseUriBuilder()
                      .path("chirps")
                      .path(chirpId)
                      .build();
    }

    public static PubUser toPubUser(UriInfo uriInfo, User user) {

        Map<String, URI> links = new LinkedHashMap<>();
        links.put("self", toUserLink(uriInfo, user.getUsername()));
        links.put("chirps", toChirpsLink(uriInfo, user.getUsername()));

        return new PubUser(links, user.getUsername(), user.getRealName());
    }

    public static URI toChirpsLink(UriInfo uriInfo, String username) {
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