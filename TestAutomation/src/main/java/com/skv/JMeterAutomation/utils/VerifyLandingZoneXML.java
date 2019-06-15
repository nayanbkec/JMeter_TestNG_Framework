package com.skv.JMeterAutomation.utils;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

public class VerifyLandingZoneXML {

	public boolean verifyChildXMLs(File folderPath, File masterXML) {
		boolean result = true;
		try {
			DocumentBuilderFactory mfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder mbuilder = mfactory.newDocumentBuilder();
			Document mDocument = mbuilder.parse(masterXML);
			String master = new String(convertDocumentToString(mDocument));
			String[] extn = { "xml" };
			Collection<File> files = FileUtils.listFiles(folderPath, extn, true);
			File[] fArray = files.toArray(new File[files.size()]);

			for (File xml : fArray) {
				String child = null;
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(xml);
				child = new String(convertDocumentToString(document));
				result = master.contains(child);
				if (result == false) {
					return result;
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		String output = null;
		try {
			transformer = tf.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return output;
	}

	public boolean verifyXmlCount(File folderPath, File masterXML) {
		boolean result = true;
		try {
			DocumentBuilderFactory mfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder mbuilder;
			mbuilder = mfactory.newDocumentBuilder();
			Document mDocument = mbuilder.parse(masterXML);
			String[] extn = { "xml" };
			Collection<File> files = FileUtils.listFiles(folderPath, extn, true);
			int inventoryCount = mDocument.getElementsByTagName("system").getLength();
			int eventCount = mDocument.getElementsByTagName("event").getLength();
			
			if (inventoryCount != 0)
			{
				if (files.size() == inventoryCount) {
					result = true;
				} else {
					result = false;
				}
			}
			else if (eventCount != 0)
			{
				if (files.size() == eventCount) {
					result = true;
				} else {
					result = false;
				}
			}
			else {
			result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getRetailerID(File masterXML) throws Exception {
		DocumentBuilderFactory mfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder mbuilder = mfactory.newDocumentBuilder();
		Document mDocument = mbuilder.parse(masterXML);
		return mDocument.getElementsByTagName("retailer_id").item(0).getTextContent();
	}

	public String[] listUnmatchedXML(File folderPath, File masterXML) {
		ArrayList<String> unmatchedXML = new ArrayList<String>();
		try {
			DocumentBuilderFactory mfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder mbuilder = mfactory.newDocumentBuilder();
			Document mDocument = mbuilder.parse(masterXML);
			String master = new String(convertDocumentToString(mDocument));

			String[] extn = { "xml" };
			Collection<File> files = FileUtils.listFiles(folderPath, extn, true);
			File[] fArray = files.toArray(new File[files.size()]);

			for (File xml : fArray) {
				String child = null;
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(xml);
				child = new String(convertDocumentToString(document));

				if (master.contains(child) == false) {
					unmatchedXML.add(xml.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unmatchedXML.toArray(new String[unmatchedXML.size()]);
	}
}
