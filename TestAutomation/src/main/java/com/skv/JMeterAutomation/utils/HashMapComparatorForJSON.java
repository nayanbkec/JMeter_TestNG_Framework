package com.skv.JMeterAutomation.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class HashMapComparatorForJSON implements Comparator<HashMap<String, Object>>
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

   @Override
   public int compare( HashMap<String, Object> hashMap1, HashMap<String, Object> hashMap2 )
   {
      ArrayList<String> keyArrayList1 = new ArrayList<String>( hashMap1.keySet() );
      ArrayList<String> keyArrayList2 = new ArrayList<String>( hashMap2.keySet() );

      Collections.sort( keyArrayList1 );
      Collections.sort( keyArrayList2 );

      int keyArraySize1 = keyArrayList1.size();
      int keyArraySize2 = keyArrayList2.size();

      if ( keyArraySize1 != keyArraySize2 )
      {
         if ( enableErrorString )
         {
            compareErrorString.append( "\nNumber of attributes do not match \nExpected:\n" + hashMap1.toString() + "\nActual:\n" + hashMap2.toString() );
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

         Object subObject1 = hashMap1.get( keyArrayList1.get( i ) );
         Object subObject2 = hashMap2.get( keyArrayList2.get( i ) );

         ObjectComparatorForJSON objectComparator = new ObjectComparatorForJSON();
         objectComparator.setEnableErrorString( enableErrorString );

         int subObjectCompareResult = objectComparator.compare( subObject1, subObject2 );

         if ( subObjectCompareResult != 0 )
         {
            if ( enableErrorString )
            {
               compareErrorString.append( objectComparator.getCompareErrorString() );
               compareErrorString.append( "\nHashMap key value does not match \nExpected:\n" + keyArrayList1.get( i ) + ":" + subObject1 + "\nActual:\n" + keyArrayList2.get( i ) + ":" + subObject2 ); //
            }

            return subObjectCompareResult;
         }
      }

      return 0;
   }
}
