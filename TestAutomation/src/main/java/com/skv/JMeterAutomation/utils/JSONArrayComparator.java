package com.skv.JMeterAutomation.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;

public class JSONArrayComparator implements Comparator<JSONArray>
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
   public int compare( JSONArray object1, JSONArray object2 )
   {
      List<Object> objectList1 = ( (JSONArray) object1 ).toList();
      List<Object> objectList2 = ( (JSONArray) object2 ).toList();

      int objectList1Size = objectList1.size();
      int objectList2Size = objectList2.size();

      if ( objectList1Size != objectList2Size )
      {
         if ( enableErrorString )
         {
            compareErrorString.append( "\nJSON Arrays sizes dont' match \nExpected:\n" + object1 + "\nActual:\n" + object2 );
         }

         return objectList1Size - objectList2Size;
      }

      ObjectComparatorForJSON sortObjectComparator = new ObjectComparatorForJSON();
      sortObjectComparator.setEnableErrorString( false );
      Collections.sort( objectList1, sortObjectComparator );
      Collections.sort( objectList2, sortObjectComparator );

      for ( int i = 0; i < objectList1Size; i++ )
      {
         ObjectComparatorForJSON objectComparator = new ObjectComparatorForJSON();
         objectComparator.setEnableErrorString( enableErrorString );

         int objectCompareValue = objectComparator.compare( objectList1.get( i ), objectList2.get( i ) );

         if ( ( objectCompareValue != 0 ) && enableErrorString )
         {
            compareErrorString.append( objectComparator.getCompareErrorString() );
            compareErrorString.append( "\nJSON Arrays don't match \nExpected:\n" + object1 + "\nActual:\n" + object2 );

            return objectCompareValue;
         }
      }
      return 0;
   }
}
