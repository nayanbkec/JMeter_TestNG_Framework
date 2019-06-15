package com.skv.JMeterAutomation.framework;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestSuiteListener implements ISuiteListener {

	public void onStart(ISuite suite) {
		TestAutomationLogger.info("TEST SUITE " + suite.getName() + " STARTED");
	}

	public void onFinish(ISuite suite) {
		TestAutomationLogger.info("TEST SUITE " + suite.getName() + " FINISHED");
	}
}
