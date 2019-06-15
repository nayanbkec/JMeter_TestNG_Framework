package com.skv.JMeterAutomation.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLNodeComparator implements Comparator<Node>
{
   boolean       enableErrorString                = true;
   StringBuilder compareErrorString               = new StringBuilder();
   boolean       elementLevelErrorAlreadyReported = false;

   public boolean isErrorStringEnabled()
   {
      return enableErrorString;
   }

   public void setEnableErrorString( boolean pEnableErrorString )
   {
      enableErrorString = pEnableErrorString;
   }

   public String getCompareErrorString()
   {
      return compareErrorString.toString();
   }

   public void clearCompareErrorString()
   {
      compareErrorString = new StringBuilder();
   }

   public int compareXMLDocuments( String documentFile1, String documentFile2 ) throws ParserConfigurationException, SAXException, IOException
   {
      DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      return compareXMLDocuments( db.parse( new File( documentFile1 ) ), db.parse( new File( documentFile2 ) ) );
   }

   public int compareXMLDocuments( Document document1, Document document2 ) throws ParserConfigurationException
   {
      return compare( document1.getDocumentElement(), document2.getDocumentElement() );
   }

   public int compareXMLDocuments( String documentFile1, String documentFile2, String xslTransformFile ) throws Exception
   {
      return compareXMLDocuments( transformAndLoadXML( documentFile1, xslTransformFile ), transformAndLoadXML( documentFile2, xslTransformFile ) );
   }

   public int compareXMLDocuments( Document document1, Document document2, Document xslTransformDoc ) throws Exception
   {
      document1 = transformXML( document1, xslTransformDoc );
      document2 = transformXML( document2, xslTransformDoc );

      return compare( document1.getDocumentElement(), document2.getDocumentElement() );
   }

   public static Document transformAndLoadXML( String xmlFileName, String xslFileName ) throws Exception
   {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware( true );

      DocumentBuilder db = dbf.newDocumentBuilder();

      Document xslt = db.parse( xslFileName );
      Document xml = db.parse( new File( xmlFileName ) );
      Document result = transformXML( xml, xslt );

      return result;
   }

   public static Document transformXML( Document xml, Document xslt ) throws Exception
   {
      Source xmlSource = new DOMSource( xml );
      Source xsltSource = new DOMSource( xslt );
      DOMResult result = new DOMResult();

      // the factory pattern supports different XSLT processors
      TransformerFactory transFact = TransformerFactory.newInstance();
      Transformer trans = transFact.newTransformer( xsltSource );

      trans.transform( xmlSource, result );

      Document resultDoc = (Document) result.getNode();

      return resultDoc;
   }

   private static String nodeToString( Node node )
   {
      StringWriter sw = new StringWriter();
      try
      {
         Transformer t = TransformerFactory.newInstance().newTransformer();
         t.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "yes" );
         t.setOutputProperty( OutputKeys.INDENT, "yes" );
         t.transform( new DOMSource( node ), new StreamResult( sw ) );
      }
      catch ( TransformerException te )
      {
         System.out.println( "nodeToString Transformer Exception" );
      }
      return sw.toString();
   }

   public static String getFirstLevelTextContent( Node node )
   {
      NodeList list = node.getChildNodes();
      StringBuilder textContent = new StringBuilder();
      for ( int i = 0; i < list.getLength(); ++i )
      {
         Node child = list.item( i );
         if ( child.getNodeType() == Node.TEXT_NODE )
            textContent.append( child.getTextContent() );
      }
      return textContent.toString().trim();
   }

   @Override
   public int compare( Node node1, Node node2 )
   {
      short node1Type = node1.getNodeType();
      short node2Type = node2.getNodeType();

      if ( node1Type != node2Type )
      {
         if ( enableErrorString )
         {
            compareErrorString.append( "\nNode type is not same" );
         }
         return -1;
      }

      String node1Name = node1.getNodeName();
      String node2Name = node2.getNodeName();

      int nodeNameCompareResult = node1Name.compareTo( node2Name );

      if ( node1Type == Node.ELEMENT_NODE )
      {
         // System.out.println( "Comparing *****************************************\n" + nodeToString( node1 ) + "\n and *****************************************\n" + nodeToString( node2 ) );

         if ( nodeNameCompareResult != 0 )
         {
            if ( enableErrorString )
            {
               compareErrorString.append( "\nElement names dont match (" + node1Name + " and " + node2Name + ")." );
            }

            return nodeNameCompareResult;
         }
         else
         {
            // Compare the attributes list of the element
            NamedNodeMap node1AttributeList = node1.getAttributes();
            NamedNodeMap node2AttributeList = node2.getAttributes();

            ArrayList<Node> attrList1 = new ArrayList<Node>();
            ArrayList<Node> attrList2 = new ArrayList<Node>();

            for ( int i = 0; i < node1AttributeList.getLength(); i++ )
            {
               attrList1.add( node1AttributeList.item( i ) );
            }

            for ( int i = 0; i < node2AttributeList.getLength(); i++ )
            {
               attrList2.add( node2AttributeList.item( i ) );
            }

            boolean saveEnableErrorString = enableErrorString;
            enableErrorString = false;
            Collections.sort( attrList1, this );
            Collections.sort( attrList2, this );
            enableErrorString = saveEnableErrorString;

            int attrList1Size = attrList1.size();
            int attrList2Size = attrList2.size();

            for ( int i = 0; i < attrList1Size; i++ )
            {
               if ( i > ( attrList2Size - 1 ) )
               {
                  if ( enableErrorString )
                  {
                     compareErrorString.append( "\nExpected more attributes\nExpected:\n" + nodeToString( node1 ) + "\nActual:\n" + nodeToString( node2 ) );
                     elementLevelErrorAlreadyReported = true;
                  }
                  return 1;
               }

               int attrNodeCompareResult = compare( attrList1.get( i ), attrList2.get( i ) );

               if ( attrNodeCompareResult != 0 )
               {
                  if ( enableErrorString )
                  {
                     compareErrorString.append( "\nAttributes don't match\nExpected:\n" + attrList1.get( i ) + "\nActual:\n" + attrList2.get( i ) );
                  }
                  return attrNodeCompareResult;
               }
            }

            if ( attrList2Size > attrList1Size )
            {
               if ( enableErrorString )
               {
                  compareErrorString.append( "\nExpected lesser attributes\nExpected:\n" + nodeToString( node1 ) + "\nActual:\n" + nodeToString( node2 ) );
                  elementLevelErrorAlreadyReported = true;
               }

               return -1;
            }
            else
            {
               // Compare child nodes for the element
               NodeList node1Children = ( (Element) node1 ).getElementsByTagName( "*" );
               NodeList node2Children = ( (Element) node2 ).getElementsByTagName( "*" );// getElementsByTagName( "*" );

               ArrayList<Node> subElementList1 = new ArrayList<Node>();
               ArrayList<Node> subElementList2 = new ArrayList<Node>();

               for ( int i = 0; i < node1Children.getLength(); i++ )
               {
                  subElementList1.add( node1Children.item( i ) );
               }

               for ( int i = 0; i < node2Children.getLength(); i++ )
               {
                  subElementList2.add( node2Children.item( i ) );
               }

               saveEnableErrorString = enableErrorString;
               enableErrorString = false;
               Collections.sort( subElementList1, this );
               Collections.sort( subElementList2, this );
               enableErrorString = saveEnableErrorString;

               int list1Length = subElementList1.size();
               int list2Length = subElementList2.size();

               for ( int i = 0; i < list1Length; i++ )
               {
                  if ( i > ( list2Length - 1 ) )
                  {
                     if ( enableErrorString )
                     {
                        compareErrorString.append( "\nSome sub nodes are missing\nExpected:\n" + nodeToString( node1 ) + "\nActual:\n" + nodeToString( node2 ) );
                        elementLevelErrorAlreadyReported = true;
                     }
                     return 1;
                  }

                  int subElementCompareResult = compare( subElementList1.get( i ), subElementList2.get( i ) );

                  if ( subElementCompareResult != 0 )
                  {
                     if ( enableErrorString && !elementLevelErrorAlreadyReported )
                     {
                        compareErrorString.append( "\nSub nodes don't match\nExpected:\n" + nodeToString( subElementList1.get( i ) ) + "\nActual:\n" + nodeToString( subElementList2.get( i ) ) );
                        elementLevelErrorAlreadyReported = true;
                     }
                     return subElementCompareResult;
                  }
               }

               if ( list2Length > list1Length )
               {
                  if ( enableErrorString )
                  {
                     compareErrorString.append( "\nMore sub nodes found than expected\nExpected:\n" + nodeToString( node1 ) + "\nActual:\n" + nodeToString( node2 ) );
                     elementLevelErrorAlreadyReported = true;
                  }
                  return -1;
               }
            }

            String node1Text = getFirstLevelTextContent( node1 );
            String node2Text = getFirstLevelTextContent( node2 );

            int nodeTextCompareResult = node1Text.compareTo( node2Text );

            if ( nodeTextCompareResult != 0 )
            {
               if ( enableErrorString )
               {
                  compareErrorString.append( "\nNode text don't match\nExpected:\n" + node1Text + "\nActual:\n" + node2Text );
                  return nodeTextCompareResult;
               }
            }

            return 0;
         }
      }
      if ( node1Type == Node.ATTRIBUTE_NODE )
      {
         if ( nodeNameCompareResult != 0 )
         {
            if ( enableErrorString )
            {
               compareErrorString.append( "\nAttribute names don't match\nExpected:\n" + node1.getNodeName() + "\nActual:\n" + node2.getNodeName() );
            }
            return nodeNameCompareResult;
         }
      }

      String node1Value = node1.getNodeValue();
      String node2Value = node2.getNodeValue();

      int nodeValueCompareResult = node1Value.compareTo( node2Value );

      if ( nodeValueCompareResult != 0 )
      {
         if ( enableErrorString )
         {
            compareErrorString.append( "\nNode values don't match\nExpected:\n" + node1Value + "\nActual:\n" + node2Value );
         }
      }

      return nodeValueCompareResult;
   }
}