import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MeasurementDetails {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException
	{
		// TODO Auto-generated method stub
		File directory=new File(args[0]);
		FileWriter f=new FileWriter("measure.csv");
		f.write("measure,count");
		f.write("\r\n");
		Map<String, Integer> map=new HashMap<>();
		for(File file:directory.listFiles())
		{
			Scanner scn=new Scanner(new File(file.getAbsolutePath()),"UTF-8");
			
//		    System.out.println("Path:::"+path);
		
		    while(scn.hasNext())
		    {
		    	String nextLine=scn.nextLine();
		    	nextLine=nextLine.substring(1,nextLine.length()-1);
		    	System.out.println("line:"+nextLine);
		    	String[] list=nextLine.split("}");
		    	for(int i=0;i<list.length;i++)
			    {
		    		if(list[i].startsWith(","))
		    			list[i]=list[i].substring(1,list[i].length());
		        		list[i]=list[i]+"}";
		        		System.out.println("new str:"+list[i]);
		        		JSONObject obj= (JSONObject) new JSONParser().parse(list[i]);
		        		
		        		if(("Measurements").equals(obj.get("Type")))
		        		{
		        			String data=(String) obj.get("Value");   
			        		String[] dataval=data.split(" ");
			        		data="";
			        		for(int j=1;j<dataval.length;j++)
			        		{
			        			data+=dataval[j]+" ";
			        		}
			        	
				        	if(map.containsKey(data))
				        	{
				        		int val=map.get(data);
				        		val++;
				        		map.put(data, val);
				        	}
				        	else
				        		map.put(data, 1);
		        		}
		        		else
		        		{
		        			continue;
		        		}
		   	    }
		    }
		}
		 for(Entry<String, Integer> e: map.entrySet())
	        {
	        	String record=e.getKey()+","+e.getValue();    
	  		   f.write(record);
	  		   f.write("\r\n");
	        }
		

		f.close();

	}

}
