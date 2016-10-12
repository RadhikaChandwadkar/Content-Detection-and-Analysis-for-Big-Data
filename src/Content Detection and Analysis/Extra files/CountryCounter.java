import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class CountryCounter {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		
        // create a csv file		
		FileWriter csvData = new FileWriter("geotopic.csv");
		csvData.write("location,count");
		// create a map for keeping count of each location
		Map<String,Integer> map=new TreeMap<>(); 
		csvData.write("\r\n");
		String record="";
		int i = 0;

		File directory=new File(args[0]);
		for(File file:directory.listFiles())
		{

			Scanner scn=new Scanner(file,"UTF-8");
			String name="";
	    	
		
			scn=scn.useDelimiter("}");
		    
		    //Reading and Parsing Strings to Json
		    
		    	
		    	String nextLine=scn.nextLine();
		    	String[] list=nextLine.split("}");
		    //	nextLine=nextLine+"}";
		//    	if(nextLine.startsWith("[") || nextLine.startsWith("]"))
		 //   		nextLine=scn.nextLine();
		    //	nextLine=nextLine.substring(0,nextLine.length()-1);
		    //	System.out.println(nextLine);	
		    	System.out.println(nextLine);
		      
		        
		        for(i=0;i<list.length;i++)
			    {
		        		list[i]=list[i]+"}";
		        
		        		  JSONObject obj= (JSONObject) new JSONParser().parse(list[i]);
		    
		        		  //read the location and the update the count for it
			        if(("Geotopic_NAME").equals(obj.get("Type")))
			        {
			        	name=(String) obj.get("Value");
			        	if(map.containsKey(name))
			        	{
			        		int val=map.get(name);
			        		val++;
			        		map.put(name, val);
			        	}
			        	else
			        		map.put(name, 1);
			        }
			    }
		
		        
		       
		   
		}	
	
		//write the records to csv
		 for(Entry<String, Integer> e: map.entrySet())
	        {
	        	record=e.getKey()+","+e.getValue();    
	  		   csvData.write(record);
	  		   csvData.write("\r\n");
	        }
		
		csvData.close();
		
		
	}

}
