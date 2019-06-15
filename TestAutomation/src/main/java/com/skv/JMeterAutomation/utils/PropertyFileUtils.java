package com.skv.JMeterAutomation.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.skv.JMeterAutomation.framework.TestAutomationLogger;

public class PropertyFileUtils {
	
	String propFile;
	
	public PropertyFileUtils(String propFile) {
		super();
		this.propFile = propFile;
	}

	public String getPropFile() {
		return propFile;
	}

	public String getProperty(String propName) {
		Properties prop = new Properties();
		InputStream input = null;
		String propValue = null;
		try {
			input = new FileInputStream(propFile);
			prop.load(input);
			propValue = prop.getProperty(propName, "Property File " + propFile +" Not Found");
		} catch (IOException ex) {
			TestAutomationLogger.error("Property File " + propFile +" Not Found");
			ex.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return propValue;
	}

	public void setProperty(String propName, String propValue) {
		InputStream input = null;
        OutputStream output = null;
        Properties prop = new Properties();  
        try {
        	input = new FileInputStream(propFile);
        	prop.load(input);
        	input.close();	
            output = new FileOutputStream(propFile);
            prop.setProperty(propName, propValue);
            prop.store(output, propFile);  
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }    
        }      
	}
}