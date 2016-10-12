import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class OntologyCounter {

	/**
	 * This counts the concepts found the metadata 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// create a csv
		FileWriter csvData = new FileWriter("sweet.csv");
		Map<String,Integer> map=new TreeMap<>(); 
		
		String record="";
		
		try
		{
		
		// write data to csv
		csvData.write("SWEET Concept,count");
		csvData.write("\r\n");
		int i = 0;

		//get directory from command
		File directory=new File(args[0]);
		//for each file
		for(File file:directory.listFiles())
		{
	
			Scanner scn=new Scanner(file,"UTF-8");
			String name="";
	    	
		
			while(scn.hasNext())
		   {
		    	String nextLine=scn.nextLine();
		    	System.out.println("line:"+nextLine);
		    	String[] list=nextLine.split("}");
		      
		        
		        for(i=0;i<list.length;i++)
			    {
		        		list[i]=list[i]+"}";
		        		System.out.println("new str:"+list[i]);
		        		  JSONObject obj= (JSONObject) new JSONParser().parse(list[i]);
		    
		        		 String data=(String) obj.get("Value");
			        
		        		 String[] dataval=data.split(" ");
		        		 name=dataval[1];
		        		 
		        		 //read the concept found and updates its count in the list
			        	if(map.containsKey(dataval[1]))
			        	{
			        		int val=map.get(name);
			        		val++;
			        		map.put(name, val);
			        	}
			        	else
			        	{
			        		System.out.println("name:"+name);
			            		map.put(name, 1);
			        	}
			    }    
		   }	   
		}	
	
		
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
			// write the list to the csv file
			 for(Entry<String, Integer> e: map.entrySet())
		        {
				 	System.out.println(e.getKey()+","+e.getValue());
				   record=e.getKey()+","+e.getValue();    
		  		   csvData.write(record);
		  		   csvData.write("\r\n");
		        }
			
			csvData.close();
		}
	}

}