package com.skv.JMeterAutomation.framework;

import org.testng.annotations.BeforeClass;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.testng.annotations.AfterClass;

public class BaseClass {

	@SuppressWarnings("deprecation")
	@BeforeClass(alwaysRun=true)
	public void beforeClass() throws Exception {
		JMeterUtils.setJMeterHome(System.getenv("JMETER_HOME"));
		JMeterUtils.loadJMeterProperties(System.getenv("JMETER_HOME") + "/bin/jmeter.properties");
		JMeterUtils.loadProperties(System.getenv("JMETER_HOME") + "/bin/user.properties");
		JMeterUtils.getProperties(System.getenv("JMETER_HOME") + "/bin/user.properties");
		JMeterUtils.getJMeterProperties();
		JMeterUtils.initLocale();
		JMeterUtils.initLogging();
		SaveService.loadProperties();
		//TestAutomationLogger.info("JMeter User Properties : " + JMeterUtils.getJMeterProperties());
	}

	@AfterClass(alwaysRun=true)
	public void afterClass() {
		JMeterUtils.refreshUI();
	}
}
