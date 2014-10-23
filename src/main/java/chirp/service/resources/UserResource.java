package chirp.service.resources;

import java.util.Deque;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import chirp.model.User;
import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

@Path("/users")
public class UserResource {

    private final UserRepository userRepository = UserRepository.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUserUsingJson(UserRepresentation user) {

        userRepository.createUser(user.getUsername(), user.getRealname());

        return Response.created(
                UriBuilder.fromResource(this.getClass()).path(user.getUsername()).build())
                .build();

    }

    @POST
    public Response createUser(@FormParam("username") String username,
            @FormParam("realname") String realname) {

        userRepository.createUser(username, realname);

        // using a java.net.URI for creating a location
        // URI location = URI.create("/user/" + username);
        // return Response.created(location).build();

        return Response.created(UriBuilder.fromResource(this.getClass()).path(username).build())
                .build();

    }

    private Response createUserCollectionResponse(boolean isGet, UriInfo uriInfo) {

        final Deque<User> users = userRepository.getUsers();

        final ResponseBuilder rb =
                (isGet) ? Response.ok(new UserCollectionRepresentation(users, uriInfo))
                        : Response.ok();

        // return no links for if there are no users on the server
        if (users.size() > 0) {
            rb.links(

            // http://localhost:9998/users
            // title:self
                    Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()).rel("self").build(),

                    // http://localhost:9998/users/<first user name>
                    // rel: first
                    Link.fromUriBuilder(
                            uriInfo.getAbsolutePathBuilder().path(users.getFirst().getUsername()))
                            .rel("first").build(),

                    // http://localhost:9998/users/<last user name>
                    // rel: last
                    Link.fromUriBuilder(
                            uriInfo.getAbsolutePathBuilder().path(users.getLast().getUsername()))
                            .rel("last").build());
        }

        return rb.build();
    }

    @HEAD
    public Response headAllUsers(@Context UriInfo uriInfo) {
        return createUserCollectionResponse(false, uriInfo);
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getAllUsers(@Context UriInfo uriInfo) {
        return createUserCollectionResponse(true, uriInfo);
    }

    private Response createSingleUserResponse(boolean isGet, String username, UriInfo uriInfo) {
        final User user = userRepository.getUser(username);

        final ResponseBuilder rb =
                (isGet) ? Response.ok(new UserRepresentation(user, false, uriInfo
                        .getAbsolutePathBuilder().build())) : Response.ok();
        rb.links(

        // http://localhost:9998/users/gordonff
        // rel:self
        // title: Gordon Force (realname field)
                Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()).rel("self")
                        .title(user.getRealname()).build(),

                // http://localhost:9998/users
                // rel: up
                // title: all users
                Link.fromUriBuilder(
                        uriInfo.getBaseUriBuilder().path(
                                uriInfo.getPathSegments().get(0).getPath())).rel("up")
                        .title("all users").build("users"),

                // http://localhost:9998/chirps/gordonff
                // rel: related
                // title: Gordon Force chirps
                Link.fromUriBuilder(uriInfo.getBaseUriBuilder().path(ChirpResource.class))
                        .rel("related").title(user.getRealname() + " chirps").build(username)

        );

        return rb.build();
    }

    @HEAD
    @Path("{username}")
    public Response
            headResponse(@PathParam("username") String username, @Context UriInfo uriInfo) {
        return createSingleUserResponse(false, username, uriInfo);
    }

    @GET
    @Path("{username}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response getUser(@PathParam("username") String username, @Context UriInfo uriInfo) {
        return createSingleUserResponse(true, username, uriInfo);
    }

}
