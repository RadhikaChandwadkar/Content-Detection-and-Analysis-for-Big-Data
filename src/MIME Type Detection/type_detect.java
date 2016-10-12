import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;

public class type_detect {

	public static void main(String[] args) throws Exception {
		File directory = new File("C:\\Classified_Dataset\\run_octet_zero");// mydirectory
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
			add_ext(directory, tika.detect(directory));//detecting and sending the file type
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;

	}

	private static void add_ext(File directory, String detect) {
		FileUtils f = new FileUtils();
		System.out.println("****"+detect);
		
		/*-------------*/
		try {
			switch (detect) {

			case "text/html":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\HTML"), false);
				break;
			case "application/octet-stream":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\OCTET_STREAM"), false);
				break;
			case "application/pdf":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\PDF"), false);
				break;
			case "image/jpeg":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\JPEG"), false);
				break;
			case "video/mp4":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\VIDEO_MP4"), false);
				break;
			case "audio/mpeg":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\AUDIO_MPEG"), false);
				break;
			case "application/x-executable":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\APP_X_EXECUATBLE"), false);
				break;
			case "application/epub+zip":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\APP_EPUB_ZIP"), false);
				break;
			case "application/msword":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\MSWORD"), false);
				break;
			case "application/x-hdf":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\APP_XHDF"), false);
				break;
			case "application/vnd.ms-excel":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\APP_EXCEL"), false);
				break;
			case "text/x-php":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\PHP"), false);
				break;
			case "xscapplication/zip":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\ZIP"), false);
				break;
			case "message/rfc822":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\RFC822"), false);
				break;
			case "video/x-flv":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\FLV"), false);
				break;
			default:
				break;
			case "application/x-netcdf":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\cdf"), false);
				break;
				
			case "application/vnd.xara":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\xar"), false);
				break;
				
			case "application/vnd.webturbo":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\wordperfect"), false);
				break;
			
			case "video/quicktime":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\mdat"), false);
				break;
			
			case "application/zerosize":
				f.moveFileToDirectory(directory, new File("C:\\Classified_Dataset\\zero_files"), false);
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//processing the directory structure recursively
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
