package com.example.chirp.app.resources;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ch.qos.logback.classic.Level;

import com.example.chirp.app.ChirpApplication;
import com.example.chirp.app.TestSpringConfig;
import com.example.chirp.app.support.LogbackUtil;
import com.fasterxml.jackson.jaxrs.xml.JacksonXMLProvider;

public abstract class ResourceTestSupport extends JerseyTest {

	private AutowireCapableBeanFactory beanFactory;
	
	@Before
  public void setUp() throws Exception {
		super.setUp();
		beanFactory.autowireBean(this);
	}
	
	@Override
	protected Application configure() {
		LogbackUtil.initLogback(Level.WARN);
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("test");
		context.register(TestSpringConfig.class);
		context.refresh();
		
		beanFactory = context.getBeanFactory();
		
		ChirpApplication application = context.getBean(ChirpApplication.class);
		
		ResourceConfig config = ResourceConfig.forApplication(application);
		config.register(SpringLifecycleListener.class);
    config.register(RequestContextFilter.class);
    config.property("contextConfig", context);
    config.packages("com.example.chirp.app");

		return config;
	}	

	@Override
	protected void configureClient(ClientConfig config) {
		config.register(JacksonXMLProvider.class);
		super.configureClient(config);
	}
}
