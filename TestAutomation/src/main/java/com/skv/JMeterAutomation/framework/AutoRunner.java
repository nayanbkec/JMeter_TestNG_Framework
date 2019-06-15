package com.skv.JMeterAutomation.framework;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;

import com.skv.JMeterAutomation.utils.PropertyFileUtils;

public class AutoRunner {

	public static void main(String[] args) {
		TestAutomationLogger.info("Started JMeter Test Automation Suite...");
		PropertyFileUtils userProperies = new PropertyFileUtils(System.getenv("AUTO_HOME") + "/conf/user.properties");
		TestNG tng = new TestNG();
		TestListenerAdapter tla = new TestListenerAdapter();
		List<String> suites = Lists.newArrayList();
		suites.add(System.getenv("AUTO_HOME") + "/conf/TestNGAutoProperties/testng.xml");
		tng.setTestSuites(suites);
		tng.setOutputDirectory(userProperies.getProperty("TestNG_Output_Dir"));
		tng.addListener(tla);
		tng.run();
		TestAutomationLogger.info("Finished JMeterTest Automation Suite...");
		TestAutomationLogger.info("***** TEST RESULTS *****");
		TestAutomationLogger.info("PASSED Tests Count = " + tla.getPassedTests().size());
		TestAutomationLogger.info("FAILED Tests Count = " + tla.getFailedTests().size());
		TestAutomationLogger.info("SKIPPED Tests Count = " + tla.getSkippedTests().size());
	}
}
