package com.example.util.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

public interface EntityDao<T extends Entity> {

	public T findExactlyOne(long entityId) throws NoResultException;

	public T findOneOrNull(long entityId);

	public List<T> findAll();

	public void save(T entity) throws EntityExistsException;

	public T update(T entity);

	public T saveOrUpdate(T entity);

	public void delete(T entity);

	void delete(long entityId) throws EntityNotFoundException;

	boolean deleteIfExists(long entityId);

}
