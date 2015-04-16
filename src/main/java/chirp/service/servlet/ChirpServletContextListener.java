package chirp.service.servlet;

import chirp.service.ServerUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ChirpServletContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
		/* Preload data into the database. */
    ServerUtils.resetAndSeedRepository();
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
