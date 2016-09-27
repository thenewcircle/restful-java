package com.example.chirp.app;

import com.example.chirp.app.stores.UserStore;

import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/users")
public class UserResource {

    private final UserStore userStore = ChirpApplication.USER_STORE;

    @PUT
    @Path("/{username}")
    public void createUser(@PathParam("username") String username,
                           @FormParam("realName") String realName) {

        userStore.createUser(username, realName);
    }
}
