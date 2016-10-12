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


public class ExtractMinMax
{
	
	public static void main(String[] args) throws IOException, ParseException
	{
		// TODO Auto-generated method stub
		File directory=new File(args[0]);
		FileWriter f=new FileWriter("minmax.csv");
		f.write("measure,min,max,avg");
		f.write("\r\n");
		Map<String, MinMax> map=new HashMap<>();
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
			        		double num=Double.parseDouble(dataval[0]);
			        		for(int j=1;j<dataval.length;j++)
			        		{
			        			data+=dataval[j]+" ";
			        		}
			        		
				        	if(map.containsKey(data))
				        	{
				        		MinMax val=map.get(data);
				        		val.changeMax(num);
				        		val.changeMin(num);
				        		map.put(data, val);
				        	}
				        	else
				        	{
				        		 MinMax m=new MinMax(num);
				        		map.put(data,m);
				        	}
				        	
		        		}
		        		else
		        		{
		        			continue;
		        		}
		   	    }
		    }
		}
		 for(Entry<String, MinMax> e: map.entrySet())
	        {
	        	String record=e.getKey()+","+e.getValue().min+","+e.getValue().max;
	        	
	        	double avg=(e.getValue().min+e.getValue().max)/2;
	        	record+=","+avg;
	  		   f.write(record);
	  		   f.write("\r\n");
	        }
		

		f.close();

	}
}
