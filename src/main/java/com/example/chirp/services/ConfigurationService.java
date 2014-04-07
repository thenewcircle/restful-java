package com.example.chirp.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.util.dao.GuidRepository;
import com.example.util.dao.SimpleGuidRepository;

public class ConfigurationService {

	private static Logger logger = LoggerFactory
			.getLogger(ConfigurationService.class);

	private static File file = new File("state.bin");

	private static GuidRepository guidRepository;
	private static ChirpRepository chirpRepository;

	static {
		guidRepository = new SimpleGuidRepository();
		chirpRepository = new SimpleChirpRepository(guidRepository);
	}

	public static ChirpRepository getChirpRepository() {
		return chirpRepository;
	}

	public static GuidRepository getGuidRepository() {
		return guidRepository;
	}

	/**
	 * Erases the state of all repositories and then populates the database with
	 * predefined starter data.
	 * 
	 * This method is not thread-safe and should only be called when there is
	 * only one thread running.
	 */
	public static void resetAndSeedRepository() {
		guidRepository.clear();
		chirpRepository.clear();
		chirpRepository.createUser("maul", "Darth Maul");
		chirpRepository.createUser("luke", "Luke Skywaler");
		chirpRepository.createUser("vader", "Darth Vader");
		chirpRepository.createUser("yoda", "Master Yoda");
		chirpRepository.createPost("yoda", "Do or do not.  There is no try.",
				"wars01");
		chirpRepository
				.createPost(
						"yoda",
						"Fear leads to anger, anger leads to hate, and hate leads to suffering.",
						"wars02");
		chirpRepository.createPost("vader",
				"You have failed me for the last time.", "wars03");
	}

	/**
	 * Load the repositories from <code>state.bin</code> if it exists.
	 * 
	 * This method is not thread-safe and should only be called when there is
	 * only one thread running.
	 */
	public static synchronized void thawRepositories() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				file))) {
			guidRepository = (GuidRepository) in.readObject();
			chirpRepository = (ChirpRepository) in.readObject();
			logger.info("Repository state loaded from file.");
		} catch (Exception e) {
			logger.error("Repository state failed to load from file.  Using fresh repository.");
			guidRepository = new SimpleGuidRepository();
			chirpRepository = new SimpleChirpRepository(guidRepository);
		}
	}

	/**
	 * Persist the repositories to the file <code>state.bin</code>.
	 * 
	 * This method is not thread-safe and should only be called when there is
	 * only one thread running.
	 */
	public static synchronized void freezeRepositories() {
		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(file))) {
			out.writeObject(guidRepository);
			out.writeObject(chirpRepository);
			logger.info("Repository state flushed to file.");
		} catch (IOException e) {
			logger.error("Error saving repository state to file.", e);
		}
	}

	/**
	 * Gets the file used to freeze and thaw the repositories. This method is
	 * used principally for unit-testing the thaw/freeze process.
	 */
	public static synchronized File getRepositoryFile() {
		return ConfigurationService.file;
	}

	/**
	 * Changes the file used to freeze and thaw the repositories. This method is
	 * used principally for unit-testing the thaw/freeze process.
	 */
	public static synchronized void setRepositoryFile(File file) {
		ConfigurationService.file = file;
	}

}
