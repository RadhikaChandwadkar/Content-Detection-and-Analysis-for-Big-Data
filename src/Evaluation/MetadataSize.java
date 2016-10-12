import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MetadataSize {

	
	public static void main(String[] args) {
		double score=0.0,n=0;
		
		File directory=new File(args[0]);
		try {
			FileWriter f=new FileWriter("minmax.csv");
			for(File file:directory.listFiles())
			{
				double size=file.length();
				score=(score*n +size)/(n+1);
				n++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Size:"+score);
	}
	
}
