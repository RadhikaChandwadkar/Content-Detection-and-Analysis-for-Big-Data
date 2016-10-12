import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


//this class scores each parser depending upon the data extracted from each metadata file 
public class ParserScorer {

	
	//initialize the scores for each parser
	public static double MeasureCount=0;
	public static double GrobidCount=0;
	public static double GeotopicCount=0;
	public static double OntologyCount=0;
	
	public static void main(String[] args) throws ParseException, FileNotFoundException 
	{
		
				
		//get the directory from command line
		File directory=new File(args[0]);
		
		for(File file:directory.listFiles())
		{
			Map<String,Integer> ParsingList=new HashMap<>();
			Scanner scn=new Scanner(file,"UTF-8");
		
		
			while(scn.hasNext())
			{
				
				String nextLine=scn.nextLine();
				String[] listObj=nextLine.split("}");
				for(int i=0;i<listObj.length;i++)
				{
					listObj[i]=listObj[i]+"}";
					if(listObj[i].equals("}"))
						continue;
					
					/*if(flag==false)
					{	
						score=100;
						flag=true;
					}*/
					System.out.println("list::"+listObj[i]);
					JSONObject obj = (JSONObject) new JSONParser().parse(listObj[i]);       
					String name=(String) obj.get("Type");
	        
				  	if(ParsingList.containsKey(name))
		        	{
		        		int val=ParsingList.get(name);
		        		val++;
		        		ParsingList.put(name, val);
		        	}
		        	else
		        	{
		        		System.out.println("name:"+name);
		            		ParsingList.put(name, 1);
		        	}
				}
			}
			
			
			computeScore(ParsingList);
			
			
		}
		
	
	
		System.out.println("Measurement:::"+MeasureCount);
		System.out.println("Grobid:::"+GrobidCount);
		System.out.println("Geotopic:::"+GeotopicCount);
		System.out.println("Ontology:::"+OntologyCount);
	}



	private static void computeScore(Map<String, Integer> parsingList) 
	{
		
		//if there is data for particular parser increase the score for that parsers 
		if(parsingList.containsKey("Measurements"))
		{
		
			MeasureCount++;
		}
		if(parsingList.containsKey("Geographic_LATITUDE"))
		{
			
			GeotopicCount++;
		}
		if(parsingList.containsKey("SWEET"))
		{
			
			OntologyCount++;
		}
		if(parsingList.containsKey("Grobid"))
		{
			
			GrobidCount++;
		}
	
	}



}
