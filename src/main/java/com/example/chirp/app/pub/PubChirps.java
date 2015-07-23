package com.example.chirp.app.pub;

import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubChirps {

	private final URI self;

	private final URI next;
	private final URI previous;
	private final URI first;
	// private final URI last;

	private final int limit;
	private final int offset;
	// private final boolean complete;

	@JsonInclude(Include.NON_EMPTY)
	private final List<PubChirp> chirps;

	@JsonInclude(Include.NON_EMPTY)
	private final List<URI> links;

	@JsonCreator
	public PubChirps(@JsonProperty("self") URI self, 
			         @JsonProperty("next") URI next, 
			         @JsonProperty("previous") URI previous, 
			         @JsonProperty("first") URI first, 
			         @JsonProperty("limit") int limit, 
			         @JsonProperty("offset") int offset, 
			         @JsonProperty("chirps") List<PubChirp> chirps, 
			         @JsonProperty("links") List<URI> links) {

		this.self = self;
		this.next = next;
		this.previous = previous;
		this.first = first;
		this.limit = limit;
		this.offset = offset;
		this.chirps = chirps;
		this.links = links;
	}

	public URI getSelf() {
		return self;
	}

	public URI getNext() {
		return next;
	}

	public URI getPrevious() {
		return previous;
	}

	public URI getFirst() {
		return first;
	}

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

	public List<PubChirp> getChirps() {
		return chirps;
	}

	public List<URI> getLinks() {
		return links;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chirps == null) ? 0 : chirps.hashCode());
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + limit;
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + offset;
		result = prime * result + ((previous == null) ? 0 : previous.hashCode());
		result = prime * result + ((self == null) ? 0 : self.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PubChirps other = (PubChirps) obj;
		if (chirps == null) {
			if (other.chirps != null)
				return false;
		} else if (!chirps.equals(other.chirps))
			return false;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (limit != other.limit)
			return false;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (offset != other.offset)
			return false;
		if (previous == null) {
			if (other.previous != null)
				return false;
		} else if (!previous.equals(other.previous))
			return false;
		if (self == null) {
			if (other.self != null)
				return false;
		} else if (!self.equals(other.self))
			return false;
		return true;
	}
}
