package com.example.chirp.app;

import com.example.chirp.app.stores.UserStore;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/users")
public class UserResource {

    private final UserStore userStore = ChirpApplication.USER_STORE;

    @GET
    @Path("/{username}")
    public String getUser(@PathParam("username") String username) {
      return userStore.getUser(username).getRealName();
    }

    @PUT
    @Path("/{username}")
    public Response createUser(@PathParam("username") String username,
                               @FormParam("realName") String realName,
                               @Context UriInfo uriInfo) {

        userStore.createUser(username, realName);
        URI location = uriInfo.getBaseUriBuilder().path("/users").path(username).build();
        return Response.created(location).build();

//        try {
//            userStore.createUser(username, realName);
//
//            URI location = uriInfo.getBaseUriBuilder().path("/users").path(username).build();
//            return Response.created(location).build();
//
//        } catch (DuplicateEntityException ex) {
//            return Response.status(403).build();
//        }

    }
}
