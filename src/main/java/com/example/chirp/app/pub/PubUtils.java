package com.example.chirp.app.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.providers.UriInfoRequestFilter;

public class PubUtils {

  private static UriInfo getUriInfo() {
    return UriInfoRequestFilter.tlui.get();
  }

  public static PubUser toPubUser(User user) {

    Map<String, URI> links = new LinkedHashMap<>();

    links.put("self", getUriInfo().getBaseUriBuilder().path("users").path(user.getUsername()).build());

    links.put("chirps", getUriInfo().getBaseUriBuilder().path("users").path(user.getUsername()).path("chirps").build());

    return new PubUser(links, user.getUsername(), user.getRealName());
  }

  public static void addLinks(ResponseBuilder builder, Map<String, URI> links) {
    for (Map.Entry<String, URI> link : links.entrySet()) {
      builder.link(link.getValue(), link.getKey());
    }
  }

  public static PubChirp toPubChirp(UriInfo uriInfo, Chirp chirp) {
    User user = chirp.getUser();
    Map<String, URI> links = new LinkedHashMap<>();

    //users/yoda/chirps/wars01
    //chirps/wars01
    links.put("self", uriInfo.getBaseUriBuilder()
        // .path("users")
        // .path(user.getUsername())
        .path("chirps")
        .path(chirp.getId().getId()).build());
    
    links.put("chirps", uriInfo.getBaseUriBuilder().path("users")
        .path(user.getUsername())
        .path("chirps")
        .build());
    
    links.put("user", uriInfo.getBaseUriBuilder()
        .path("users")
        .path(user.getUsername())
        .build());

    return new PubChirp(links, chirp.getId().getId(), chirp.getContent());
  }

  public static PubChirps toPubChirps(UriInfo uriInfo, User user,
      String limitString,
      String offsetString,
      String detailString) {

    int limit;
    int offset;
    boolean detail;

    if (detailString == null) detail = false;
    else if ("1".equals(detailString.toLowerCase())) detail = true;
    else if ("t".equals(detailString.toLowerCase())) detail = true;
    else if ("y".equals(detailString.toLowerCase())) detail = true;
    else if ("true".equals(detailString.toLowerCase())) detail = true;
    else detail = false;
    
    try {
      limit = Integer.valueOf(limitString);
    } catch (Exception e) {
      throw new BadRequestException("The limit must be an integral value.");
    }

    try {
      offset = Integer.valueOf(offsetString);
    } catch (Exception e) {
      throw new BadRequestException("The offset must be an integral value.");
    }


    int total = user.getChirps().size();
    int count = 0;

    int index = 0;
    List<PubChirp> pubChirps = new ArrayList<>();
    List<URI> itemLinks = new ArrayList<>();
    
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

      itemLinks.add(pubChirp.getLinks().get("self"));
      
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
        detail ? pubChirps : null,
        limit,
        offset,
        total,
        count,
        detail ? null : itemLinks);
  }}






















