package com.skv.JMeterAutomation.utils;
//package jMeterTools;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;



public class XMLCompare<string> {

static String connectionStatus1 = null; 
static String discoveryAttributes1 = null; 
static String encryptedAlias1 = null;
static String eventFilterFlag1 = null;
static String evtFilterFlags1 = null;
static String hostName1 = null;
static String ipAddress1 = null;
static String portNum1 = null;
static String storeId1 = null;
static String storeName1 = null;
static String storeRegisterId1 = null;

static String connectionStatus2 = null; 
static String discoveryAttributes2 = null; 
static String encryptedAlias2 = null;
static String eventFilterFlag2 = null;
static String evtFilterFlags2 = null;
static String hostName2 = null;
static String ipAddress2 = null;
static String portNum2 = null;
static String storeId2 = null;
static String storeName2 = null;
static String storeRegisterId2 = null;
public void XMLComapare(String File1,String File2) {

  try { 
	  File inputFile1 = new File(File1);
	  File inputFile2 = new File(File1);
     //File inputFile1 = new File("C:/apache-jmeter-3.3/tests/data/xmlfiles/Reference/source1.xml");
     //File inputFile2 = new File("C:/apache-jmeter-3.3/tests/data/xmlfiles/target1.xml");
     DocumentBuilderFactory dbFactory 
        = DocumentBuilderFactory.newInstance();
     DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
     Document doc1 = dBuilder.parse(inputFile1);
     Document doc2 = dBuilder.parse(inputFile2);
     doc1.getDocumentElement().normalize();
     doc2.getDocumentElement().normalize();
     System.out.println("Root element :" 
        + doc1.getDocumentElement().getNodeName());
     NodeList nList1 = doc1.getElementsByTagName("store");
     System.out.println("----------------------------");
     for (int temp = 0; temp < nList1.getLength(); temp++) {
        Node nNode1 = nList1.item(temp);
        System.out.println("\nCurrent Element :" 
           + nNode1.getNodeName());
        if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
           Element eElement = (Element) nNode1;
           connectionStatus1 = eElement.getElementsByTagName("connectionStatus").item(0).getTextContent();
           discoveryAttributes1 = eElement.getElementsByTagName("discoveryAttributes").item(0).getTextContent();
           encryptedAlias1 = eElement.getElementsByTagName("encryptedAlias").item(0).getTextContent();
           eventFilterFlag1 = eElement.getElementsByTagName("eventFilterFlag").item(0).getTextContent();
           evtFilterFlags1 = eElement.getElementsByTagName("evtFilterFlags").item(0).getTextContent();
           hostName1 = eElement.getElementsByTagName("hostName").item(0).getTextContent();
           ipAddress1 = eElement.getElementsByTagName("ipAddress").item(0).getTextContent();
           portNum1 = eElement.getElementsByTagName("portNum").item(0).getTextContent();
           storeId1 = eElement.getElementsByTagName("storeId").item(0).getTextContent();
           storeName1 = eElement.getElementsByTagName("storeName").item(0).getTextContent();
           storeRegisterId1 = eElement.getElementsByTagName("storeRegisterId").item(0).getTextContent();
                    
           }
     }
	// System.out.println("Root element :" 
    //    + doc2.getDocumentElement().getNodeName());
     NodeList nList2 = doc2.getElementsByTagName("store");
    // System.out.println("----------------------------");
     for (int temp = 0; temp < nList2.getLength(); temp++) {
        Node nNode2 = nList2.item(temp);
      // System.out.println("\nCurrent Element :" 
       //    + nNode2.getNodeName());
        if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
           Element eElement2 = (Element) nNode2;
           connectionStatus2 = eElement2.getElementsByTagName("connectionStatus").item(0).getTextContent();
           discoveryAttributes2 = eElement2.getElementsByTagName("discoveryAttributes").item(0).getTextContent();
           encryptedAlias2 = eElement2.getElementsByTagName("encryptedAlias").item(0).getTextContent();
           eventFilterFlag2 = eElement2.getElementsByTagName("eventFilterFlag").item(0).getTextContent();
           evtFilterFlags2 = eElement2.getElementsByTagName("evtFilterFlags").item(0).getTextContent();
           hostName2 = eElement2.getElementsByTagName("hostName").item(0).getTextContent();
           ipAddress2 = eElement2.getElementsByTagName("ipAddress").item(0).getTextContent();
           portNum2 = eElement2.getElementsByTagName("portNum").item(0).getTextContent();
           storeId2 = eElement2.getElementsByTagName("storeId").item(0).getTextContent();
           storeName2 = eElement2.getElementsByTagName("storeName").item(0).getTextContent();
           storeRegisterId2 = eElement2.getElementsByTagName("storeRegisterId").item(0).getTextContent();
           }
     }
     
     System.out.println();
     System.out.println("-------------TEST RESULTS----------------");
     System.out.println();
     
     if(compareStrings(connectionStatus1,connectionStatus2)){
      	 System.out.println("connectionStatus Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-connectionStatus Value doesn't matched in both XML's");
       }
     
     if(compareStrings(discoveryAttributes1,discoveryAttributes2)){
      	 System.out.println("discoveryAttributes Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-discoveryAttributes Value doesn't matched in both XML's");
       }
     
     if(compareStrings(encryptedAlias1,encryptedAlias2)){
      	 System.out.println("encryptedAlias Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-encryptedAlias Value doesn't matched in both XML's");
       }
     
     if(compareStrings(eventFilterFlag1,eventFilterFlag2)){
      	 System.out.println("eventFilterFlag Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-eventFilterFlag Value doesn't matched in both XML's");
       }
     
     if(compareStrings(evtFilterFlags1,evtFilterFlags2)){
      	 System.out.println("evtFilterFlags Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-evtFilterFlags Value doesn't matched in both XML's");
       }
     
     if(compareStrings(hostName1,hostName2)){
      	 System.out.println("hostName Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-hostName Value doesn't matched in both XML's");
       }
     
     if(compareStrings(ipAddress1,ipAddress2)){
      	 System.out.println("ipAddress Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-ipAddress Value doesn't matched in both XML's");
       }
     
     if(compareStrings(portNum1,portNum2)){
      	 System.out.println("portNum Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-portNum Value doesn't matched in both XML's");
       }
     
     if(compareStrings(storeId1,storeId2)){
      	 System.out.println("storeId Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-storeId Value doesn't matched in both XML's");
       }
     
     if(compareStrings(storeName1,storeName2)){
      	 System.out.println("storeName Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-storeName Value doesn't matched in both XML's");
       }
     
     if(compareStrings(storeRegisterId1,storeRegisterId2)){
      	 System.out.println("storeRegisterId Value matched in both XML's");}
      	 else{
      		 System.out.println("Test Failed-storeRegisterId Value doesn't matched in both XML's");
       }
/*
     FileHandler handler = new FileHandler("logfile.log");

     // Add to the desired logger
     Logger logger = Logger.getLogger("");
     logger.addHandler(handler);
     //System.out.println("Log Generated Successfully...!!!");
*/

  } catch (Exception e) {
     // System.out.println("Log Generation Failed..!!!");
     e.printStackTrace();
  }
}
static boolean compareStrings(String str1, String str2)
	{
 	    if(str1==null || str2==null) {
 	        //return false; if you assume null not equal to null
 	        return str1==str2;
 	    }
 	    return str1.equals(str2);
 	}

}