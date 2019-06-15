package com.skv.JMeterAutomation.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import net.javacrumbs.jsonunit.core.Configuration;
import net.javacrumbs.jsonunit.core.internal.Diff;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class CompareJSONFiles {

	private static final String FULL_JSON = "fullJson";
	private static Configuration configuration = Configuration.empty();

	public String getDifferences(String source, String target) throws SAXException,IOException {
		Diff diff = Diff.create(source, target, FULL_JSON, "", configuration);
		return diff.differences();
	}

	public boolean isSimilar(String source, String target) throws SAXException,IOException, ParseException {
		Diff diff = Diff.create(source, target, FULL_JSON, "", configuration);
		return diff.similar();
	}


	public boolean isIdentical(String source, String target) throws SAXException {
		if(source.equals(target)){
			return true;
		}
		return false;
	}

	public String readJSONFile(String filePath) throws SAXException,IOException {

		String jsonData = "";
		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				//jsonData += line + "\n";
				jsonData += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return jsonData;

	}

}
