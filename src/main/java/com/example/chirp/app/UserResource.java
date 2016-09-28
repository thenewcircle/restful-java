package com.example.chirp.app;

import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.stores.UserStore;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/users")
public class UserResource {

    private final UserStore userStore = ChirpApplication.USER_STORE;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserPlain(@PathParam("username") String username) {
        return userStore.getUser(username).getRealName();
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserJson(@PathParam("username") String username) {
        User user = userStore.getUser(username);
        PubUser pubUser = PubUtils.toPubUser(uriInfo, user);

        Response.ResponseBuilder builder = Response.ok(pubUser);
        return PubUtils.addLinks(builder, pubUser.getLinks()).build();

// Version #1
//        return Response.ok(pubUser)
//                       .link(pubUser.getLinks().get("self"), "self")
//                       .link(pubUser.getLinks().get("chirps"), "chirps")
//                       .build();

// Version #2
//        Response.ResponseBuilder builder = Response.ok(pubUser);
//        for (Map.Entry<String, URI> entry : pubUser.getLinks().entrySet()) {
//            builder.link(entry.getValue(), entry.getKey());
//        }
//        return builder.build();
    }

//    @HEAD
//    @Path("/{username}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response headUserJson(@PathParam("username") String username) {
//        User user = userStore.getUser(username);
//        PubUser pubUser = PubUtils.toPubUser(uriInfo, user);
//
//        return Response.ok(/*pubUser*/)
//                       .link(pubUser.getLinks().get("self"), "self")
//                       .link(pubUser.getLinks().get("chirps"), "chirps")
//                       .build();
//    }

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
