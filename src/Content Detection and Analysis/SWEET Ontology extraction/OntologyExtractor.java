import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import opennlp.tools.postag.POSDictionary;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;


public class OntologyExtractor 
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		System.out.println("dsfsdfdd");
		BufferedReader reader = null;
	    try
	    {
	    	//Read the dictionary file in object
	    	POSDictionary pos=POSDictionary.create(new FileInputStream(("ont.xml")));
	    	
	    	File directory= new File(args[0]);
	    	System.out.println("directory:"+directory);
	    	for(File file:directory.listFiles())
	    	{
	
	    	
	    		//convert file to XHTML format
	    		String cmd="java -jar tika-app-1.11.jar -x "+file.getAbsolutePath()+" > myxhtmlfile.html";
				new ProcessBuilder("CMD","/C",cmd).start();
			
				String data=null;
				
				//below code gets the data from the file in a string object
				 reader = new BufferedReader( new FileReader("myxhtmlfile.html"));
				    String         line = null;
				    StringBuilder  stringBuilder = new StringBuilder();
				    String         ls = System.getProperty("line.separator");

				    
					
				    while( ( line = reader.readLine() ) != null ) 
				    {
				            stringBuilder.append( line );
				            stringBuilder.append( ls );
				    }

				  
				  data =stringBuilder.toString();
			
				 //removes the tags and comments
				data = data.replaceAll("(?s)<!--.*?-->", "");
				data = data.replaceAll("(?s)<script.*?>.*?</script>", "");
				data = data.replaceAll("(?s)<SCRIPT.*?>.*?</SCRIPT>", "");
				data = data.replaceAll("(?s)<style.*?>.*?</style>", "");
				data= data.replaceAll("<[^>]*>", "");
			
				data = data.replaceAll("[\\n]*","");
				data = removeStopWords(data);
					
				//write into a temporary text file
				BufferedWriter br=new BufferedWriter(new FileWriter("output.txt"));
				br.write(data);
				br.close();
		
	    		ObjectStream<String> lineStream = new PlainTextByLineStream(new FileReader("output.txt"));
	    		List<String> ontList=new ArrayList<>();
	    		String tag[];
	    		line=null;
	    		while ((line = lineStream.read()) != null) 
	    		{

	    			// tokenize the file data based on the existing model	
	    			TokenizerModel tm = new TokenizerModel(new FileInputStream(new File("models//en-token.bin")));
	    			TokenizerME wordBreaker = new TokenizerME(tm);	  
	    			String[] tokens = wordBreaker.tokenize(line);
	    			for(int i=0;i<tokens.length;i++)
	    			{
	    				tokens[i]=removeChars(tokens[i]);
	    				//if it is a ontology concept store it 
	    				if( (tag = pos.getTags(tokens[i]))!=null )
	    				{
	    					ontList.add(tokens[i]+" "+tag[0]);
	    				}
	    			}
			
			
			 	}
	    		// get the DOI for the file
	    		String DOI=getDOIfromPath(file.getAbsolutePath());
	    		// write the data to the file
	    		writeToFile(DOI,ontList);
	    	}
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	}
	
	//this function is used for formating the data for special characters like . , ! ?
	 private static String removeChars(String string)
	 {
		// TODO Auto-generated method stub
		 int end=string.length()-1;
	
		 if(!(Character.isAlphabetic(string.charAt(end))) && !(Character.isDigit(string.charAt(end))))
		 {
			 string=string.substring(0, end);
	
			 end=end-1;
			 if(end<0)
				 return " ";
		 }
	
		 if(!(Character.isAlphabetic(string.charAt(0))) && !(Character.isDigit(string.charAt(0))))
		 {
			 if(end==0)
				 return " ";
			
			 string=string.substring(1, end);
			
			 if(end==0)
				 return " ";
	
		 }
		 //convert to lower case for comparison with the dictionary data
		 string=string.toLowerCase();
		return string;
	 }


	@SuppressWarnings("unchecked")
	private static void writeToFile(String DOI,List<String> ontList)
	{
		try 
		{
			//if no data found return
			if(ontList.isEmpty())
				return;
			
    		DOI=DOI.substring(19);
    		FileWriter fw= new FileWriter("d:/Sweet_Files/"+DOI+".json");
			
			for(int i=0;i<ontList.size();i++)
			{
				//write the data a JSON object to the output file
				JSONObject obj=new JSONObject();
				obj.put("Type",("SWEET").toString());
				obj.put("Value",ontList.get(i));	
				fw.write(obj.toJSONString());	
			}	
		
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//this function removes the common words like a, the  which are of no use for data extraction
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

	public static String getDOIfromPath(String path) throws FileNotFoundException, ParseException
	{
	
		Scanner scn=new Scanner(new File("Mapping_File.json"),"UTF-8");
	
	    System.out.println("Path:::"+path);
	   	
	    while(scn.hasNext())
	    {
	     	String nextLine=scn.nextLine();
	     	//Reading as JSON object
	        JSONObject obj= (JSONObject) new JSONParser().parse(nextLine);
	        if(path.equals(obj.get("abs_path")))
	        {
	        	//return the DOI for the particular file
	        	return (String) obj.get("DOI");
	        }
	    }
	    //if no DOI exists return null
	   		return null;
	}
	
}
