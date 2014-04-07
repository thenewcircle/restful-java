package com.example.util.dao;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.example.chirp.services.DuplicateEntityException;
import com.example.chirp.services.NoSuchEntityException;

/** This class is thread-safe (and does this without requiring locking). */
public class SimpleGuidRepository implements GuidRepository, Serializable {

	private static final long serialVersionUID = -8454157781303511569L;

	private static final int GUID_SIZE = 6;

	public final ConcurrentHashMap<String, Long> guids = new ConcurrentHashMap<String, Long>();

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.util.dao.GuidRepository#createGuid(Entity)
	 */
	@Override
	public String createGuid(Entity entity) {
		while (true) {
			String guid = randomString();
			long id = entity.getId();
			Long existingId = guids.putIfAbsent(guid, id);
			if (existingId == null || id == existingId.longValue()) {
				// Success, we generated a unique ID.
				return guid;
			} else {
				// If someone already claimed this random GUID, loop to try
				// again.
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.util.dao.GuidRepository#registerGuid(String, Entity)
	 */
	@Override
	public void registerGuid(String guid, Entity entity) {
		long id = System.identityHashCode(entity);
		Long existingId = guids.putIfAbsent(guid, id);
		if (existingId != null && id != existingId.longValue()) {
			throw new DuplicateEntityException(entity.getClass(), guid);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.util.dao.GuidRepository#lookupGuid(String, Class)
	 */
	@Override
	public long lookupGuid(String guid, Class<? extends Entity> entityType) {
		Long id = guids.get(guid);
		if (id == null) {
			throw new NoSuchEntityException(entityType, guid);
		}
		return id.longValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.util.dao.GuidRepository#isExistingGuid(String)
	 */
	@Override
	public boolean isExistingGuid(String guid) {
		boolean result = guids.containsKey(guid);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.example.util.dao.GuidRepository#clear()
	 */
	@Override
	public void clear() {
		guids.clear();
	}

	private String randomString() {
		StringBuilder buf = new StringBuilder(GUID_SIZE);
		for (int i = 0; i < GUID_SIZE; i++) {
			char ch = randomLetter();
			buf.append(ch);
		}
		String result = buf.toString();
		return result;
	}

	private char randomLetter() {
		char letter = (char) (Math.random() * 26 + 'a');
		return letter;
	}

}
