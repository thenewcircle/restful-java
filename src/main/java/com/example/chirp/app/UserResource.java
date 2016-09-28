package com.example.chirp.app;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubChirps;
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
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserJsonAndXml(@PathParam("username") String username) {

        User user = userStore.getUser(username);
        PubUser pubUser = PubUtils.toPubUser(uriInfo, user);

        Response.ResponseBuilder builder = Response.ok(pubUser);
        return PubUtils.addLinks(builder, pubUser.getLinks()).build();
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

    @POST
    @Path("/{username}/chirps")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    // .../user/tom/chirps
    public Response createChirp(@PathParam("username") String username,
                                String content) {

        User user = userStore.getUser(username);
        Chirp chirp = user.createChirp(content);
        userStore.updateUser(user);

        PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);

        URI location = pubChirp.getLinks().get("self");
        Response.ResponseBuilder builder = Response.created(location).entity(pubChirp);
        return PubUtils.addLinks(builder, pubChirp.getLinks()).build();
    }

    @GET
    @Path("/{username}/chirps")
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML} )
    public Response getChirps(@PathParam("username") String username,
                              @DefaultValue("0") @QueryParam("offset") String offsetString,
                              @DefaultValue("10") @QueryParam("limit") String limitString) {

        User user = userStore.getUser(username);

        PubChirps pubChirps = PubUtils.toPubChirps(uriInfo, user, limitString, offsetString + "");

        Response.ResponseBuilder builder = Response.ok(pubChirps);
        return PubUtils.addLinks(builder, pubChirps.get_links()).build();

    }
}





























