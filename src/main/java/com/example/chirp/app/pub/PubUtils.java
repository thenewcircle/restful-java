package com.example.chirp.app.pub;

import com.example.chirp.app.kernel.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class PubUtils {

    public static PubUser toPubUser(UriInfo uriInfo, User user) {
        Map<String,URI> links = new LinkedHashMap<>();

        URI uri = uriInfo.getBaseUriBuilder()
                         .path("users")
                         .path(user.getUsername())
                         .build();
        links.put("self", uri);

        uri = uriInfo.getBaseUriBuilder()
                     .path("users")
                     .path(user.getUsername())
                     .path("chirps")
                     .build();
        links.put("chirps", uri);

        return new PubUser(links, user.getUsername(), user.getRealName());
    }
}