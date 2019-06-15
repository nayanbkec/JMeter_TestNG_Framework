package com.skv.JMeterAutomation.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class ObjectComparatorForJSON implements Comparator<Object>
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

   private enum ObjectType
   {
      Boolean( 0 ), Integer( 1 ), Long( 2 ), Float( 3 ), Double( 4 ), String( 5 ), JSONObject( 6 ), HashMap( 6 ), JSONArray( 7 ), Collection( 8 ), Unknown( 9 );

      ObjectType( int typeValue )
      {
         this.typeValue = typeValue;
      }

      private int typeValue;

      public int getTypeValue()
      {
         return typeValue;
      }
   }

   ObjectType objectTypeValue( Object obj )
   {
      if ( obj instanceof Boolean )
      {
         return ObjectType.Boolean;
      }
      if ( obj instanceof Integer )
      {
         return ObjectType.Integer;
      }
      else if ( obj instanceof Long )
      {
         return ObjectType.Long;
      }
      else if ( obj instanceof Float )
      {
         return ObjectType.Float;
      }
      else if ( obj instanceof Double )
      {
         return ObjectType.Double;
      }
      else if ( obj instanceof String )
      {
         return ObjectType.String;
      }
      else if ( obj instanceof JSONObject )
      {
         return ObjectType.JSONObject;
      }
      else if ( obj instanceof HashMap )
      {
         return ObjectType.HashMap;
      }
      else if ( obj instanceof Collection )
      {
         return ObjectType.Collection;
      }
      else if ( obj instanceof JSONArray )
      {
         return ObjectType.JSONArray;
      }
      return ObjectType.Unknown;
   }

   @SuppressWarnings( {"unchecked", "rawtypes"} )
   @Override
   public int compare( Object object1, Object object2 )
   {
      ObjectType object1Type = objectTypeValue( object1 );
      ObjectType object2Type = objectTypeValue( object2 );

      int diff = object1Type.getTypeValue() - object2Type.getTypeValue();

      if ( object1Type == object2Type )
      {
         switch ( object1Type )
         {
            case Boolean:
               int lhsValue = (Boolean) object1 ? 1 : 0;
               int rhsValue = (Boolean) object2 ? 1 : 0;

               diff = lhsValue - rhsValue;

               if ( ( diff != 0 ) && enableErrorString )
               {
                  compareErrorString.append( "\nValues don't match \nExpected:\n" + lhsValue + "\nActual:\n" + rhsValue );
               }

               break;

            case Integer:
            case Long:
               long longval1 = ( (Number) object1 ).longValue();
               long longval2 = ( (Number) object2 ).longValue();

               diff = ( longval1 > longval2 ) ? 1 : ( ( longval1 < longval2 ) ? -1 : 0 );

               if ( ( diff != 0 ) && enableErrorString )
               {
                  compareErrorString.append( "\nValues don't match \nExpected:\n" + longval1 + "\nActual:\n" + longval2 );
               }

               break;

            case Float:
            case Double:
               double doubleVal1 = ( (Number) object1 ).doubleValue();
               double doubleVal2 = ( (Number) object2 ).doubleValue();

               diff = ( doubleVal1 > doubleVal2 ) ? 1 : ( ( doubleVal1 < doubleVal2 ) ? -1 : 0 );

               if ( ( diff != 0 ) && enableErrorString )
               {
                  compareErrorString.append( "\nValues don't match \nExpected:\n" + doubleVal1 + "\nActual:\n" + doubleVal1 );
               }

               break;

            case String:

               diff = ( (String) object1 ).compareTo( (String) object2 );

               if ( ( diff != 0 ) && enableErrorString )
               {
                  compareErrorString.append( "\nValues don't match \nExpected:\n" + object1 + "\nActual:\n" + object2 );
               }

               break;

            case HashMap:
               HashMapComparatorForJSON recursiveJSONHashMapComparator = new HashMapComparatorForJSON();
               recursiveJSONHashMapComparator.setEnableErrorString( enableErrorString );
               diff = recursiveJSONHashMapComparator.compare( (HashMap<String, Object>) object1, (HashMap<String, Object>) object2 );

               if ( ( diff != 0 ) && enableErrorString )
               {
                  compareErrorString.append( recursiveJSONHashMapComparator.getCompareErrorString() );
                  // compareErrorString.append( "\nJSON objects don't match \nExpected:\n" + object1 + "\nActual:\n" + object2 );
               }
               break;

            case JSONObject:
               JSONObjectComparator jSONObjectComparator = new JSONObjectComparator();
               jSONObjectComparator.setEnableErrorString( enableErrorString );
               diff = jSONObjectComparator.compare( (JSONObject) object1, (JSONObject) object2 );

               if ( ( diff != 0 ) && enableErrorString )
               {
                  compareErrorString.append( jSONObjectComparator.getCompareErrorString() );
                  // compareErrorString.append( "\nJSON objects don't match \nExpected:\n" + object1 + "\nActual:\n" + object2 );
               }

               break;

            case Collection:
               CollectionComparatorForJSON collectionComparatorForJSON = new CollectionComparatorForJSON();
               collectionComparatorForJSON.setEnableErrorString( enableErrorString );
               diff = collectionComparatorForJSON.compare( (Collection) object1, (Collection) object2 );

               if ( ( diff != 0 ) && enableErrorString )
               {
                  compareErrorString.append( collectionComparatorForJSON.getCompareErrorString() );
                  // compareErrorString.append( "\nJSON objects don't match \nExpected:\n" + object1 + "\nActual:\n" + object2 );
               }

               break;

            case JSONArray:
               JSONArrayComparator jSONArrayComparator = new JSONArrayComparator();
               jSONArrayComparator.setEnableErrorString( enableErrorString );
               diff = jSONArrayComparator.compare( (JSONArray) object1, (JSONArray) object2 );

               if ( ( diff != 0 ) && enableErrorString )
               {
                  compareErrorString.append( jSONArrayComparator.getCompareErrorString() );
                  // compareErrorString.append( "\nJSON objects don't match \nExpected:\n" + object1 + "\nActual:\n" + object2 );
               }

               break;

            default:
               diff = -1;

               if ( enableErrorString )
               {
                  compareErrorString.append( "Unknown data type or uimplemented comparision between " + object1.getClass() + " and " + object2.getClass() );
               }
               break;
         }
      }
      else
      {
         if ( enableErrorString )
         {
            compareErrorString.append( "\nJSON object types don't match \nExpected:\n" + object1Type + "\nActual:\n" + object2Type );
         }
      }

      return diff;
   }
}
