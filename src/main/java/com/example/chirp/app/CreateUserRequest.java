package com.example.chirp.app;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class CreateUserRequest {

    private @PathParam("username") String username;
    private @FormParam("realName") String realName;

    public String getUsername() {
        return username;
    }

    public String getRealName() {
        return realName;
    }

    public void validate() {
        if (username.contains(" ")) {
            String msg = "The specified username must not contain spaces.";
            throw new BadRequestException(msg);
        }
    }
}

