import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonCOmbining {
	public static void main(String[] args) throws IOException {

		//input 3 folder names

//accessing each directory and combining jsons
		File dir1=new File(args[0]);
		File dir2=new File(args[1]);
		File dir3=new File(args[2]);
		File dir4=new File(args[3]);
		System.out.println("args[]0"+args[0]);
		System.out.println("args[]0"+args[1]);
		System.out.println("args[]0"+args[2]);
		System.out.println("args[]0"+args[3]);
		
		for(File file:dir1.listFiles())
		{System.out.println("------------------------------------------------------------------------");
			FileWriter jsonwrite= new FileWriter("/home/adeshgupta/CombinedJson/"+file.getName(),true); //where to write the path
		
			String data=null;
			
			BufferedReader reader = new BufferedReader( new FileReader(file.getAbsolutePath()));
			    String         line = null;
			    StringBuilder  stringBuilder = new StringBuilder();
			    String         ls = System.getProperty("line.separator");

			    
				
			    while( ( line = reader.readLine() ) != null ) 
			    {
			            stringBuilder.append( line );
			            stringBuilder.append( ls );
			    }

			  data =stringBuilder.toString();
			
			  jsonwrite.write(data);
			  System.out.println(data);
			  jsonwrite.close();
			
		}
		
		
		for(File file:dir2.listFiles())
		{
			System.out.println("----------------------------------------------");
			FileWriter jsonwrite= new FileWriter("/home/adeshgupta/CombinedJson/"+file.getName(),true);
		
			String data=null;
			
			BufferedReader reader = new BufferedReader( new FileReader(file.getAbsolutePath()));
			    String         line = null;
			    StringBuilder  stringBuilder = new StringBuilder();
			    String         ls = System.getProperty("line.separator");

			    
				
			    while( ( line = reader.readLine() ) != null ) 
			    {
			            stringBuilder.append( line );
			            stringBuilder.append( ls );
			    }

			  data =stringBuilder.toString();
			
			  jsonwrite.write(data);
			  System.out.println(data);
			  jsonwrite.close();
			
		}
		
		for(File file:dir3.listFiles())
		{
			System.out.println("-----------------------------------------");
			FileWriter jsonwrite= new FileWriter("/home/adeshgupta/CombinedJson/"+file.getName()+".json",true); //output folder
		
			String data=null;
			
			BufferedReader reader = new BufferedReader( new FileReader(file.getAbsolutePath()));
			    String         line = null;
			    StringBuilder  stringBuilder = new StringBuilder();
			    String         ls = System.getProperty("line.separator");

			    
				
			    while( ( line = reader.readLine() ) != null ) 
			    {
			            stringBuilder.append( line );
			            stringBuilder.append( ls );
			    }

			  data =stringBuilder.toString();
			
			  jsonwrite.write(data);
			  System.out.println(data);
			  jsonwrite.close();
			
		}
		
		
		for(File file:dir4.listFiles())
		{
			System.out.println("-----------------------------------------");
			FileWriter jsonwrite= new FileWriter("/home/adeshgupta/CombinedJson/"+file.getName(),true); //output folder
		
			String data=null;
			
			BufferedReader reader = new BufferedReader( new FileReader(file.getAbsolutePath()));
			    String         line = null;
			    StringBuilder  stringBuilder = new StringBuilder();
			    String         ls = System.getProperty("line.separator");

			    
				
			    while( ( line = reader.readLine() ) != null ) 
			    {
			            stringBuilder.append( line );
			            stringBuilder.append( ls );
			    }

			  data =stringBuilder.toString();
			
			  jsonwrite.write(data);
			  System.out.println(data);
			  jsonwrite.close();
			
		}

	}
}
