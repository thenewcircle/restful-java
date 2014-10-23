package chirp.service.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import chirp.model.UserRepository;
import chirp.service.representations.UserCollectionRepresentation;
import chirp.service.representations.UserRepresentation;

public class UserResourceTest extends JerseyResourceTest {

    private final UserRepository userRepository = UserRepository.getInstance();

    @Before
    public void testInit() {
        userRepository.clear();
    }

    private Response
            createUser(String username, String realname, Response.Status expectedResponse) {

        final Form userForm = new Form().param("realname", realname).param("username", username);

        log.info("Created user {} with realname {} via POST", username, realname);

        return postFormData("users", userForm, expectedResponse);

    }

    private void createUserSuccess(MediaType readAcceptHeader) {

        Response response = createUser("bob", "Bob Student", Response.Status.CREATED);

        // You wan't to an object from the server -- User
        // the entity to read is in the previous response's location header

        response = getEntity(response.getLocation(), readAcceptHeader, Response.Status.OK);

        verifyLinkHeaderExists("up", readAcceptHeader, response);
        verifyLinkHeaderExists("self", readAcceptHeader, response);
        verifyLinkHeaderExists("related", readAcceptHeader, response); // no chirps created yet

        log.info("Read user entity from the resposne");
        final UserRepresentation user = readEntity(response, UserRepresentation.class);

        assertNotNull(user);
        assertEquals("bob", user.getUsername());
        assertEquals("Bob Student", user.getRealname());

    }

    @Test
    public void createUserSuccessWithJsonVerify() {
        log.info("Test: create user success with Json Verify");
        createUserSuccess(MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void createUserSuccessWithXmlVerify() {
        log.info("Test: create user success with XML Verify");
        createUserSuccess(MediaType.APPLICATION_XML_TYPE);
    }

    @Test
    public void createDuplicateUserFail() {
        logStart("Verify 403 response when adding the same user a second time");
        createUser("bob", "Bob Student", Response.Status.CREATED);
        createUser("bob", "Bob Student", Response.Status.FORBIDDEN);
    }

    @Test
    public void createUserCollectionSuccess() {

        log.info("Test: Creating a collection of two unique users should succeed.");
        createUser("bob", "Bob Student", Response.Status.CREATED);
        createUser("cole", "Cole Student", Response.Status.CREATED);

        for (final UserRepresentation user : readEntity("/users",
                MediaType.APPLICATION_JSON_TYPE, UserCollectionRepresentation.class).getUsers()) {

            getHead(user.getSelf(), MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
        }
    }

    @Test
    public void verifyUserCollectionLinkHeaders() {

        logStart("Verify link headers for a collection of users");

        createUser("bob", "Bob Student", Response.Status.CREATED);
        createUser("cole", "Cole Student", Response.Status.CREATED);
        createUser("maddie", "Maddie Student", Response.Status.CREATED);

        final Response response =
                getHead("/users", MediaType.APPLICATION_JSON_TYPE, Response.Status.OK);
        verifyLinkHeaderExists("self", MediaType.APPLICATION_JSON_TYPE, response);
        verifyLinkHeaderExists("first", MediaType.APPLICATION_JSON_TYPE, response);
        verifyLinkHeaderExists("last", MediaType.APPLICATION_JSON_TYPE, response);

    }

}
