package com.example.chirp.app.resources;

import java.net.URI;

import javax.ws.rs.BeanParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.ChirpId;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.pub.PubUser;
import com.example.chirp.app.pub.PubUtils;

@Path("/users")
public class UserResource {

  @Context UriInfo uriInfo;
  
  @PUT
  @Path("/{username}")
  public Response createUser(@BeanParam CreateUserRequest request) {

    request.validate();
    
    User user = ChirpApplication.USER_STORE.createUser(
        request.getUsername(), 
        request.getFullName());

    //URI uri = uriInfo.getAbsolutePathBuilder();
    URI uri = request.getUriInfo().getBaseUriBuilder()
        .path("/users")
        .path(request.getUsername())
        .build();

    return Response.created(uri).build();
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
  @Path("/{username}")
  public Response getUser(@PathParam("username") String username) {
    User user = ChirpApplication.USER_STORE.getUser(username);
    
    PubUser pubUser = PubUtils.toPubUser(uriInfo, user);
    ResponseBuilder builder = Response.ok(pubUser);
    PubUtils.addLinks(builder, pubUser.getLinks());
    return builder.build();
  }

  @POST
  @Path("/{username}/chirps")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response createChrip(@PathParam("username") String username, 
                          String content) {
    
    User user = ChirpApplication.USER_STORE.getUser(username);
    ChirpId chirpId = new ChirpId();
    Chirp chirp = new Chirp(chirpId, content, user);
    user.addChirp(chirp);
    ChirpApplication.USER_STORE.updateUser(user);
    
    PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);
    URI location = pubChirp.getLinks().get("self");
    ResponseBuilder builder = Response.created(location).entity(pubChirp);
    PubUtils.addLinks(builder, pubChirp.getLinks());
    return builder.build();
  }

  @GET
  @Path("/{username}/chirps")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response getChirps(@PathParam("username") String username,
                            @DefaultValue("5") @QueryParam("limit") String limit,
                            @DefaultValue("0") @QueryParam("offset") String offset) {

    User user = ChirpApplication.USER_STORE.getUser(username);

    PubChirps pubChirps = PubUtils.toPubChirps(uriInfo, user, limit, offset);
    
    ResponseBuilder builder = Response.ok(pubChirps);
    PubUtils.addLinks(builder, pubChirps.get_links());
    return builder.build();
  }
}




















