package chirp.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;
import chirp.model.ChirpId;

public class ChirpRep {

  @XmlElement
  private final String id;

  @XmlAttribute
  private final String content;

  private ChirpRep() {
    id = null;
    content = null;
  }

  public ChirpRep(Chirp chirp) {
    this.id = chirp.getId().toString();
    this.content = chirp.getContent();
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
