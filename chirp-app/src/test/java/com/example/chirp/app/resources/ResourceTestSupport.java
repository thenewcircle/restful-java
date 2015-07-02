package com.example.chirp.app.resources;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.LogbackUtil;
import com.example.chirp.kernel.stores.UsersStore;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

import ch.qos.logback.classic.Level;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class ResourceTestSupport extends JerseyTest {

	private ServletContainer servletContainer;
	private Application chirpApplication;

	public ResourceTestSupport() {
		LogbackUtil.initLogback(Level.WARN);
	}
	
  @Override
  protected TestContainerFactory getTestContainerFactory() {
    return new GrizzlyWebTestContainerFactory();
  }

  @Override
  protected DeploymentContext configureDeployment(){
	  
    chirpApplication = new ChirpApplication();
    ResourceConfig resourceConfig = ResourceConfig.forApplication(chirpApplication);

    servletContainer = new ServletContainer(resourceConfig);

    return ServletDeploymentContext
        .forServlet(servletContainer)
        .addListener(ContextLoaderListener.class)
        .contextParam("contextConfigLocation", "classpath:/chirp-test-spring.xml")
        .build();
  }

  @Override
	protected void configureClient(ClientConfig config) {
		config.register(JacksonXMLProvider.class);
		super.configureClient(config);
	}

  public BeanFactory getBeanFactory() {
    ServletContext servletContext = servletContainer.getServletContext();
    return WebApplicationContextUtils.getWebApplicationContext(servletContext);
  }

  public UsersStore getUserStore() {
    return getBeanFactory().getBean(UsersStore.class);
  }
}
