import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class DocumentLanguageDetection {
	//static FileWriter newFile;
	static HashMap<String,Long> map=new HashMap<String,Long>();
   public static void main(final String[] args) throws IOException, SAXException, TikaException {

      //Instantiating a file object
	   File directory = new File(args[0]); 
	   System.out.println(args[0]);
      //File file = new File("sample.txt");
      
	   if (directory.isDirectory()) {
			processDirectory(directory);
		} else {
			process_File(directory);
		}

	   Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	       // it.remove(); // avoids a ConcurrentModificationException
	    }
      
   }
   
   
   private static void processDirectory(File directory) throws ClientProtocolException, IOException, ParseException, SAXException, TikaException {
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

   
   private static void process_File(File file) throws ClientProtocolException, IOException, ParseException, SAXException, TikaException {
		// TODO Auto-generated method stub
	   
	   	try {
			Parser parser = new AutoDetectParser();
			 BodyContentHandler handler = new BodyContentHandler(-1);
				
			  Tika tika=new Tika();
			  tika.setMaxStringLength(10*1024*1024);
			  Metadata metadata = new Metadata();
			  FileInputStream content = new FileInputStream(file);
			  Long count=(long) 1;
			  //Parsing the given document
			  parser.parse(content, handler, metadata, new ParseContext());
			 // tika.parse(content, metadata);
			  LanguageIdentifier object = new LanguageIdentifier(handler.toString());
			  
			  //map.put(object.getLanguage(), 1);
			  if(!map.containsKey(object.getLanguage())){
				  map.put(object.getLanguage(), count);
			  }
			  else{
				  count=map.get(object.getLanguage());
				  map.put(object.getLanguage(), count+1);
			  }
			  System.out.println("Language name :" + object.getLanguage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}