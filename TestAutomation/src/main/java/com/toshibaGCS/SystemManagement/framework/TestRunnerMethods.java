package com.toshibaGCS.SystemManagement.framework;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class TestRunnerMethods {

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		TestAutomationLogger.info("***** Calling the Script for checking Test Environment *****");
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		TestAutomationLogger.info("***** Finished The Test *****");
	}

}
