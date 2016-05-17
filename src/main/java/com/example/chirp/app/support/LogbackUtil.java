package com.example.chirp.app.support;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class LogbackUtil {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(LogbackUtil.class);

  public static void initLogback(ch.qos.logback.classic.Level level) {

    // Reroute java.util.Logger to SLF4J
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    PatternLayoutEncoder ple = new PatternLayoutEncoder();
    String pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n";
    ple.setPattern(pattern);
    ple.setContext(context);
    ple.start();

    ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
    appender.setName("STDOUT");
    appender.setContext(context);
    appender.setEncoder(ple);

    ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
    root.setLevel(level);
    root.addAppender(appender);

    log.info("Using default logback config: {} ({})", level, pattern);
  }
}
