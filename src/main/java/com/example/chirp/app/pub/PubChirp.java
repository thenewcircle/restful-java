package com.example.chirp.app.pub;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PubChirp {

	private final String id;
	private final String content;
	// private final java.util.Date createdAt;

	private final URI self;
	private final URI chirpsLink;
	private final URI userLink;

	@JsonCreator
	public PubChirp(@JsonProperty("id") String id, @JsonProperty("content") String content, @JsonProperty("self") URI self,
			@JsonProperty("chirpsLink") URI chirpsLink, @JsonProperty("userLink") URI userLink) {

		this.id = id;
		this.content = content;
		this.self = self;
		this.chirpsLink = chirpsLink;
		this.userLink = userLink;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public URI getSelf() {
		return self;
	}

	public URI getChirpsLink() {
		return chirpsLink;
	}

	public URI getUserLink() {
		return userLink;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chirpsLink == null) ? 0 : chirpsLink.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((self == null) ? 0 : self.hashCode());
		result = prime * result + ((userLink == null) ? 0 : userLink.hashCode());
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
		PubChirp other = (PubChirp) obj;
		if (chirpsLink == null) {
			if (other.chirpsLink != null)
				return false;
		} else if (!chirpsLink.equals(other.chirpsLink))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (self == null) {
			if (other.self != null)
				return false;
		} else if (!self.equals(other.self))
			return false;
		if (userLink == null) {
			if (other.userLink != null)
				return false;
		} else if (!userLink.equals(other.userLink))
			return false;
		return true;
	}
}
