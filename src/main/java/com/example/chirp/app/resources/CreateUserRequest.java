package com.example.chirp.app.resources;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class CreateUserRequest {
  
	@PathParam("username") String username;
  @FormParam("realName") String realName;
  @Context UriInfo uriInfo;

  public String getUsername() { return username; }
  public String getRealName() { return realName; }
  public UriInfo getUriInfo() { return uriInfo; }
  
  public void validate() {
    if (username.contains(" ")) {
      throw new BadRequestException("The username must not contain spaces.");
    }
  }
}
