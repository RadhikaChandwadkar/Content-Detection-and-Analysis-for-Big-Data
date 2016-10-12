import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import opennlp.tools.postag.SearchInPos;


public class TagRatio 
{

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException, ParseException 
	{
		
			File directory=new File(args[0]);
//for all files		
	for(File file:directory.listFiles())
			{
				/*System.out.println("lemgt of list "+directory.listFiles().length);
				System.out.println("file is:"+file.getAbsolutePath());*/
				String cmd="java -jar tika-app-1.11.jar -x "+file.getAbsolutePath()+" > myxhtmlfile.xhtml";
				Process pb=new ProcessBuilder("CMD","/C",cmd).start();
			pb.waitFor();
				String data=null;
				try
				{		
					BufferedReader reader = new BufferedReader( new FileReader("myxhtmlfile.xhtml"));
				    String         line = null;
				    StringBuilder  stringBuilder = new StringBuilder();
				    String         ls = System.getProperty("line.separator");

				    try {
				        while( ( line = reader.readLine() ) != null ) {
				            stringBuilder.append( line );
				            stringBuilder.append( ls );
				        }

				        data=stringBuilder.toString();
				    } finally {
				        reader.close();
				    }
			
//detecting and removing tags		
					//System.out.println("data is:"+data);
					File x=new File("myxhtmlfile.xhtml");
					x.delete();
					data = data.replaceAll("(?s)<!--.*?-->", "");
					data = data.replaceAll("(?s)<script.*?>.*?</script>", "");
					data = data.replaceAll("(?s)<SCRIPT.*?>.*?</SCRIPT>", "");
					data = data.replaceAll("(?s)<style.*?>.*?</style>", "");
					data= data.replaceAll("<[^>]*>", "");
				
					data = data.replaceAll("[\\n]*","");
					data = removeStopWords(data);
					
					BufferedWriter br=new BufferedWriter(new FileWriter("output.txt"));
					//data="79 degree celcius";
					br.write(data);
					br.close();
					String DOI=getDOIforFile(file.getAbsolutePath());
					//String DOI="radhika";
					SearchInPos mt=new SearchInPos();
					mt.measurementExtraction("output.txt",DOI);
					//System.out.println("returned...!");
				} 
				catch (FileNotFoundException e)
				{
				// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}

//getting the doi for the file
	private static String getDOIforFile(String path) throws ParseException, FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scn=new Scanner(new File("Mapping_File.json"),"UTF-8");

		while(scn.hasNext())
	    {
	    	
	    	String nextLine=scn.nextLine();
	    	if(nextLine.startsWith("[") || nextLine.startsWith("]"))
	    		nextLine=scn.nextLine();
	    	
	    	JSONObject obj= (JSONObject) new JSONParser().parse(nextLine);       
	        String name=(String) obj.get("abs_path");
	        if(name.equals(path))
	        {
	        	String DOI=(String) obj.get("DOI");
	       // 	System.out.println("path is: "+path);
	        	return DOI;
	        }
	    }
		return null;
	}

//REMOVING STOP WORDS
	private static String removeStopWords(String data) {
		// TODO Auto-generated method stub
		data = data.replaceAll(" the ", " ");
		data = data.replaceAll(" THE ", " ");
		data = data.replaceAll(" The ", " ");
		data = data.replaceAll(" is ", " ");
		data = data.replaceAll(" IS ", " ");
		data = data.replaceAll(" Is ", " ");
		data = data.replaceAll(" A ", " ");
		data = data.replaceAll(" a ", " ");
		data = data.replaceAll(" an ", " ");
		data = data.replaceAll(" AN ", " ");
		data = data.replaceAll(" An ", " ");
		return data;
	}
}
