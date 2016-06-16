package com.example.chirp.app.springdata;

import javax.servlet.ServletContextEvent;

public class MySpringContextLoaderListener extends org.jboss.resteasy.plugins.spring.SpringContextLoaderListener {

  public void contextInitialized(ServletContextEvent event) {
    try {
      super.contextInitialized(event);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }  
}
