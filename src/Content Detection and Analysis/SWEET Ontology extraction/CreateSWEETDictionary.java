package opennlp.tools.postag;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;

import opennlp.tools.postag.POSDictionary;

public class CreateSWEETDictionary 
{

	  @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
	 
	    // first we create a POSDictionary
	
	    POSDictionary dictionary = new POSDictionary();
	 
	    // suppose that you have a file with words and tags as follows:
	    // word0 tag0 tag1
	    // word1 tag2
	    // word3 tag3 tag4 tag5
	 
	    // we open the file and read it line by line...
	 
	    Scanner scanner = new Scanner(new FileInputStream("f.txt"), "UTF-8");
	    while (scanner.hasNextLine()) {
	      String line = scanner.nextLine();
	      String[] data = line.split("\\s+");
	      String word = data[0];
	      String[] tags = Arrays.copyOfRange(data, 1, data.length);
	      dictionary.addTags(word, tags);
	    }
	 
	    scanner.close();
	 
	    // now we can serialize the dictionary
	    OutputStream out = new FileOutputStream("ont.xml");
	    dictionary.serialize(out);
	    out.close();
	  }
}

