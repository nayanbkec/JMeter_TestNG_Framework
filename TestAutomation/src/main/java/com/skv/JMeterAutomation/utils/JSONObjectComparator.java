package com.skv.JMeterAutomation.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONObject;

public class JSONObjectComparator implements Comparator<JSONObject>
{

   boolean       enableErrorString  = true;
   StringBuilder compareErrorString = new StringBuilder();

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

   public int compareJSONFiles( String jsonFile1, String jsonFile2, String preProcessPropertyFile ) throws IOException
   {
      String jsonString1 = new String( Files.readAllBytes( Paths.get( jsonFile1 ) ) );
      String jsonString2 = new String( Files.readAllBytes( Paths.get( jsonFile2 ) ) );

      return compareJSONStrings( jsonString1, jsonString2, preProcessPropertyFile );
   }

   public int compareJSONFiles( String jsonFile1, String jsonFile2 ) throws IOException
   {
      String jsonString1 = new String( Files.readAllBytes( Paths.get( jsonFile1 ) ) );
      String jsonString2 = new String( Files.readAllBytes( Paths.get( jsonFile2 ) ) );

      return compareJSONStrings( jsonString1, jsonString2 );
   }

   public int compareJSONStrings( String jsonString1, String jsonString2 )
   {
      JSONObject jobj1 = new JSONObject( jsonString1 );
      JSONObject jobj2 = new JSONObject( jsonString2 );

      return compare( jobj1, jobj2 );
   }

   public int compareJSONStrings( String jsonString1, String jsonString2, String preProcessPropertyFile ) throws IOException
   {
      JSONObject jobj1 = JSONPreProcesssor.preProcessJSONString( jsonString1, preProcessPropertyFile );
      JSONObject jobj2 = JSONPreProcesssor.preProcessJSONString( jsonString2, preProcessPropertyFile );

      return compare( jobj1, jobj2 );
   }

   @Override
   public int compare( JSONObject jobj1, JSONObject jobj2 )
   {
      ArrayList<String> keyArrayList1 = new ArrayList<String>( jobj1.keySet() );
      ArrayList<String> keyArrayList2 = new ArrayList<String>( jobj2.keySet() );

      Collections.sort( keyArrayList1 );
      Collections.sort( keyArrayList2 );

      int keyArraySize1 = keyArrayList1.size();
      int keyArraySize2 = keyArrayList2.size();

      if ( keyArraySize1 != keyArraySize2 )
      {
         if ( enableErrorString )
         {
            compareErrorString.append( "\nNumber of attributes do not match \nExpected:\n" + jobj1.toString() + "\nActual:\n" + jobj2.toString() );
         }
         return keyArraySize1 - keyArraySize2;
      }

      for ( int i = 0; i < keyArraySize1; i++ )
      {
         int keyNameCompareResult = keyArrayList1.get( i ).compareTo( keyArrayList2.get( i ) );

         if ( keyNameCompareResult != 0 )
         {
            return keyNameCompareResult;
         }

         Object subObject1 = jobj1.get( keyArrayList1.get( i ) );
         Object subObject2 = jobj2.get( keyArrayList2.get( i ) );

         ObjectComparatorForJSON objectComparator = new ObjectComparatorForJSON();
         objectComparator.setEnableErrorString( enableErrorString );

         int subObjectCompareResult = objectComparator.compare( subObject1, subObject2 );

         if ( subObjectCompareResult != 0 )
         {
            if ( enableErrorString )
            {
               compareErrorString.append( objectComparator.getCompareErrorString() );
               compareErrorString.append( "\nJSON key value does not match \nExpected:\n" + keyArrayList1.get( i ) + ":" + subObject1 + "\nActual:\n" + keyArrayList2.get( i ) + ":" + subObject2 );
               // compareErrorString.append( "\nJSON objects don't match \nExpected:\n" + jobj1 + "\nActual:\n" + jobj2 );
            }

            return subObjectCompareResult;
         }
      }

      return 0;
   }
}
