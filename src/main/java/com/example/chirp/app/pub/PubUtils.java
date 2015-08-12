package com.example.chirp.app.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.kernel.Chirp;
import com.example.chirp.app.kernel.User;

public abstract class PubUtils {

	public static PubChirps toPubChirps(UriInfo uriInfo, User user, 
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

			} else if (index >= offset+limit) {
				index++;
				continue;
			}			
			
			PubChirp pubChirp = PubUtils.toPubChirp(uriInfo, chirp);
			pubChirps.add(pubChirp);
			count++;
			index++;
		}

		URI self = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(user.getUsername())
				.path("chirps")
				.queryParam("offset", offset)
				.queryParam("limit", limit)
				.build();

		URI userLink = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(user.getUsername())
				.build();
		
		URI first = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(user.getUsername())
				.path("chirps")
				.queryParam("offset", Math.min(0, offset))
				.queryParam("limit", limit)
				.build();
		
		URI last = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(user.getUsername())
				.path("chirps")
				.queryParam("offset", Math.max(0, user.getChirps().size() - limit))
				.queryParam("limit", limit)
				.build();

		URI prev = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(user.getUsername())
				.path("chirps")
				.queryParam("offset", offset - limit)
				.queryParam("limit", limit)
				.build();
		
		URI next = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(user.getUsername())
				.path("chirps")
				.queryParam("offset", offset + limit)
				.queryParam("limit", limit)
				.build();
		
		return new PubChirps(
				pubChirps,
				limit,
				offset,
				total,
				count,
				self,
				userLink,
				first,
				prev,
				next,
				last);
	}
	
	public static PubChirp toPubChirp(UriInfo uriInfo, Chirp chirp) {
		URI self = uriInfo.getBaseUriBuilder()
				.path("chirps")
				.path(chirp.getId().getId())
				.build();
		
		URI userLink = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(chirp.getUser().getUsername())
				.build();

		URI chirpsLink = uriInfo.getBaseUriBuilder()
				.path("users")
				.path(chirp.getUser().getUsername())
				.path("chirps")
				.build();
		
		return new PubChirp(chirp.getId().getId(), 
				            chirp.getContent(), 
				            self, 
				            chirpsLink, 
				            userLink);
	}
	
}
