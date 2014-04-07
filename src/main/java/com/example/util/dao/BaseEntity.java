package com.example.util.dao;

import java.beans.Transient;
import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlAttribute;


@MappedSuperclass
public abstract class BaseEntity implements Entity, Serializable {

	private static final long serialVersionUID = 3865981154541626334L;
	
	private Long id;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlAttribute
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * The businessKey is used for caching and should be unchangeable once set.
	 * This ensures an object will always be stored in a consistent location in
	 * a HashMap. By default id is used, but if a more suitable immutable value
	 * exists, subclasses may use it.
	 * 
	 * The short-coming of using id is it might be set until after the object is
	 * inserted into the database, meaning the cacheKey would inadvertently
	 * change during persist.
	 */
	@Transient
	public Object getBusinessKey() {
		Long id = this.getId();
		return id;
	}

	protected BaseEntity() {
		this(null);
	}

	protected BaseEntity(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (!this.getClass().isInstance(o))
			return false;
		BaseEntity other = (BaseEntity) o;
		Object thisId = this.getBusinessKey();
		Object otherId = other.getBusinessKey();
		if (thisId == null) {
			return false;
		}
		if (otherId == null) {
			return false;
		}
		if (thisId.equals(otherId)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		Object key = this.getBusinessKey();
		if (key != null) {
			return key.hashCode();
		} else {
			return super.hashCode();
		}
	}

	@Override
	public String toString() {
		String name = this.getClass().getSimpleName();
		try {
			Object businessKey = getBusinessKey();
			if (businessKey != id) {
				String result = name + "[id=" + id + ", key=" + businessKey + "]";
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = name + "[id=" + id + "]";
		return result;
	}

}
