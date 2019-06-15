package com.toshibaGCS.SystemManagement.framework;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;

public class BeforeAfterGroups extends BaseClass {
	@BeforeGroups(groups = "Landing-Zone")
	public void lzSetUp() {
		TestAutomationLogger.info(">>>>> Started Landing-Zone Test <<<<<");
	}

	@AfterGroups(groups = "Landing-Zone")
	public void lzTearDown() {
		TestAutomationLogger.info(">>>>> Finished Landing-Zone Test <<<<<");
	}

	@BeforeGroups(groups = "Cloud-Forwarder")
	public void cfSetUp() {
		TestAutomationLogger.info(">>>>> Started Cloud-Forwarder Test <<<<<");
	}

	@AfterGroups(groups = "Cloud-Forwarder")
	public void cfTearDown() {
		TestAutomationLogger.info(">>>>> Finished Cloud-Forwarder Test <<<<<");
	}

	@BeforeGroups(groups = "DemoTest")
	public void remsSetUp() {
		TestAutomationLogger.info(">>>>> Started Demo Test <<<<<");
	}

	@AfterGroups(groups = "DemoTest")
	public void remsTearDown() {
		TestAutomationLogger.info(">>>>> Finished Demo Test <<<<<");
	}
}
