package com.example.util.dao;

import com.example.chirp.services.DuplicateEntityException;

public interface GuidRepository {

	/**
	 * Generates a 6-letter guid for the entity. This is thread-safe without need for locking.
	 */
	public String createGuid(Entity entity);

	/**
	 * Register the GUID to the entity's databaseKey.
	 * 
	 * @throws DuplicateEntityException
	 *             if the GUID has already been registered with another entity.
	 */
	public void registerGuid(String guid, Entity entity);

	/**
	 * Returns the entity databaseKey that was registered to this GUID.
	 */
	public long lookupGuid(String guid, Class<? extends Entity> entityType);

	/**
	 * Returns true if and only if the GUID has been registered.
	 */
	public boolean isExistingGuid(String guid);
	
	/**
	 * Resets the database to a clean start.
	 */
	public void clear();
	
}
