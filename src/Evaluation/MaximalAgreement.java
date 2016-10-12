import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MaximalAgreement {
	
	public static void main(String[] args) throws ParseException, IOException
	{
		File directory=new File(args[0]);
		FileWriter f=new FileWriter("o.txt");
		//map to keep rexord of the maximally agreed words and their count
		Map<String,Integer> map= new HashMap();
	
		for(File file:directory.listFiles())
		{
			Scanner scn=new Scanner(new File(file.getAbsolutePath()),"UTF-8");
			

		
		    while(scn.hasNext())
		    {
		    	String nextLine=scn.nextLine();
		    	System.out.println("line:"+nextLine);
		    	String[] list=nextLine.split("}");
		    	for(int i=0;i<list.length;i++)
			    {
		        		list[i]=list[i]+"}";
		        		System.out.println("new str:"+list[i]);
		        		JSONObject obj= (JSONObject) new JSONParser().parse(list[i]);
		        		String data=(String) obj.get("Value");   
		        		String[] dataval=data.split(" ");
		        	
		        		//finding the value of the word and incrementing it in the map 
		        		int cnt=map.get(data);
		        		map.put(data, cnt++);
		        		
		        		f.write(dataval[0]);
		        		f.write("\r\n");
		        		f.write(dataval[1]);
		        		f.write("\r\n");
			    }
		    }
		}
		f.close();
	}
}
