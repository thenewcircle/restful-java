package com.example.chirp.app.support;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ch.qos.logback.classic.Level;

public class ChirpLogbackInitializer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LogbackUtil.initLogback(Level.WARN);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
