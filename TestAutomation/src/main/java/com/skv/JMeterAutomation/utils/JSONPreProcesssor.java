package com.skv.JMeterAutomation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONPreProcesssor
{
   public static void setValueforJSONPath( String path, String value, JSONObject object )
   {
      if ( ( path == null ) || path.isEmpty() )
      {
         return;
      }

      if ( path.startsWith( "." ) )
      {
         if ( path.length() > 1 )
         {
            path = path.substring( 1 );
         }
         else
         {
            return;
         }
      }

      if ( path.contains( "." ) )
      {
         String objectKey = path.substring( 0, path.indexOf( '.' ) );
         String subPath = path.substring( objectKey.length() + 1 );

         if ( objectKey.contains( "[" ) )
         {
            int indexStartIndex = objectKey.indexOf( '[' );
            int indexEndIndex = objectKey.indexOf( ']' );

            if ( indexEndIndex <= indexStartIndex )
            {
               return;
            }

            String indexString = objectKey.substring( indexStartIndex + 1, indexEndIndex );

            String arrayPath = path.substring( 0, indexStartIndex );

            if ( object.has( arrayPath ) )
            {
               Object pathObject = object.get( arrayPath );

               if ( pathObject instanceof JSONArray )
               {
                  JSONArray jsonArray = (JSONArray) pathObject;

                  if ( indexString.equals( "*" ) )
                  {
                     int jArrLen = jsonArray.length();

                     for ( int i = 0; i < jArrLen; i++ )
                     {
                        JSONObject subObject = jsonArray.getJSONObject( i );
                        setValueforJSONPath( subPath, value, subObject );
                        jsonArray.put( i, subObject );
                     }
                  }
                  else if ( indexString.startsWith( "@" ) )
                  {
                     String matchingAttributeName = indexString.substring( 1, indexString.indexOf( '=' ) );
                     String matchingAttributeValue = indexString.substring( matchingAttributeName.length() + 2 );

                     int jArrLen = jsonArray.length();

                     for ( int i = 0; i < jArrLen; i++ )
                     {
                        JSONObject subObject = jsonArray.getJSONObject( i );

                        if ( subObject.has( matchingAttributeName ) && subObject.getString( matchingAttributeName ).equals( matchingAttributeValue ) )
                        {
                           setValueforJSONPath( subPath, value, subObject );
                           jsonArray.put( i, subObject );
                        }
                     }
                  }
                  else
                  {
                     int aindex = Integer.parseInt( indexString );

                     int arrayLength = jsonArray.length();

                     if ( aindex < arrayLength )
                     {
                        JSONObject subObject = jsonArray.getJSONObject( aindex );
                        setValueforJSONPath( subPath, value, subObject );
                        jsonArray.put( aindex, subObject );
                     }
                  }
                  object.put( arrayPath, jsonArray );
               }
            }
         }
         else
         {
            if ( object.has( objectKey ) )
            {
               JSONObject subObject = object.getJSONObject( objectKey );
               setValueforJSONPath( subPath, value, subObject );
               object.put( objectKey, subObject );
            }
         }
      }
      else
      {
         if ( path.contains( "[" ) )
         {
            int indexStartIndex = path.indexOf( '[' );
            int indexEndIndex = path.indexOf( ']' );

            if ( indexEndIndex <= indexStartIndex )
            {
               return;
            }

            String arrayPath = path.substring( 0, indexStartIndex );

            if ( object.has( arrayPath ) )
            {
               Object pathObject = object.get( arrayPath );

               if ( pathObject instanceof JSONArray )
               {
                  JSONArray jsonArray = (JSONArray) pathObject;

                  String stringIndex = path.substring( indexStartIndex + 1, indexEndIndex );

                  if ( stringIndex.equals( "*" ) )
                  {
                     int jArrLen = jsonArray.length();

                     for ( int i = 0; i < jArrLen; i++ )
                     {
                        jsonArray.put( i, value );
                     }
                  }
                  else
                  {
                     int aindex = Integer.parseInt( stringIndex );
                     int arrayLength = jsonArray.length();

                     if ( aindex < arrayLength )
                     {
                        jsonArray.put( aindex, value );
                     }
                  }
               }
            }
         }
         else
         {
            object.put( path, value );
         }
      }

   }

   public static JSONObject preProcessJSONFile( String jsonFile, String preProcessPropertiesFile ) throws IOException
   {
      String jsonString = new String( Files.readAllBytes( Paths.get( jsonFile ) ) );

      return preProcessJSONString( jsonString, preProcessPropertiesFile );
   }

   public static JSONObject preProcessJSONString( String jsonString, String preProcessPropertiesFile ) throws IOException
   {
      Properties preProcessProperties = new Properties();

      preProcessProperties.load( new FileInputStream( new File( preProcessPropertiesFile ) ) );

      JSONObject jsonObject = null;

      jsonObject = new JSONObject( jsonString );

      for ( Object property : preProcessProperties.keySet().toArray() )
      {
         setValueforJSONPath( (String) property, preProcessProperties.getProperty( (String) property ), jsonObject );
      }

      return jsonObject;
   }
}
