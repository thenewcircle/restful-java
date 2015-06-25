package com.example.chirp.dist.jersey;

import ch.qos.logback.classic.Level;
import com.example.chirp.app.LogbackUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ChirpLogbackInitializer implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LogbackUtil.initLogback(Level.WARN);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
  }
}
