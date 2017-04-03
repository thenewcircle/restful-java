package com.example.chirp.app;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.kernel.exceptions.DuplicateEntityException;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.pub.PubUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{username}")
    @Produces( { MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getUserJsonXml(@PathParam("username") String username) {
        User user = ChirpApplication.USER_STORE.getUser(username);

        PubUser pubUser = PubUtils.toPubUser(uriInfo, user);

        Response.ResponseBuilder builder = Response.ok(pubUser);
        return PubUtils.addLinks(builder, pubUser.getLinks()).build();
    }

    @PUT
    @Path("{username}")
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createUser(@BeanParam CreateUserRequest request) {

        request.validate();

        User user = ChirpApplication.USER_STORE.createUser(
                request.getUsername(), request.getRealName());

        URI location = uriInfo.getBaseUriBuilder()
                .path("users")
                .path(request.getUsername())
                .build();


        return Response.created(location)
                .link(location, "self")
                .build();
    }

    @GET
    @Path("{username}/chirps")
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAllChrip(@PathParam("username") String username,
                                @QueryParam("offset") String offset,
                                @QueryParam("limit") String limit,
                                @QueryParam("include") List<String> includes) {

        User user = ChirpApplication.USER_STORE.getUser(username);
        PubChirps pubChirps = PubUtils.toPubChirps(uriInfo, user, limit, offset, includes);
        Map<String, URI> links = pubChirps.get_links();

        Response.ResponseBuilder builder = Response.ok(pubChirps);
        return PubUtils.addLinks(builder, links).build();
    }

    @POST
    @Path("{username}/chirps")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createChrip(@PathParam("username") String username,
                                String content) {

        User user = ChirpApplication.USER_STORE.getUser(username);
        Chirp chirp = user.createChirp(content);
        ChirpApplication.USER_STORE.updateUser(user);

        PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);
        URI location = pubChirp.getLinks().get("self");

        Response.ResponseBuilder builder = Response
                .created(location);
        return PubUtils.addLinks(builder, pubChirp.getLinks()).build();
    }
}
