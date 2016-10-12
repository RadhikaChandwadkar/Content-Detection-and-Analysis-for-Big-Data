package opennlp.tools.postag;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;


// Search the concepts in the Ontology Dictionary
public class SearchInSWEETDictionary {

	public void measurementExtraction(String filename,String DOI) throws IOException {

	
	PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
	//read the file data
	ObjectStream<String> lineStream = new PlainTextByLineStream(new FileReader(filename));
	// get the dictionary
	POSDictionary pos=POSDictionary.create(new FileInputStream(("posDict.xml")));

	FileWriter metaFile= new FileWriter(DOI+".json",true);
	
	perfMon.start();
	String line,tag[];
	List<String> measurementList=new ArrayList<>();
	boolean flag=false;
	String newMeasure="";
		while ((line = lineStream.read()) != null) 
		{
 
			//tokenize the file based on white spaces
			
			String tokens[] = WhitespaceTokenizer.INSTANCE.tokenize(line);		
			for(int i=0;i<tokens.length;i++)
			{
				//remove the unwanted words
				tokens[i]=removeChars(tokens[i]);
				//record integer 
				if(isInteger(tokens[i]))
				{
						newMeasure=tokens[i];
						flag=true;
						System.out.println("token is found int: "+ tokens[i]);
						continue;
				}	
	
				if( flag==true && (tag = pos.getTags(tokens[i]))!=null )
				{
					System.out.println("token is flag=true: "+ tokens[i]);
					newMeasure=newMeasure+" "+tokens[i];
					flag=true;
					continue;
				}
				else
				{
					if(!newMeasure.equals(""))
					{
						measurementList.add(newMeasure);
						newMeasure="";
					}
					flag=false;	
				}
			}
		}
		writeToFile(metaFile, measurementList);
	}
	
	private void writeToFile(FileWriter fw,List<String> measurementList)
	{
		try 
		{
			fw.write("[\n");
			
			JSONObject obj=new JSONObject();
			obj.put("Type",("Measurements").toString());
			obj.put("Info",measurementList);
			fw.write(obj.toJSONString());
			fw.write("]");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	 private static String removeChars(String string)
	 {
		// TODO Auto-generated method stub
		 int end=string.length()-1;
		// System.out.println("bfre 1st if:"+string);
		 if(!(Character.isAlphabetic(string.charAt(end))) && !(Character.isDigit(string.charAt(end))))
		 {
			 string=string.substring(0, end);
			// System.out.println("in 1st if:"+string);
			 end=end-1;
			 if(end<0)
				 return " ";
		 }
		// System.out.println("after 1st if:"+string);
		 if(!(Character.isAlphabetic(string.charAt(0))) && !(Character.isDigit(string.charAt(0))))
		 {
			 
			 string=string.substring(1, end);
			// System.out.println("in 2nd if:"+string);
		 }
		 string=string.toLowerCase();
		return string;
	 }

	 
	 
	 

	public static boolean isInteger(String s) {
	      boolean isValidInteger = false;
	      try
	      {
	         Double.parseDouble(s);
	 
	         // s is a valid integer
	 
	         isValidInteger = true;
	      }
	      catch (NumberFormatException ex)
	      {
	         // s is not an integer
	      }
	 
	      return isValidInteger;
	   }


	
	
}
