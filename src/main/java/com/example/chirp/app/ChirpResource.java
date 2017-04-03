package com.example.chirp.app;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubUtils;
import com.example.chirp.app.stores.UserStore;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/chirps")
public class ChirpResource {

    private UserStore userStore = ChirpApplication.USER_STORE;

    @GET
    @Path("/{chirpId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getChirp(@PathParam("chirpId") String chirpId,
                             @Context UriInfo uriInfo) {

        Chirp chirp = userStore.getChirp(chirpId);
        PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);
        Response.ResponseBuilder builder = Response.ok(pubChirp);
        return PubUtils.addLinks(builder, pubChirp.getLinks()).build();
    }
}