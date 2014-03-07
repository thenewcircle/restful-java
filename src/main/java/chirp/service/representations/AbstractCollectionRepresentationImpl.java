package chirp.service.representations;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;

public abstract class AbstractCollectionRepresentationImpl<E,R extends Representation> extends
		AbstractRepresentationImpl implements CollectionRepresentation <R> {
	
	final private Collection<R> reps;

	public AbstractCollectionRepresentationImpl(URI self) {
		super(self);
		reps = new ArrayList<R>();
	}
	
	public AbstractCollectionRepresentationImpl(URI self, Collection<R> reps) {
		super(self);
		this.reps = reps; 
	}

	/**
	 * Use this method to create a representation based on a entity. 
	 * @param entity
	 * @return
	 */
	protected abstract R create(E entity);
	
	protected void addEntities(Collection<E> entities) {
		for (E entity : entities)
			reps.add(create(entity));
	}

	/* (non-Javadoc)
	 * @see chirp.service.representations.CollectionRepresentation#get()
	 */
	@Override
	@XmlElement
	public Collection<R> get() {
		return reps;
	}

}