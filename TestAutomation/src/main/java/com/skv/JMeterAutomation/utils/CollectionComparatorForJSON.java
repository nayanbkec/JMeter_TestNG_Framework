package com.skv.JMeterAutomation.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

@SuppressWarnings( "rawtypes" )
public class CollectionComparatorForJSON implements Comparator<Collection>
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

   @SuppressWarnings( {"unchecked"} )
   @Override
   public int compare( Collection object1, Collection object2 )
   {
      ArrayList<Object> objectList1 = new ArrayList<Object>( object1 );
      ArrayList<Object> objectList2 = new ArrayList<Object>( object2 );

      int objectList1Size = objectList1.size();
      int objectList2Size = objectList2.size();

      if ( objectList1Size != objectList2Size )
      {
         if ( enableErrorString )
         {
            compareErrorString.append( "\nCollection sizes dont' match \nExpected:\n" + object1 + "\nActual:\n" + object2 );
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
            compareErrorString.append( "\nCollection items does not match \nExpected:\n" + object1 + "\nActual:\n" + object2 );

            return objectCompareValue;
         }
      }
      return 0;
   }

}
