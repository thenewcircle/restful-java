package com.example.chirp.pub;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PubChirps {

	private final URI self;
	
	private final List<PubChirp> chirps = new ArrayList<>();

	public PubChirps(URI self, Collection<PubChirp> chirps) {
		this.self = self;

		if (chirps != null) {
			this.chirps.addAll(chirps);
		}
	}

	public URI getSelf() {
		return self;
	}

	public List<PubChirp> getChirps() {
		return chirps;
	}
	
	
}
