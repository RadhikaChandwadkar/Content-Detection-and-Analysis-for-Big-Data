import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SizeDiversity {

	public static void main(String[] args) throws  ParseException {
		double file_cnt=0;
		File directory= new File(args[0]);
		
		
		double avg=0;
		try
		{
		for(File file:directory.listFiles())
		{
			System.out.println("--------------"+file_cnt+"------------");
			
			double  main_file = file.length();
			
			String DOI=getDOIfromPath(file.getAbsolutePath());
			DOI= DOI.substring(19 );
			System.out.println("DOI:"+DOI);
			File meta= new File("D:\\SampleSet\\"+DOI+".json");
			
			double meta_file=meta.length();
			
			double ratio=meta_file/main_file;
			System.out.println("main file: "+main_file);
			System.out.println("meta file: "+meta_file);
			System.out.println("ratio: "+ratio);
			avg=(avg*file_cnt+ratio)/(file_cnt+1);
			System.out.println("avg: "+avg);
			file_cnt++;
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		System.out.println("score: "+avg);
	}
		
		
	}

	public static String getDOIfromPath(String path) throws FileNotFoundException, ParseException {

		Scanner scn = new Scanner(new File("Mapping_File.json"), "UTF-8");

		System.out.println(path);
		while (scn.hasNext()) {

			String nextLine = scn.nextLine();
			if (nextLine.startsWith("[") || nextLine.startsWith("]"))
				nextLine = scn.nextLine();

			JSONObject obj = (JSONObject) new JSONParser().parse(nextLine);
			String name = (String) obj.get("abs_path");
			if (name.equalsIgnoreCase(path)){
				System.out.println("found");
				String DOI = (String) obj.get("DOI");
				// System.out.println("path is: "+path);
				return DOI;
			}
		}
		return null;
	}
}
