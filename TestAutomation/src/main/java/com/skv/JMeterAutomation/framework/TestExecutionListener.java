package com.skv.JMeterAutomation.framework;

import java.util.List;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestExecutionListener extends TestListenerAdapter {

	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		TestAutomationLogger.info("Started Test Instance : " + result.getInstanceName());
		TestAutomationLogger.info("Started Test Method : " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult tr) {

		super.onTestSuccess(tr);
		TestAutomationLogger.info("JMeter Test Case: " + tr.getInstanceName() + " is " + tr.getStatus());
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		TestAutomationLogger.error("JMeter Test Case: " + tr.getInstanceName() + " is Failed");
		// Write Code for taking Screenshots on Test Failure
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		TestAutomationLogger.warn("JMeter Test Case: " + tr.getInstanceName() + " is Skipped");
	}

	@Override
	public void onStart(ITestContext testContext) {
		super.onStart(testContext);
		TestAutomationLogger.info("JMeter Test Start Time: " + testContext.getStartDate());
		TestAutomationLogger.info("JMeter Suite Name is: " + testContext.getSuite().getName());
		TestAutomationLogger.info("JMeter Test Name is: " + testContext.getName());
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
		TestAutomationLogger.info("PASSED Tests Count = " + testContext.getPassedTests().size());
		TestAutomationLogger.info("FAILED Tests Count = " + testContext.getFailedTests().size());
		TestAutomationLogger.info("SKIPPED Tests Count = " + testContext.getSkippedTests().size());
		TestAutomationLogger.info("Included Groups: " + testContext.getIncludedGroups());
		TestAutomationLogger.info("Excluded Groups: " + testContext.getExcludedGroups());
		
		TestAutomationLogger.info("PASSED TEST CASES");
		Set<ITestResult> p = testContext.getPassedTests().getAllResults();
		int i = 0;
		for (ITestResult r : p) {
			i++;
			TestAutomationLogger.info(i + ": " + r.getInstanceName());
		}

		TestAutomationLogger.info("FAILED TEST CASES");
		Set<ITestResult> f = testContext.getFailedTests().getAllResults();
		int j = 0;
		for (ITestResult r : f) {
			j++;
			TestAutomationLogger.info(j + ": " + r.getInstanceName());
		}

		TestAutomationLogger.info("SKIPPED TEST CASES");
		Set<ITestResult> s = testContext.getSkippedTests().getAllResults();
		int k = 0;
		for (ITestResult r : s) {
			k++;
			TestAutomationLogger.info(k + ": " + r.getInstanceName());
		}	
		TestAutomationLogger.info("JMeter Test Finish Time: " + testContext.getEndDate());
	}

	@Override
	public List<ITestResult> getPassedTests() {

		return super.getPassedTests();
	}

	@Override
	public List<ITestResult> getFailedTests() {

		return super.getFailedTests();
	}

	@Override
	public List<ITestResult> getSkippedTests() {

		return super.getSkippedTests();
	}

	@Override
	public void beforeConfiguration(ITestResult tr) {

		super.beforeConfiguration(tr);
	}

	@Override
	public void onConfigurationSuccess(ITestResult itr) {

		super.onConfigurationSuccess(itr);
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {

		super.onConfigurationFailure(itr);
	}

	@Override
	public void onConfigurationSkip(ITestResult itr) {

		super.onConfigurationSkip(itr);
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestFailedButWithinSuccessPercentage(tr);
	}

	@Override
	protected ITestNGMethod[] getAllTestMethods() {
		// TODO Auto-generated method stub
		return super.getAllTestMethods();
	}

	@Override
	public List<ITestResult> getFailedButWithinSuccessPercentageTests() {
		// TODO Auto-generated method stub
		return super.getFailedButWithinSuccessPercentageTests();
	}

	@Override
	public void setAllTestMethods(List<ITestNGMethod> allTestMethods) {
		// TODO Auto-generated method stub
		super.setAllTestMethods(allTestMethods);
	}

	@Override
	public void setFailedButWithinSuccessPercentageTests(List<ITestResult> failedButWithinSuccessPercentageTests) {
		// TODO Auto-generated method stub
		super.setFailedButWithinSuccessPercentageTests(failedButWithinSuccessPercentageTests);
	}

	@Override
	public void setFailedTests(List<ITestResult> failedTests) {
		// TODO Auto-generated method stub
		super.setFailedTests(failedTests);
	}

	@Override
	public void setPassedTests(List<ITestResult> passedTests) {
		// TODO Auto-generated method stub
		super.setPassedTests(passedTests);
	}

	@Override
	public void setSkippedTests(List<ITestResult> skippedTests) {
		// TODO Auto-generated method stub
		super.setSkippedTests(skippedTests);
	}

	@Override
	public List<ITestContext> getTestContexts() {
		// TODO Auto-generated method stub
		return super.getTestContexts();
	}

	@Override
	public List<ITestResult> getConfigurationFailures() {
		// TODO Auto-generated method stub
		return super.getConfigurationFailures();
	}

	@Override
	public List<ITestResult> getConfigurationSkips() {
		// TODO Auto-generated method stub
		return super.getConfigurationSkips();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
