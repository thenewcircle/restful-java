package com.example.util.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileRepositoryManager {

	private static Logger logger = LoggerFactory
			.getLogger(FileRepositoryManager.class);

	private Map<Class<?>, Object> rawRepositories = new HashMap<Class<?>, Object>();
	private Map<Class<?>, Object> decoratedRepositories = new HashMap<Class<?>, Object>();

	private File file = new File("state.bin");

	public <T> T lookup(Class<T> interfaceType) {
		Object repo = decoratedRepositories.get(interfaceType);
		T result = interfaceType.cast(repo);
		return result;
	}

	public <T> T registerDefault(Class<T> interfaceType,
			final T defaultImplementation) {
		T result = lookup(interfaceType);
		if (result == null) {
			result = reregister(interfaceType, defaultImplementation);
		}
		return result;
	}

	public <T> T reregister(Class<T> interfaceType,
			final T defaultImplementation) {
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Object result = method.invoke(defaultImplementation, args);
				save();
				return result;
			}
		};
		Class<?> interfaces[] = defaultImplementation.getClass()
				.getInterfaces();
		ClassLoader loader = defaultImplementation.getClass().getClassLoader();
		Object proxy = Proxy.newProxyInstance(loader, interfaces, handler);
		T result = interfaceType.cast(proxy);
		rawRepositories.put(interfaceType, defaultImplementation);
		decoratedRepositories.put(interfaceType, result);
		return result;
	}

	/**
	 * Call this method to create user repository from the file
	 * <code>state.bin</code> if it exists. This method is not thread-safe.
	 */
	@SuppressWarnings("unchecked")
	public synchronized void load() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				file))) {
			rawRepositories = (HashMap<Class<?>, Object>) in.readObject();
			logger.trace("Repository state loaded from file.");
		} catch (Exception e) {
			logger.error("Repository state failed to load from file.  Using fresh repository.");
			rawRepositories = new HashMap<Class<?>, Object>();
		}
	}

	/**
	 * Call this method to persist the state of the user repository to the file
	 * <code>state.bin</code>.
	 */
	public synchronized void save() {
		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(file))) {
			out.writeObject(rawRepositories);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFilename(String filename) {
		this.file = new File(filename);
	}

}
