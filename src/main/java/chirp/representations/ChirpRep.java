package chirp.representations;

import java.net.URI;

import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;
import chirp.model.ChirpId;

public class ChirpRep {

  @XmlElement 
  private URI self;

  @XmlAttribute
  private final String id;

  @XmlElement
  private final String content;

  private ChirpRep() {
    id = null;
    content = null;
  }

  public ChirpRep(Chirp chirp, boolean summary, UriInfo uriInfo) {
    this.id = chirp.getId().toString();
    this.content = summary ? null : chirp.getContent();
    this.self = uriInfo.getBaseUriBuilder().path("chirps").path(id).build();
  }

  public URI getSelf() {
    return self;
  }
  
  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
