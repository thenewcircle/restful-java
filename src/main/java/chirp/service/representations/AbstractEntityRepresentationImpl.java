package chirp.service.representations;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;

public abstract class AbstractEntityRepresentationImpl implements
		EntityRepresentation {
	
	private URI self;

	public AbstractEntityRepresentationImpl(URI self) {
		this.self = self;
	}

	@Override
	@XmlElement
	public URI getSelf() {
		return self;
	}

}
