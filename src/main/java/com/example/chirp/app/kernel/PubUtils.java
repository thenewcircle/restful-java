package com.example.chirp.app.kernel;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import com.example.chirp.app.UserResource;
import com.example.chirp.app.UserResource.Variant;
import com.example.chirp.app.pub.PubChirp;
import com.example.chirp.app.pub.PubChirps;
import com.example.chirp.app.pub.PubUser;

public class PubUtils {

	public static Variant toVariant(String variantString) {
		try {
			if (variantString == null || variantString.isEmpty()) {
				return Variant.STANDARD;
			} else {
				return Variant.valueOf(variantString);
			}
		} catch (Exception e) {
			String msg = String.format("The value %s is not a valid Variant.", variantString);
			throw new BadRequestException(msg, e);
		}
	}

	public static int toInt(String stringValue, String propertyName) {
		try {
			if (stringValue == null) return 0;
			return Integer.valueOf(stringValue);

		} catch (Exception e) {
			String msg = String.format("Unable to convert the \"%s\" query parameter (%s) to an intergral value.", propertyName, stringValue);
			throw new BadRequestException(msg);
		}
	}
	
	public static PubChirps toPubChirps(User user, UriInfo uriInfo, String variantString, String offsetString, String limitString, boolean embedded) {

		Variant variant = toVariant(variantString);

		if (embedded && UserResource.Variant.STANDARD == variant) {
			return null;
		}

		List<Chirp> chirps = new ArrayList<>(user.getChirps());
		
		int offset = toInt(offsetString, "offset");
		if (offset < 0) offset = 0;
		
		int limit = toInt(limitString, "limit");
		if (limit <= 0) limit = 4;
		
		URI self 	 = uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).path("chirps").queryParam("limit", limit).queryParam("offset", offset).build();
		
		URI next 	 = (offset > chirps.size()) ? null : uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).path("chirps").queryParam("limit", limit).queryParam("offset", offset + limit).build();
		URI previous = (offset <= 0) ?            null : uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).path("chirps").queryParam("limit", limit).queryParam("offset", offset - limit).build();

		URI first 	 = uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).path("chirps").queryParam("limit", limit).queryParam("offset", 0).build();

		List<PubChirp> pubChirps = new ArrayList<>();
		List<URI> links = new ArrayList<>();

		for (int i = 0; i < chirps.size(); i++) {
			if (i < offset) continue;
			if (i >= offset+limit) continue;
			
			Chirp chirp = chirps.get(i);
			
			if (UserResource.Variant.FULL == variant || UserResource.Variant.STANDARD == variant) {
				PubChirp pubChirp = toPubChirp(chirp, uriInfo);
				pubChirps.add(pubChirp);

			} else if (UserResource.Variant.LINKS == variant) {
				PubChirp pubChirp = toPubChirp(chirp, uriInfo);
				links.add(pubChirp.getSelf());
			}
		}

		return new PubChirps(self, next, previous, first, limit, offset, pubChirps, links);
	}

	public static PubUser toPubUser(User user, UriInfo uriInfo, String variantString, String offsetString, String limitString) {

		URI selfLink =   uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).build();
		URI chirpsLink = uriInfo.getBaseUriBuilder().path("users").path(user.getUsername()).path("chirps").build();

		PubChirps pubChirps = toPubChirps(user, uriInfo, variantString, offsetString, limitString, true);

		return new PubUser(chirpsLink, selfLink, user.getUsername(), user.getRealname(), pubChirps);
	}

	public static PubChirp toPubChirp(Chirp chirp, UriInfo uriInfo) {

		// http://localhost:8080/chirp-app/chrips/123
		URI self = uriInfo.getBaseUriBuilder().path("chirps").path(chirp.getId().toString()).build();

		// http://localhost:8080/chirp-app/users/tom/chirps
		URI chirpsLink = uriInfo.getBaseUriBuilder().path("users").path(chirp.getUser().getUsername()).path("chirps").build();

		// http://localhost:8080/chirp-app/users/tom
		URI userLink = uriInfo.getBaseUriBuilder().path("users").path(chirp.getUser().getUsername()).build();

		// http://localhost:8080/chirp-app/users/
		// URI allUsersLink = uriInfo.getBaseUriBuilder().path("users").build();

		return new PubChirp(chirp.getId().getId(), chirp.getContent(), self, chirpsLink, userLink);
	}
}
