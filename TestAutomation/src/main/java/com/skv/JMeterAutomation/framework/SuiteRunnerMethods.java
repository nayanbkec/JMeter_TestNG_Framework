package com.skv.JMeterAutomation.framework;

import org.testng.annotations.BeforeSuite;

import com.skv.JMeterAutomation.utils.PropertyFileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.apache.jmeter.util.JMeterUtils;
import org.testng.annotations.AfterSuite;

public class SuiteRunnerMethods {
	
	PropertyFileUtils userProperies = new PropertyFileUtils(System.getenv("AUTO_HOME") + "/conf/user.properties");

	Path srcEnv = Paths.get(System.getenv("AUTO_HOME") + "/conf/TestNGAutoProperties/environment.properties");
	Path srcCat = Paths.get(System.getenv("AUTO_HOME") + "/conf/TestNGAutoProperties/categories.json");
	Path destEnv = Paths.get(userProperies.getProperty("Allure_Result_Dir") + "/environment.properties");
	Path destCat = Paths.get(userProperies.getProperty("Allure_Result_Dir") + "/categories.json");
	
	File allureResultsDir = new File(userProperies.getProperty("Allure_Result_Dir"));
	File allureReportDir = new File(userProperies.getProperty("Allure_Report_Dir"));
	File extentReportDir= new File(userProperies.getProperty("Extent_Report_Dir"));
	File autoLogFile = new File(System.getenv("AUTO_HOME") + "/logs/Automation_Execution_Logs.log");
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws Exception {
		TestAutomationLogger.info("JMETER VERSION : " + JMeterUtils.getJMeterVersion());
		try {
			if (allureResultsDir.exists()) {
				FileUtils.deleteDirectory(allureResultsDir);
			}
			if (allureReportDir.exists()) {
				FileUtils.deleteDirectory(allureReportDir);
			}
			if (autoLogFile.exists()) {
				autoLogFile.delete();
			}
			if (extentReportDir.exists()){
				FileUtils.deleteDirectory(extentReportDir);
			}
			extentReportDir.mkdir();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() throws Exception {
		TestAutomationLogger.info("***** Finished The Suite *****");
		Files.copy(srcEnv, destEnv, StandardCopyOption.REPLACE_EXISTING);
		Files.copy(srcCat, destCat, StandardCopyOption.REPLACE_EXISTING);
		String allureCmd= "allure generate "+allureResultsDir+" -o "+allureReportDir;
        Runtime.getRuntime().exec(allureCmd);
		System.out.println("JMeter Test Suite Execution Finished!!!");
	}
}
 