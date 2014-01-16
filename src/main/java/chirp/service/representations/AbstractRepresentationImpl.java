package chirp.service.representations;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;

/**
 * Default implementation of a the Representation interface exposing properties
 * as XML elements using JAXB annotations.
 * 
 * @author Gordon Force
 * 
 */
public abstract class AbstractRepresentationImpl implements Representation {

	private URI self;

	public AbstractRepresentationImpl(URI self) {
		this.self = self;
	}

	@Override
	@XmlElement(name = "self")
	public URI getSelf() {
		return self;
	}

}
