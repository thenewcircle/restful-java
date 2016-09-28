package com.example.chirp.app;

import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubUser;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class PubUtils {

    public static PubUser toPubUser(UriInfo uriInfo, User user) {

        Map<String, URI> links = new LinkedHashMap<>();

        //                                  | start here
        // http://whatever.com:999/chirp-app/users/tom
        links.put("self", uriInfo.getBaseUriBuilder()
                                 .path("users")
                                 .path(user.getUsername())
                                 .build());

        //                                  | start here
        // http://whatever.com:999/chirp-app/users/tom/chirps
        links.put("chirps", uriInfo.getBaseUriBuilder()
                                   .path("users")
                                   .path(user.getUsername())
                                   .path("chirps").build());

        return new PubUser(links, user.getUsername(), user.getRealName());
    }
}