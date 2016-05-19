package com.example.chirp.app.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubUtils;

@Path("/chirps")
public class ChirpResource {

  @Context UriInfo uriInfo;
  
  @GET
  @Path("/{chirpId}")
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response getChirp(@PathParam("chirpId") String chirpId) {

    Chirp chirp = ChirpApplication.USER_STORE.getChirp(chirpId);
    PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);
    
    // ResponseBuilder builder = Response.ok(pubChirp);
    ResponseBuilder builder = Response.ok().entity(pubChirp);
    
    PubUtils.addLinks(builder, pubChirp.getLinks());
    return builder.build();
  }
}











