import java.io.File;
import java.io.IOException;

public class ExIfTool {

	public static void main(String[] args) throws IOException, InterruptedException {

		File directory = new File(args[0]);
		int my_cnt = 0;

//for all the files
		 for (File file : directory.listFiles())
		{
			System.out.println("--------------------" + my_cnt++ + "-----------------");
			

//firing the command
			 String cmd =
			 "java -Dtika.config=/home/adeshgupta/exif-tika-config.xml -classpath /home/adeshgupta/tika-master/tika-app/target/tika-app-1.13-SNAPSHOT.jar org.apache.tika.cli.TikaCLI -m "
			 + file.getAbsolutePath() + " > /home/adeshgupta/ExtraCredit2/" +
			 file.getName()+".md";
			
		//	String cmd = "java -Dtika.config=/home/adeshgupta/exif-tika-config.xml -classpath /home/adeshgupta/tika-master/tika-app/target/tika-app-1.13-SNAPSHOT.jar org.apache.tika.cli.TikaCLI -m /media/adeshgupta/Seagate Expansion Drive/data/video/mp4/0A90C67E158A05A507F710BAFF511FB2D60D1A10E9C59B4393FC7853FE53284B > home/adeshgupta/xyz.md";

			// /media/adeshgupta/Seagate Expansion
			// Drive/data/video/mp4/0A90C67E158A05A507F710BAFF511FB2D60D1A10E9C59B4393FC7853FE53284B
			ProcessBuilder process = new ProcessBuilder("bash", "-c", cmd);
			Process pb = process.start();
			pb.waitFor();

		}
	}
}
