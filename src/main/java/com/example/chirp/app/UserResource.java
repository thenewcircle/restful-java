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

    @Context private UriInfo uriInfo;

    @GET
    @Path("/{username}")
    public String getUser(@PathParam("username") String username) {
      return userStore.getUser(username).getRealName();
    }

    @PUT
    @Path("/{username}")
    public Response createUser(@BeanParam CreateUserRequest request) {

        request.validate();

        userStore.createUser(request.getUsername(), request.getRealName());
        URI location = request.getUriInfo()
                              .getBaseUriBuilder()
                              .path("/users")
                              .path(request.getUsername())
                              .build();

        return Response.created(location).build();
    }
}
