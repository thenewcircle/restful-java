package chirp.service.representations;

public interface AbstractRepresentationFactory {
	
	RepresentationFactory<?,? extends Representation> createFactory(Object object);

}
