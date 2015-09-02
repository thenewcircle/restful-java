package com.example.chirp.app.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;

public class PubUtils {
  public static PubChirps toPubChirps(UriInfo uriInfo, 
                                      User user,
                                      String limitString,
                                      String offsetString) {

    int limit;
    int offset;

    try {
      if (limitString == null) limitString = "5";
      limit = Integer.valueOf(limitString);
    } catch (Exception e) {
      throw new BadRequestException("The limit ("+limitString+") must be an integral value.");
    }

    try {
      if (offsetString == null) offsetString = "0";
      offset = Integer.valueOf(offsetString);
    } catch (Exception e) {
      throw new BadRequestException("The offset ("+offsetString+") must be an integral value.");
    }


    int total = user.getChirps().size();
    int count = 0;

    int index = 0;
    List<PubChirp> pubChirps = new ArrayList<>();
    for (Chirp chirp : user.getChirps()) {
      if (index < offset) {
        index++;
        continue;

      } else if (index >= offset+limit) {
        index++;
        continue;
      }

      PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);
      pubChirps.add(pubChirp);
      count++;
      index++;
    }

    Map<String,URI> links = new LinkedHashMap<>();

    links.put("self", uriInfo.getBaseUriBuilder()
        .path("users")
        .path(user.getUsername())
        .path("chirps")
        .queryParam("offset", offset)
        .queryParam("limit", limit)
        .build());

    links.put("user", uriInfo.getBaseUriBuilder()
        .path("users")
        .path(user.getUsername())
        .build());

    links.put("first", uriInfo.getBaseUriBuilder()
        .path("users")
        .path(user.getUsername())
        .path("chirps")
        .queryParam("offset", Math.min(0, offset))
        .queryParam("limit", limit)
        .build());

    links.put("last", uriInfo.getBaseUriBuilder()
        .path("users")
        .path(user.getUsername())
        .path("chirps")
        .queryParam("offset", Math.max(0, user.getChirps().size() - limit))
        .queryParam("limit", limit)
        .build());

    links.put("prev", uriInfo.getBaseUriBuilder()
        .path("users")
        .path(user.getUsername())
        .path("chirps")
        .queryParam("offset", offset - limit)
        .queryParam("limit", limit)
        .build());

    links.put("next", uriInfo.getBaseUriBuilder()
        .path("users")
        .path(user.getUsername())
        .path("chirps")
        .queryParam("offset", offset + limit)
        .queryParam("limit", limit)
        .build());

    return new PubChirps(
        links,
        pubChirps,
        limit,
        offset,
        total,
        count);
  }
  
  public static PubChirp toPubChirp(UriInfo uriInfo, Chirp chirp) {
    User user = chirp.getUser();
    Map<String,URI> links = new LinkedHashMap<>();

    links.put("self", uriInfo.getBaseUriBuilder()
      //.path("users").path(user.getUsername())
      .path("chirps").path(chirp.getId().getId()).build());
   
    links.put("chirps", uriInfo.getBaseUriBuilder()
      .path("users").path(user.getUsername()).path("chirps").build());
    
    links.put("user", uriInfo.getBaseUriBuilder()
      .path("users").path(user.getUsername()).build());

    return new PubChirp(links, chirp.getId().getId(), chirp.getContent());
  }
  
  public static ResponseBuilder addLinks(ResponseBuilder builder, Map<String, URI> links) {
    for (Map.Entry<String, URI> entry : links.entrySet()) {
      builder.link(entry.getValue(), entry.getKey());
    }
    return builder;
  }
  
  public static PubUser toPubUser(UriInfo uriInfo, 
                                  User user, 
                                  String limitString, 
                                  String offsetString,
                                  List<String> includes) {
    
    Map<String,URI> links = new LinkedHashMap<>();

    URI selfLink = uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).build();
    links.put("self", selfLink);

    links.put("chirps", uriInfo.getBaseUriBuilder()
      .path("users").path(user.getUsername()).path("chirps").build());

    PubChirps pubChirps = null;
    if (includes.contains("chirps")) {
      pubChirps = toPubChirps(uriInfo, user, limitString, offsetString);
    }
    
    return new PubUser(links, user.getUsername(), user.getRealName(), pubChirps);
  }
}







