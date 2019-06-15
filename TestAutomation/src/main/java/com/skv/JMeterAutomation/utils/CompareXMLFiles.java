package com.skv.JMeterAutomation.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.ElementNameAndTextQualifier;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

public class CompareXMLFiles {

	static int totalDiff = 0;
	static boolean similar = false;
	static boolean identical = false;

	public List fileInput(String file1, String file2) throws SAXException,
			IOException {
		FileInputStream fis1 = new FileInputStream(file1);
		FileInputStream fis2 = new FileInputStream(file2);
		BufferedReader source = new BufferedReader(new InputStreamReader(fis1));
		BufferedReader target = new BufferedReader(new InputStreamReader(fis2));
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.getIgnoreDiffBetweenTextAndCDATA();
		XMLUnit.setExpandEntityReferences(Boolean.TRUE);
		XMLUnit.getTransformerFactory();
		List differences = compareXML(source, target);
		return differences;
	}

	public static List compareXML(Reader source, Reader target)
			throws SAXException, IOException {
		Diff xmlDiff = new Diff(source, target);
		xmlDiff.overrideElementQualifier(new ElementNameAndTextQualifier());
		similar = xmlDiff.similar();
		identical = xmlDiff.identical();
		DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff);
		return detailXmlDiff.getAllDifferences();
	}

	public boolean isSimilar(String file1, String file2) throws SAXException,
			IOException {
		FileInputStream fis1 = new FileInputStream(file1);
		FileInputStream fis2 = new FileInputStream(file2);
		BufferedReader source = new BufferedReader(new InputStreamReader(fis1));
		BufferedReader target = new BufferedReader(new InputStreamReader(fis2));
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.getIgnoreDiffBetweenTextAndCDATA();
		XMLUnit.setExpandEntityReferences(Boolean.TRUE);
		XMLUnit.getTransformerFactory();
		Diff xmlDiff = new Diff(source, target);
		xmlDiff.overrideElementQualifier(new ElementNameAndTextQualifier());
		return xmlDiff.similar();

	}

	public boolean isIdentical(String file1, String file2) throws SAXException,
			IOException {
		FileInputStream fis1 = new FileInputStream(file1);
		FileInputStream fis2 = new FileInputStream(file2);
		BufferedReader source = new BufferedReader(new InputStreamReader(fis1));
		BufferedReader target = new BufferedReader(new InputStreamReader(fis2));
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreComments(true);
		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.getIgnoreDiffBetweenTextAndCDATA();
		XMLUnit.setExpandEntityReferences(Boolean.TRUE);
		XMLUnit.getTransformerFactory();
		Diff xmlDiff = new Diff(source, target);
		xmlDiff.overrideElementQualifier(new ElementNameAndTextQualifier());
		return xmlDiff.identical();

	}
}
