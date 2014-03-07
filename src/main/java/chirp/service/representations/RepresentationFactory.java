package chirp.service.representations;

import java.util.Collection;
import java.util.List;

public interface RepresentationFactory<E,P extends Representation> {
	
	P create(E entity);
	
	List<P> create(Collection<E> entities);

}
