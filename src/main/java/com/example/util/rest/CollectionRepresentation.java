package com.example.util.rest;

import static com.example.util.rest.LinkRel.CANONICAL;
import static com.example.util.rest.LinkRel.CURRENT;
import static com.example.util.rest.LinkRel.NEXT;
import static com.example.util.rest.LinkRel.PREVIOUS;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

public class CollectionRepresentation extends Representation {

	/**
	 * Adds hyperlinks for current, next, and previous.
	 */
	public final void linkPagination(URI self, long position, long pageSize) {
		super.link(CANONICAL, self);
		super.link(CURRENT, paginateUri(self, position, pageSize));
		long nextPosition = position + pageSize;
		link(NEXT, paginateUri(self, nextPosition, pageSize));
		if (position > 0) {
			long prevPosition = position - pageSize;
			long prevSize = pageSize;
			if (prevPosition < 0) {
				prevSize = pageSize+prevPosition;
				prevPosition = 0;
			}
			link(PREVIOUS, paginateUri(self, prevPosition, prevSize));
		}
	}
	
	public final void linkPagination(URI self, long position, long pageSize, long total) {
		super.link(CURRENT, paginateUri(self, position, pageSize));
		long nextPosition = position + pageSize;
		if (nextPosition < total && nextPosition > 0) {
			link(NEXT, paginateUri(self, nextPosition, pageSize));
		}
		if (position > 0) {
			long prevPosition = position - pageSize;
			long prevSize = pageSize;
			if (prevPosition < 0) {
				prevSize = pageSize+prevPosition;
				prevPosition = 0;
			}
			link(PREVIOUS, paginateUri(self, prevPosition, prevSize));
		}
	}
	
	public static URI paginateUri(URI uri, long offset, long limit) {
		return UriBuilder.fromUri(uri).queryParam("offset", offset).queryParam("limit", limit).build();
	}

}
