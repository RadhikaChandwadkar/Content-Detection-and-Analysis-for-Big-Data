import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/* This class gets the score for each file from the dataset and stores the score along with the DOI in the metadata file 
 * for each file and it also print the final average metascore for all the parsers. 
*/
public class GetMetaScore 
{	
	public static void main(String[] args) throws ParseException, FileNotFoundException 
	{
		//initialize the score variables
		double  avgScore=0;
		double numfiles=0;
		//get the directory from command line
		File directory=new File(args[0]);
		Map<String,Double> ScoreList=new HashMap<>();
		for(File file:directory.listFiles())
		{
			System.out.println("___________________________-");
			System.out.println("File"+file.getAbsolutePath());
			System.out.println("___________________________-");
			//list for keeping the count of data collected by each parser
			Map<String,Integer> ParsingList=new HashMap<>();
			Scanner scn=new Scanner(file,"UTF-8");
			
			double score=0;
			score+=50;	//Full points for proper DOI
			score+=50;	//Full points for unique DOI
			score+=100; //Full points for id as DOI
			
			while(scn.hasNext())
			{
				//read the line
				String nextLine=scn.nextLine();
				String[] listObj=nextLine.split("}");
				for(int i=0;i<listObj.length;i++)
				{
					listObj[i]=listObj[i]+"}";
					if(listObj[i].equals("}"))
						continue;
					
					//read the JSON object representing the data
					JSONObject obj = (JSONObject) new JSONParser().parse(listObj[i]);       
					String name=(String) obj.get("Type");
					//update the parser list with new data
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
			
			// compute the score for each file
			score=computeScore(score,ParsingList);
			//put the score in a list
			ScoreList.put(file.getAbsolutePath(), score);
			//double finalScore=score / totalScore;
			//update the final average score
			avgScore=((avgScore*numfiles)+score)/(++numfiles);
		}
		// write the scores and DOI to each of the JSON file
		writeScoreToFiles(directory,ScoreList);
		//print the average score
		System.out.println("META SCORE is:::"+avgScore);
	}

	@SuppressWarnings("unchecked")
	private static void writeScoreToFiles(File directory,Map<String, Double> scoreList) 
	{
		try
		{
		//for each file in the directory append the score and DOI as JSON objects AT THE END 
		for(String fileName:directory.list())
		{
			String filePath=directory.getAbsolutePath()+"\\"+fileName;
	
			FileWriter file = new FileWriter(filePath,true);
			//add score
			JSONObject obj=new JSONObject();
			obj.put("Type",("score").toString());
			obj.put("Value",scoreList.get(filePath));	
			file.write(obj.toJSONString());	
		
			//add DOI
			obj=new JSONObject();
			obj.put("Type",("DOI").toString());
			obj.put("Value",("polar.usc.edu:8080/"+fileName).toString());	
			file.write(obj.toJSONString());	
		
			
			
			file.flush();
			file.close();
		}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}

	private static double computeScore(double score,Map<String, Integer> parsingList) 
	{
		
		// if measurement data exists add to score
		if(parsingList.containsKey("Measurements"))
		{
			score=score+100;			
		}
		//if Geotopic data exists add to score
		if(parsingList.containsKey("Geographic_LATITUDE"))
		{
			score=score+100;
		}
		//if SWEET concepts found add to score
		if(parsingList.containsKey("SWEET"))
		{
			score=score+100;
		}
		//if Grobid extracted publications for this file add to score
		if(parsingList.containsKey("Grobid"))
		{
			score=score+100;
		}
		return score;
	}


}