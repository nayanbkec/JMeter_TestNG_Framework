package com.skv.JMeterAutomation.framework;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.skv.JMeterAutomation.utils.PropertyFileUtils;

public class ExtentReportsManager {
	
	private static PropertyFileUtils userProperies = new PropertyFileUtils(System.getenv("AUTO_HOME") + "/conf/user.properties");
	private static PropertyFileUtils environmentProperies = new PropertyFileUtils(System.getenv("AUTO_HOME") + "/conf/TestNGAutoProperties/environment.properties");
	private static ExtentReports extent;
	private static String extentReportFile = userProperies.getProperty("Extent_Report_Dir") + "/Automation_Test_Results.html";
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");  
    static Date date = new Date();

	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	}

	public static ExtentReports createInstance() {
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(new File(extentReportFile));
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Automation Test Results");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Systems Management - Automation Test Results");

		extent = new ExtentReports();
		extent.setSystemInfo("Application", environmentProperies.getProperty("Application"));
		extent.setSystemInfo("Execution Platform", environmentProperies.getProperty("Execution_Platform"));
		extent.setSystemInfo("Build No:", environmentProperies.getProperty("Build_No"));
		extent.setSystemInfo("Execution Date", dateFormat.format(date));
		extent.setSystemInfo("Tester Name", environmentProperies.getProperty("Tester_Name"));
		extent.attachReporter(htmlReporter);
		environmentProperies.setProperty("Execution_Date", dateFormat.format(date));
		return extent;
	}
}
