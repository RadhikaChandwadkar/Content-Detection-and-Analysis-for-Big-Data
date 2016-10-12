//Classifying data according to MIME types
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;

public class Classify_whole_dataset {
	public static void main(String[] args) throws Exception {
		File directory = new File("C:\\Classified_Dataset\\Mixed"); //the directory path that contains the unclassified files
		if (directory.isDirectory()) {
			processDirectory(directory);
		} else {
			process_File(directory);
		}
	}

	private static void process_File(File directory) {
		Tika tika = new Tika();
		try {
			System.out.println(directory.toString() + " :file type is: " + tika.detect(directory));
			add_ext(directory, tika.detect(directory));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;

	}

	private static void add_ext(File directory, String detect) throws IOException {
		FileUtils f = new FileUtils();
		
		File mydir= new File("C:\\classified_data_new\\"+detect); //Copying the detected file to its detected respective MIME type folder
		if(!mydir.exists())
		{
			mydir.mkdir();
		}

		f.copyFileToDirectory(directory, mydir, false);
		
		}

	private static void processDirectory(File directory) {
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				process_File(file);
			} else if (file.isDirectory()) {
				processDirectory(file);

			} else {
				continue;
			}
		}
		return;
	}

}
