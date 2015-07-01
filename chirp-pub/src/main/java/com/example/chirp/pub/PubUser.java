package com.example.chirp.pub;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PubUser {

  private final URI self;
  private final URI parent;
  private final String username;
  private final String realName;

  private final List<URI> chirpLinks = new ArrayList<>();
  private final List<PubChirp> chirps = new ArrayList<>();

  @JsonCreator
  public PubUser(@JsonProperty("self") URI self, 
		  		 @JsonProperty("parent") URI parent, 
		  		 @JsonProperty("username") String username, 
		  		 @JsonProperty("realName") String realName) {
	super();
	this.self = self;
	this.parent = parent;
	this.username = username;
	this.realName = realName;
  }
  
  public PubUser(URI self, URI parent, String username, String realName, PubChirp... chirps) {
	this(self, parent, username, realName);
	
	if (chirps != null) {
		Collections.addAll(this.chirps, chirps);
	}
  }
  
  public PubUser(URI self, URI parent, String username, String realName, URI... chirpLinks) {
	this(self, parent, username, realName);
	
	if (chirpLinks != null) {
		Collections.addAll(this.chirpLinks, chirpLinks);
	}
  }

  public List<URI> getChirpLinks() {
	return chirpLinks;
  }

  public List<PubChirp> getChirps() {
	return chirps;
  }

	public URI getSelf() {
		return self;
	}
	
	public URI getParent() {
		return parent;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getRealName() {
		return realName;
	}
	
	  
	}
