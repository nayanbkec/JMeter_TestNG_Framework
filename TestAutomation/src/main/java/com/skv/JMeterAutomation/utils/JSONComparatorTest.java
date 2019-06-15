package com.skv.JMeterAutomation.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class JSONComparatorTest
{
	public static void main( String[] args ) throws IOException
	{
		int compareResult=1;
		String expectedFilePath="D:/RMA-JMETER/jsonfiles/Inventory_WinMAInputData.json";
		String actualFilePath="D:/RMA-JMETER/jsonfiles/Inventory_WinMAOutputData.json";
		String ignoreFilePath="D:/RMA-JMETER/jsonfiles/jsontransform.properties";
		
		
		
		
		
		String jsonString1 = new String( Files.readAllBytes( Paths.get( expectedFilePath ) ) );
      String jsonString2 = new String( Files.readAllBytes( Paths.get( actualFilePath ) ) );
      
      JSONObject jobj1 = new JSONObject( jsonString1 );
      JSONObject jobj2 = new JSONObject( jsonString2 );
		
		String jsonDataString1 = jobj1.getString( "data" );
		String jsonDataString2 = jobj2.getString( "data" );
		

		JSONObjectComparator jsonComparator = new JSONObjectComparator();

		compareResult = jsonComparator.compareJSONStrings( jsonDataString1,jsonDataString2 , ignoreFilePath );

		if(compareResult == 0){
			System.out.println( "JSON's are similar " );
		} else {
			System.out.println( "JSON's are different");
		}

		if ( compareResult != 0 )
		{
			System.out.println( "Compare error is as follows\n" + jsonComparator.getCompareErrorString() );
		}
	}
}
