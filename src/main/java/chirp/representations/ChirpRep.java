package chirp.representations;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import chirp.model.Chirp;
import chirp.model.ChirpId;

public class ChirpRep {

  @XmlAttribute
  private final String id;

  @XmlElement
  private final String content;

  private ChirpRep() {
    id = null;
    content = null;
  }

  public ChirpRep(Chirp chirp, boolean summary) {
    this.id = chirp.getId().toString();
    this.content = summary ? null : chirp.getContent();
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
