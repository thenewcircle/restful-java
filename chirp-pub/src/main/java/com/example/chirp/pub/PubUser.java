package com.example.chirp.pub;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

public class PubUser {

  private final URI self;
  private final URI parent;
  private final String username;
  private final String realName;

  public PubUser() {
	  self = null;
	  parent = null;
	  username = null;
	  realName = null;
  }
  
  public PubUser(URI self, URI parent, String username, String realName) {
	super();
	this.self = self;
	this.parent = parent;
	this.username = username;
	this.realName = realName;
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
