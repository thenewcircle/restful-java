package com.example.chirp.app;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.pub.PubUser;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PubUtils {

    public static PubChirps toPubChirps(UriInfo uriInfo,
                                        User user,
                                        String limitString,
                                        String offsetString) {

        int limit;
        int offset;

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
                pubChirps,
                limit,
                offset,
                total,
                count);
    }

    public static PubChirp toPubChirp(UriInfo uriInfo, Chirp chirp) {
        User user = chirp.getUser();
        Map<String, URI> links = new LinkedHashMap<>();

        links.put("self", toChirpLink(uriInfo, chirp.getId().getId()));
        links.put("chirps", toChirpsLink(uriInfo, user.getUsername()));
        links.put("user", toUserLink(uriInfo, user.getUsername()));

        return new PubChirp(links, chirp.getId().getId(), chirp.getContent());
    }

    public static URI toChirpLink(UriInfo uriInfo, String chirpId) {
        return uriInfo.getBaseUriBuilder()
                .path("chirps")
                .path(chirpId)
                .build();
    }

    public static PubUser toPubUser(UriInfo uriInfo, User user) {

        Map<String, URI> links = new LinkedHashMap<>();
        links.put("self", toUserLink(uriInfo, user.getUsername()));
        links.put("chirps", toChirpsLink(uriInfo, user.getUsername()));

        return new PubUser(links, user.getUsername(), user.getRealName());
    }

    public static URI toChirpsLink(UriInfo uriInfo, String username) {
        return uriInfo.getBaseUriBuilder()
                .path("users")
                .path(username)
                .path("chirps")
                .build();
    }

    public static URI toUserLink(UriInfo uriInfo, String username) {
        return uriInfo.getBaseUriBuilder()
                .path("users")
                .path(username)
                .build();
    }


    public static Response.ResponseBuilder addLinks(Response.ResponseBuilder builder, Map<String, URI> links) {
        for (Map.Entry<String, URI> entry : links.entrySet()) {
            builder.link(entry.getValue(), entry.getKey());
        }
        return builder;
    }
}