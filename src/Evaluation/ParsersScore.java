import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ParsersScore {

	/**
	 * @param args
	 */
	static double  OntologyCount=0.0;
	static double n=0;
	static double GrobidCount=0.0;
	static double GeotopicCount=0.0;
	static double MeasureCount=0.0;
	
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		File directory=new File(args[0]);
		try {
			FileWriter f=new FileWriter("minmax.csv");
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

				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double noOfFiles=directory.list().length;
		System.out.println("Measurement Parser Score:"+(MeasureCount/noOfFiles));
		System.out.println("Grobid Parser Score:"+(GrobidCount/noOfFiles));
		System.out.println("Geotopic Parser Score:"+(GeotopicCount/noOfFiles));
		System.out.println("Ontology Parser Score:"+(OntologyCount/noOfFiles));
	}

	
	private static void computeScore(Map<String, Integer> parsingList) 
	{
		
		//if there is data for particular parser increase the score for that parsers 
		if(parsingList.containsKey("Measurements"))
		{
		
			MeasureCount=MeasureCount+parsingList.get("Measurements");
		}
		if(parsingList.containsKey("Geographic_LATITUDE"))
		{
			
			GeotopicCount+=parsingList.get("Geographic_LATITUDE");
		}
		if(parsingList.containsKey("SWEET"))
		{
			
			OntologyCount+=parsingList.get("SWEET");
		}
		if(parsingList.containsKey("Grobid"))
		{
			
			GrobidCount+=parsingList.get("Grobid");
		}
	
	}

}
