import java.io.File;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;

public class html_file_classify {
	static long PNF = 0;

	public static void main(String[] args) throws IOException {
		File directory = new File("C:\\Classified_Dataset\\OCTET_STREAM"); //the data that need to be classified
		FileUtils f = new FileUtils();

		for (File file : directory.listFiles()) {

			if (file.isFile() && PNF <= 71838) {

				f.moveFileToDirectory(file, new File("C:\\Classified_Dataset\\OCTET_STREAM\\OCTET_STREAM_TRANING"), false);

				PNF++;
			}
		}
	}
}
