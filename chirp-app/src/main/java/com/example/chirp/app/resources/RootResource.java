package com.example.chirp.app.resources;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Named // required by RESTEasy
@Path("/")
public class RootResource {

//  @Inject
//  private UserStore usersStore;

  @GET
  @Produces(MediaType.TEXT_HTML)
	public String ack(@Context UriInfo uriInfo) {

	  String html = "" +
        "<html>\n" +
        "  <body>\n" +
        "    <h1>RESTful Java with JAX-RS 2.0</h1>\n" +
        "    <table>\n" +
        "      <tr><td>App Server: </td><td><b>" + getContainerName() + "</b></td></tr>\n" +
        "      <tr><td>JAX-RS Provider: </td><td><b>" + getProviderName() + "</b></td></tr>\n" +
//        "      <tr><td>Selected Store: </td><td><b>" + usersStore.getClass().getName() + "</b></td></tr>\n" +
//        "      <tr><td>Spring Version: </td><td><b>" + SpringVersion.getVersion() + "</b></td></tr>\n" +
//        "      <tr><td>Test Links:</td><td><a target='_blank' href='"+uriInfo.getAbsolutePathBuilder().path("greetings").build()+"'>/greetings</a></td></tr>\n" +
//        "      <tr><td></td>           <td><a target='_blank' href='"+uriInfo.getAbsolutePathBuilder().path("greetings").queryParam("name", "Mickey Mouse").build()+"'>/greetings?name=Mickey Mouse</a></td></tr>\n" +
//        "      <tr><td></td>           <td><a target='_blank' href='"+uriInfo.getAbsolutePathBuilder().path("users").build()+"'>/users</a></td></tr>\n" +
//        "      <tr><td></td>           <td><a target='_blank' href='"+uriInfo.getAbsolutePathBuilder().path("users").path("yoda").build()+"'>/users/yoda</a></td></tr>\n" +
//        "      <tr><td></td>           <td><a target='_blank' href='"+uriInfo.getAbsolutePathBuilder().path("users").path("yoda").path("chirps").build()+"'>/users/yoda/chirps</a></td></tr>\n" +
        "    </table>\n" +
        "  </body>\n" +
        "</html>";

    return html;
	}

  private String getContainerName() {
    try {
      Class.forName("org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer");
      return "Grizzly";
    } catch (ClassNotFoundException ignored) { }

    try {
      Class.forName("org.jboss.ejb.client.EJBClientInterceptor");
      return "JBoss";
    } catch (ClassNotFoundException ignored) { }

    try {
      Class.forName("org.apache.catalina.connector.Connector");
      return "Tomcat";
    } catch (ClassNotFoundException ignored) { }

    return "unknown";
  }

  private String getProviderName() {
    try {
      Class.forName("org.glassfish.jersey.uri.internal.JerseyUriBuilder");
      return "Jersey";
    } catch (ClassNotFoundException ignored) { }

    try {
      Class.forName("org.jboss.resteasy.specimpl.ResteasyUriBuilder");
      return "RESTEasy";
    } catch (ClassNotFoundException ignored) { }

    return "unknown";
  }
}
