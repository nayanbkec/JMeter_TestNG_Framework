package com.toshibaGCS.SystemManagement.framework;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentReportsListener implements ITestListener {
	private static ExtentReports extent = ExtentReportsManager.createInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	public void onStart(ITestContext iTestContext) {
		TestAutomationLogger.info("Extent Reports Logging Started!!!");
	}

	public void onFinish(ITestContext iTestContext) {
		extent.flush();
	}

	public void onTestStart(ITestResult iTestResult) {
		ExtentTest extentTest = extent.createTest(iTestResult.getMethod().getMethodName(),
				iTestResult.getMethod().getDescription());
		test.set(extentTest);
	}

	public void onTestSuccess(ITestResult iTestResult) {
		test.get().pass("PASSED Test Case ID: " + iTestResult.getName());
	}

	public void onTestFailure(ITestResult iTestResult) {
		test.get().fail("FAILED Test Case ID: " + iTestResult.getName());
		test.get().info("JMeter Sampler: " + JMeterResultCollector.getSamplerName().replaceFirst("Save Test plan before calling __TestPlanName function", ""));
		test.get().info("Sampler Data: " + JMeterResultCollector.getSamplerData());
		test.get().info("Response Code: " + JMeterResultCollector.getResponseCode());
		test.get().info("Response Message: " + JMeterResultCollector.getResponseMessage());
		test.get().info("Response Data: " + JMeterResultCollector.getResponseData());		
		if(JMeterResultCollector.getAssertionFailureMessage() != null) {
			test.get().info("Assertion Failure Message: " + JMeterResultCollector.getAssertionFailureMessage());
			}
		else {
			test.get().info("Assertion Failure Message: Assertion is Not Defined for the Failed Sampler");
		}		
		
		// Enable the below line to add the TestNG Failure Message to Extent Report. For JMeter Scripts, this is not required.
		// test.get().info(iTestResult.getThrowable());
		
		// Enable the below line if we wanted to attach the screenshots of failure to Extent Report. For JMeter Scripts, this is not required.
		// test.get().addScreenCaptureFromPath("D:\\Workspace\\MyStudy_Space\\JMeterTestAutomation\\test-output\\ScreenShots\\FailedTest.png");
		
		JMeterResultCollector.setSamplerName(null);
		JMeterResultCollector.setSamplerData(null);
		JMeterResultCollector.setResponseCode(null);
		JMeterResultCollector.setResponseMessage(null);
		JMeterResultCollector.setResponseData(null);
		JMeterResultCollector.setAssertionFailureMessage(null);
	}

	public void onTestSkipped(ITestResult iTestResult) {
		test.get().skip("SKIPPED Test Case ID: " + iTestResult.getName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
	}
}
