package com.skv.JMeterAutomation.tests.DemoScripts;

import java.io.File;
import java.io.IOException;

import org.apache.jmeter.engine.JMeterEngineException;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.skv.JMeterAutomation.framework.BaseClass;
import com.skv.JMeterAutomation.framework.JMeterResultCollector;
import com.skv.JMeterAutomation.framework.TestAutomationLogger;

import org.apache.jorphan.collections.HashTree;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class JmxFilesTest2 extends BaseClass {
	
	StandardJMeterEngine jmeter = new StandardJMeterEngine();
	Summariser summer = null;
	JMeterResultCollector results;
	
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod() {
	}
	@AfterMethod(alwaysRun=true)
	public void afterMethod() {
		jmeter.exit();
		summer.clear();
		results.clearData();
		results.flushFile();
	}

	@Test(description = "Demo_TC_002", groups = {"DemoTest", "JmxFilesTest2"})
	@Description("Checking the Log File Errors")
	@Epic("RSMP v1.2")
	@Feature("Cloud-Forwarder")
	@Story("User Story: CF - Inventory Processing")
	@Severity(SeverityLevel.NORMAL)
	public void Demo_TC_002() throws JMeterEngineException, IOException, Exception {
		File JmxFile2 = new File(System.getenv("AUTO_HOME") + "/scripts/MyTestJmxFiles/CreateFolder_2.jmx");

			HashTree testPlanTree = SaveService.loadTree(JmxFile2);
			jmeter.configure(testPlanTree);
			
			String summariserName = JMeterUtils.getPropDefault("summariser.name", "TestSummary");
			if (summariserName.length() > 0) {
				summer = new Summariser(summariserName);
			}
			results = new JMeterResultCollector(summer);
			//String logFile = System.getenv("AUTO_HOME") + "/logs/JMeterSummaryReport.jtl";
			//results.setFilename(logFile);
			testPlanTree.add(testPlanTree.getArray()[0], results);
			
			jmeter.runTest();
			
			while (jmeter.isActive())
			{
				System.out.println("StandardJMeterEngine is Active...");
				Thread.sleep(3000);
			}
			
			if (results.isFailure())
			{
				TestAutomationLogger.error("TEST FAILED");
				Assert.fail("Response Code: " + JMeterResultCollector.getResponseCode() + "\n" + "Response Message: " + JMeterResultCollector.getResponseMessage() + "\n" + "Response Data: " + JMeterResultCollector.getResponseData());
			}
	}
}
