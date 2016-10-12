import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ExtractData {
	
	public static void main(String[] args) throws ParseException, IOException
	{
		File directory=new File(args[0]);
		FileWriter f=new FileWriter("o.txt");
	
	
		for(File file:directory.listFiles())
		{
			Scanner scn=new Scanner(new File(file.getAbsolutePath()),"UTF-8");
			
//		    System.out.println("Path:::"+path);
		
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
		        		//String finaldata=" "+dataval[1];
		        		
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
