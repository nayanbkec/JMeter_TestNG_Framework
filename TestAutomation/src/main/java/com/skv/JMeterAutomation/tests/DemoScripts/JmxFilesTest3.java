package com.skv.JMeterAutomation.tests.DemoScripts;

import java.io.File;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.skv.JMeterAutomation.framework.BaseClass;
import com.skv.JMeterAutomation.framework.JMeterResultCollector;
import com.skv.JMeterAutomation.framework.TestAutomationLogger;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class JmxFilesTest3 extends BaseClass {

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

	@Test(description = "Demo_TC_003", groups = {"DemoTest", "JmxFilesTest3"})
	@Description("Executing Welcome Function Test")
	@Epic("RSMP v1.0")
	@Feature("Landing-Zone")
	@Story("User Story: LZ Events")
	@Severity(SeverityLevel.CRITICAL)
	public void Demo_TC_003() throws Exception {
		File JmxFile3 = new File(System.getenv("AUTO_HOME") + "/scripts/MyTestJmxFiles/CreateFolder_3.jmx");
		HashTree testPlanTree = SaveService.loadTree(JmxFile3);
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
