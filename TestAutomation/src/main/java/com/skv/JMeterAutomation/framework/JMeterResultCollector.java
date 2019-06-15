package com.skv.JMeterAutomation.framework;

import java.util.Arrays;

import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;

public class JMeterResultCollector extends ResultCollector {

	private static final long serialVersionUID = 1L;
	private AssertionResult[] assertionResults;
	private boolean failure = false;
	private static String samplerName = null;
	private static String samplerData = null;
	private static String responseCode = null;
	private static String responseMessage = null;
	private static String responseData = null;
	private static String assertionFailureMessage = null;

	public JMeterResultCollector(Summariser summer) {
		super(summer);
	}
	
	public static void setSamplerName(String samplerName) {
		JMeterResultCollector.samplerName = samplerName;
	}

	public static void setSamplerData(String samplerData) {
		JMeterResultCollector.samplerData = samplerData;
	}

	public static void setResponseCode(String responseCode) {
		JMeterResultCollector.responseCode = responseCode;
	}

	public static void setResponseMessage(String responseMessage) {
		JMeterResultCollector.responseMessage = responseMessage;
	}

	public static void setResponseData(String responseData) {
		JMeterResultCollector.responseData = responseData;
	}

	public static void setAssertionFailureMessage(String assertionFailureMessage) {
		JMeterResultCollector.assertionFailureMessage = assertionFailureMessage;
	}

	public static String getSamplerName() {
		return samplerName;
	}
	
	public static String getSamplerData() {
		return samplerData;
	}

	public static String getResponseCode() {
		return responseCode;
	}

	public static String getResponseMessage() {
		return responseMessage;
	}

	public static String getResponseData() {
		return responseData;
	}

	public static String getAssertionFailureMessage() {
		return assertionFailureMessage;
	}

	public boolean isFailure() {
		return failure;
	}

	public AssertionResult[] getAssertArray() {
		return assertionResults;
	}

	@Override
	public void sampleStarted(SampleEvent e) {
		super.sampleStarted(e);
		SampleResult r = e.getResult();
		TestAutomationLogger.info("JMeter Sampler : " + r.getSampleLabel() + "is Started");
	}

	@Override
	public void sampleOccurred(SampleEvent e) {
		super.sampleOccurred(e);
		SampleResult r = e.getResult();
		if (r.isSuccessful()) {
			TestAutomationLogger.info("***** ***** ***** JMETER SAMPLER PASSED ***** ***** *****");
			TestAutomationLogger.info("JMeter Sampler Name : " + r.getSampleLabel());
			TestAutomationLogger.info("JMeter Sampler Response Code : " + r.getResponseCode());
			TestAutomationLogger.info("JMeter Sampler Response Message : " + r.getResponseMessage());
			TestAutomationLogger.info("JMeter Sampler Data : " + "\n" + r.getSamplerData());
			TestAutomationLogger.info("JMeter Sampler Response Data : " + "\n" + r.getResponseDataAsString());
		}
		
		else if (!r.isSuccessful()) {
			failure = true;
			TestAutomationLogger.error("***** ***** ***** JMETER SAMPLER FAILED ***** ***** *****");
			if (samplerName == null) {
				samplerName = r.getSampleLabel();
				samplerData = r.getSamplerData();
				responseCode = r.getResponseCode();
				responseMessage = r.getResponseMessage();
				responseData = r.getResponseDataAsString();
				assertionFailureMessage = r.getFirstAssertionFailureMessage();
			}
			TestAutomationLogger.error("JMeter Sampler Name : " + samplerName);
			TestAutomationLogger.error("JMeter Sampler Data : " + "\n" + samplerData);
			TestAutomationLogger.error("JMeter Sampler Response Code : " + responseCode);
			TestAutomationLogger.error("JMeter Sampler Response Message : " + responseMessage);
			TestAutomationLogger.error("JMeter Sampler Response Data : " + "\n" + responseData);
			TestAutomationLogger.error("JMeter Sampler First Assertion Failure : " + "\n" + assertionFailureMessage);
			TestAutomationLogger.error("JMeter All Assertion Failures : " + "\n" + Arrays.toString(r.getAssertionResults()));
			assertionResults = r.getAssertionResults();
		}

		else {
			TestAutomationLogger.info("***** ***** ***** JMETER SAMPLER SKIPPED ***** ***** *****");
		}
	}

	@Override
	public void sampleStopped(SampleEvent e) {
		super.sampleStopped(e);
		SampleResult r = e.getResult();
		TestAutomationLogger.info("JMeter Sampler : " + r.getSampleLabel() + "is Stopped");
	}
}
