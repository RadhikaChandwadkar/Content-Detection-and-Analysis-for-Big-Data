import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ConvertJsonToCsv 
{

	/**
	 * convert the json files from Geotopic parser to csv format
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException
	{
		// TODO Auto-generated method stub

		
		// create a csv file
		FileWriter csvData = new FileWriter("geotopic.csv");
		csvData.write("latitude,longitude,location");
		csvData.write("\r\n");
	
		int i = 0;
		// get the directory from the command line
		File directory=new File(args[0]);
		//for each file in the directory
		for(File file:directory.listFiles())
		{
			//read the file
			Scanner scn=new Scanner(file,"UTF-8");
			String longt="",lat="",name="";
	    	
			String record="";
			scn=scn.useDelimiter("}");
		    
		    //Reading and Parsing Strings to Json
		    
		    	
		    	String nextLine=scn.nextLine();
		    	String[] list=nextLine.split("}");
		    	System.out.println(nextLine);
		      
		        
		        for(i=0;i<list.length;i++)
			    {
		        		list[i]=list[i]+"}";
		        
		        		  JSONObject obj= (JSONObject) new JSONParser().parse(list[i]);
	
		        // get the longitude, latitude and location in variables
		        if(("Geotopic_LAT").equals(obj.get("Type")))
		        {
		        	longt=(String) obj.get("Value");
		        }	
		        else
		        if(("Geotopic_LON").equals(obj.get("Type")))
		        {
		        	lat=(String) obj.get("Value");
		        }	
		        
		        else
			        if(("Geotopic_NAME").equals(obj.get("Type")))
			        {
			        	name=(String) obj.get("Value");
			        }
			    }
		
		        //combine the data into a string 
		    record=lat+","+longt+","+name; 
		    //write the record in a file
		   csvData.write(record);
		   csvData.write("\r\n");
		}	
		
		
		
		csvData.close();
		
		
	}
}

