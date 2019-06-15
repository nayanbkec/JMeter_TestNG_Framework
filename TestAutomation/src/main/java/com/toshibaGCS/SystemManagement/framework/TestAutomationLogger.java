package com.toshibaGCS.SystemManagement.framework;

import org.apache.log4j.Logger;

public class TestAutomationLogger {

	// Initialize Log4j instance
	private static Logger TestLogger = Logger.getLogger(TestAutomationLogger.class.getName());

	// Info Level Logs
	public static void info(String message) {
		TestLogger.info(message);
	}

	// Warn Level Logs
	public static void warn(String message) {
		TestLogger.warn(message);
	}

	// Error Level Logs
	public static void error(String message) {
		TestLogger.error(message);
	}

	// Fatal Level Logs
	public static void fatal(String message) {
		TestLogger.fatal(message);
	}

	// Debug Level Logs
	public static void debug(String message) {
		TestLogger.debug(message);
	}

	// Trace Level Logs
	public static void trace(String message) {
		TestLogger.trace(message);
	}
}
