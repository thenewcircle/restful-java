package com.example.chirp.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.example.chirp.app.support.LogbackUtil;

public class ChirpAppGrizzlyMain {

	private static final Logger log = LoggerFactory.getLogger(ChirpAppGrizzlyMain.class);

	private static final boolean OPEN_IN_BROWSER = true;

	private static final String CONTEXT = "chirp-app";
	private static final int SERVER_PORT = 8080;
	private static final int SHUTDOWN_PORT = 8081;
	private static final String SERVER_NAME = "localhost";

	private static final String ROOT_RESOURCE = String.format("http://%s:%d/%s/", SERVER_NAME, SERVER_PORT, CONTEXT);
	private static final String WADL_RESOURCE = ROOT_RESOURCE + "application.wadl";

	private static final int socketAcceptTimeoutMilli = 5000;

	private final ResourceConfig resourceConfig;

	private HttpServer httpServer;
	private ServerSocket socket;
	private Thread acceptThread;

	/**
	 * handlerLock is used to synchronize access to socket, acceptThread and
	 * callExecutor.
	 */
	private final ReentrantLock handlerLock = new ReentrantLock();

	public static void main(String[] args) throws Exception {
		 LogbackUtil.initLogback(Level.WARN);

		new ChirpAppGrizzlyMain().start();
	}

	public ChirpAppGrizzlyMain() {
		ChirpApplication application = new ChirpApplication();
		resourceConfig = ResourceConfig.forApplication(application);
	}

	public ResourceConfig getResourceConfig() {
		return resourceConfig;
	}

	/** Starts the server. */
	public void start() {
		try {
			doStart(resourceConfig);

			log.info(String.format("Application started at %s", ROOT_RESOURCE));
			log.info(String.format("WADL available at %s", WADL_RESOURCE));

			if (OPEN_IN_BROWSER) {
				URI baseUri = URI.create(ROOT_RESOURCE);
				java.awt.Desktop.getDesktop().browse(baseUri);
			}

			Thread.currentThread().join();

		} catch (Throwable e) {
			log.error("Exception starting server", e);
			e.printStackTrace();
		}
	}

	protected void doStart(ResourceConfig resourceConfig) throws Exception {
		shutdownRemote(SERVER_NAME, SHUTDOWN_PORT);

		URI uri = URI.create(ROOT_RESOURCE);
		httpServer = GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig);

		// Lock the handler, IllegalStateException thrown if we fail.
		lockHandler();
		try {
			if (acceptThread != null) {
				throw new java.lang.IllegalStateException("Socket handler thread is already running.");
			}

			try {
				// Set the accept timeout so we won't block indefinitely.
				socket = new ServerSocket(SHUTDOWN_PORT);
				socket.setSoTimeout(socketAcceptTimeoutMilli);

				String msg = String.format("%s is accepting connections on port %s from %s.", getClass().getSimpleName(), SERVER_PORT, SERVER_NAME);
				log.info(msg);

			} catch (IOException ex) {
				String msg = String.format("IOException starting server socket, maybe port %s was not available.", SERVER_PORT);
				log.error(msg, ex);
			}

			Runnable shutdownRunnable = new Runnable() {
				public void run() {
					httpServer.shutdown();
				}
			};

			Thread shutdownThread = new Thread(shutdownRunnable, "shutdownHook");
			Runtime.getRuntime().addShutdownHook(shutdownThread);

			Runnable acceptRunnable = new Runnable() {
				public void run() {
					socketAcceptLoop();
				}
			};

			acceptThread = new Thread(acceptRunnable);
			acceptThread.start();

		} finally {
			// Be sure to always give up the lock.
			unlockHandler();
		}
	}

	/**
	 * Shuts down *this* currently running Grizzly server.
	 */
	public void shutdownThis() {
		if (httpServer != null) {
			httpServer.shutdown();
		}
	}

	/**
	 * Attempts to shutdown a Grizzly server running on with the specified
	 * hostName and shutdownPort.
	 * 
	 * @param hostName
	 *            the host name this server is running at.
	 * @param shutdownPort
	 *            the shutdown port the server is listening to.
	 * @throws IOException
	 *             upon failure.
	 */
	public static void shutdownRemote(String hostName, int shutdownPort) throws IOException {
		try (Socket localSocket = new Socket(hostName, shutdownPort)) {
			try (OutputStream outStream = localSocket.getOutputStream()) {
				outStream.write("SHUTDOWN".getBytes());
				outStream.flush();
			}
		} catch (ConnectException ignored) {
		}
	}

	private void lockHandler() throws TimeoutException, InterruptedException {
		int timeout = 5;
		TimeUnit timeUnit = TimeUnit.SECONDS;

		if (!handlerLock.tryLock(timeout, timeUnit)) {
			String msg = String.format("Failed to obtain lock within %s %s", timeout, timeUnit);
			throw new TimeoutException(msg);
		}
	}

	/**
	 * Really just used to improve readability and so we limit when we directly
	 * access handlerLock.
	 */
	private void unlockHandler() {
		handlerLock.unlock();
	}

	private void socketAcceptLoop() {

		// Socket accept loop.
		while (!Thread.interrupted()) {
			try {

				// REVIEW - Sleep to allow another thread to lock the handler
				// (never seems to happen without this). Could allow
				// acceptThread to be interrupted in stop without the lock.
				Thread.sleep(5);

				// Lock the handler so we don't accept a new connection while
				// stopping.
				lockHandler();
				Socket client;

				// Ensure we have not stopped or been interrupted.
				if (acceptThread == null || Thread.interrupted()) {
					log.info("Looks like SocketHandler has been stopped, terminate our acceptLoop.");
					System.out.println("Looks like SocketHandler has been stopped, terminate our acceptLoop.");
					return;
				}

				// We have are not stopped, so accept another connection.
				client = socket.accept();

				int val;
				StringBuilder builder = new StringBuilder();
				InputStream is = client.getInputStream();

				while ((val = is.read()) != -1) {
					builder.append((char) val);
					if ("SHUTDOWN".equals(builder.toString())) {
						log.info("Shutdown command received.");
						System.out.println("Shutdown command received.");
						httpServer.shutdownNow();
						System.exit(0);
					}
				}

			} catch (SocketTimeoutException | TimeoutException ex) {
				// Accept timed out, which is excepted, try again.

			} catch (Throwable ex) {
				log.error("Unexpected exception", ex);
				System.out.println("Unexpected exception");
				ex.printStackTrace();
				return;

			} finally {
				unlockHandler();
			}
		}
	}
}
