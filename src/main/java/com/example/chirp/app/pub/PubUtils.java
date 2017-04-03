package com.example.chirp.app.pub;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.*;

import static java.util.Collections.*;

public class PubUtils {

    public static PubUser toPubUser(UriInfo uriInfo, User user) {
        Map<String, URI> links = new LinkedHashMap<>();

        URI uri = uriInfo.getBaseUriBuilder()
                .path("users")
                .path(user.getUsername())
                .build();
        links.put("self", uri);

        uri = uriInfo.getBaseUriBuilder()
                .path("users")
                .path(user.getUsername())
                .path("chirps")
                .build();
        links.put("chirps", uri);

        return new PubUser(links, user.getUsername(), user.getRealName());
    }

    public static Response.ResponseBuilder addLinks(Response.ResponseBuilder builder, Map<String, URI> links) {
        for (Map.Entry<String, URI> entry : links.entrySet()) {
            builder.link(entry.getValue(), entry.getKey());
        }
        return builder;
    }

    public static PubChirp toPubChirp(UriInfo uriInfo, Chirp chirp) {
        User user = chirp.getUser();
        Map<String, URI> links = new LinkedHashMap<>();

        links.put("self", uriInfo.getBaseUriBuilder()
                .path("chirps")
                .path(chirp.getId().getId())
                .build());

        links.put("chirps", uriInfo.getBaseUriBuilder()
                .path("users")
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
                                        List<String> includes) {

        if (includes == null) {
            includes = singletonList("items");
        } else if (includes.isEmpty()) {
            includes.add("items");
        }

        int limit;
        int offset;

        try {
            if (limitString == null) {
                limit = 10;
            } else {
                limit = Integer.valueOf(limitString);
            }
        } catch (Exception e) {
            throw new BadRequestException("The limit must be an integral value.");
        }

        if (limit > 50) limit = 50;

        try {
            if (offsetString == null) {
                offset = 0;
            } else {
                offset = Integer.valueOf(offsetString);
            }
        } catch (Exception e) {
            throw new BadRequestException("The offset must be an integral value.");
        }


        int total = user.getChirps().size();
        int count = 0;

        int index = 0;

        List<PubChirp> pubChirps = new ArrayList<>();
        List<URI> chirpLinks = new ArrayList<>();

        for (Chirp chirp : user.getChirps()) {
            if (index < offset) {
                index++;
                continue;

            } else if (index >= offset + limit) {
                index++;
                continue;
            }

            PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);

            pubChirps.add(pubChirp);
            chirpLinks.add(pubChirp.getLinks().get("self"));

            count++;
            index++;
        }

        Map<String, URI> links = new LinkedHashMap<>();

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
                includes.contains("items") ? pubChirps : null,
                includes.contains("links") ? chirpLinks : null,
                limit,
                offset,
                total,
                count);
    }
}