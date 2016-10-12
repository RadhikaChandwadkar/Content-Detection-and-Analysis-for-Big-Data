import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class GrobidParser {
	static ArrayList<String> authorlist;

	static int cnt=0;
	public static void main(String[] args) throws InterruptedException,
			IOException, ParserConfigurationException, SAXException, ParseException {

		File directory = new File(args[0]);
		FileUtils util = new FileUtils();
//parsing all files		
		for (File file : directory.listFiles())
		{
			
			
//adding pdf extensions
			System.out.println("--------------------------------------"+cnt+++"-----------------------------");
			util.copyFileToDirectory(file, new File("/home/adeshgupta/grobidtemp/"));

			File oldfile = new File("/home/adeshgupta/grobidtemp/").listFiles()[0];

			String filename = file.getName();
			String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());

			String excel = "pdf";
			File newfile = null;
			if (!extension.equals(excel)) {
				System.out.println("adding pdf extension");
				newfile = new File("/home/adeshgupta/src/grobid/papers/" + oldfile.getName() + ".pdf");
			} else {
				System.out.println("extension already present");
				newfile = new File("/home/adeshgupta/src/grobid/papers/" + oldfile.getName());// )+".pdf");
			}

			oldfile.renameTo(newfile);

//firing the command for Grobid

			ProcessBuilder pb = new ProcessBuilder("bash", "-c",
					"java -Xmx1024m -jar /home/adeshgupta/src/grobid/grobid-core/target/grobid-core-0.4.1-SNAPSHOT.one-jar.jar -gH /home/adeshgupta/src/grobid/grobid-home/ -gP /home/adeshgupta/src/grobid/grobid-home/config/grobid.properties -dIn /home/adeshgupta/src/grobid/papers/ -dOut /home/adeshgupta/src/grobid/out -exe processFullText");
			Process process = pb.start();
			
			int errCode = process.waitFor();
			
			System.out.println("Echo command executed, any errors? " + (errCode == 0 ? "No" : "Yes"));
			System.out.println("Echo Output:\n" + output(process.getErrorStream()));

			
			//newfile.delete();
			/* Parsing the TEI XML file */
			System.out.println("parsing the tei file....!");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			SAXParserFactory parserFactor = SAXParserFactory.newInstance();

			SAXParser parser = parserFactor.newSAXParser();
			SAXHandler handler = new SAXHandler();

			
			String s = newfile.getName().substring(0, ((newfile.getName().length()) - 4));

			System.out.println("newfile: "+newfile.getName());
			System.out.println("newfile2:"+s);
			
			File del_file = new File("/home/adeshgupta/src/grobid/out/" + s + ".tei.xml");

			if(!del_file.exists())
			{
				System.out.println("no TEI!");
				Thread.sleep(60000);
				del_file.delete();
				continue;
			}
			parser.parse(del_file, handler);
			authorlist = new ArrayList<>();
			authorlist = (ArrayList<String>) handler.authorList;
			

			System.out.println(authorlist.toString());

			// String new_cmd="python
			// /home/adeshgupta/scholar.py-master/scholar.py -c 1 --csv-header
			// --author \""+authorlist.get(0)+"\" >
			// /home/adeshgupta/firstfilegrobid.csv";
			if(authorlist.size()==0)
			{
				System.out.println("no authors found....!");
				Thread.sleep(60000);
				del_file.delete();
				continue;
				
			}
			for(String nm:authorlist)
			{
				System.out.println("auth: "+nm);
			}
			String new_cmd = "python /home/adeshgupta/scholar.py-master/scholar.py -c 20 --csv-header --author "
					+ authorlist.get(0) + " > /home/adeshgupta/firstfilegrobid.csv";
			ProcessBuilder proc = new ProcessBuilder("bash", "-c", new_cmd);
			Process process2 = proc.start();
			int errCode2 = process2.waitFor();
			System.out.println("Echo command executed, any errors? " + (errCode2 == 0 ? "No" : "Yes"));
			System.out.println("Echo Output:\n");

			File csvData = new File("/home/adeshgupta/firstfilegrobid.csv");
			CSVParser csvparser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);
			int i = 0;

		//FileWriter jsonwrite = new FileWriter(file.getAbsolutePath());
			String n = getDOIforFile(file.getAbsolutePath());
			System.out.println("");
			n = n.substring(19);
			System.out.println("n:" + n);

			FileWriter jsonwrite = new FileWriter("/home/adeshgupta/GrobidFiles/" + n+ ".json");

			for (CSVRecord csvRecord : csvparser) {
				if (true) {
					JSONObject obj = new JSONObject();

					obj.put("Type", "Grobid".toString());
					String val = "author: " + csvRecord.get(0) + ", year: " + csvRecord.get(2) + ", citations: "
							+ csvRecord.get(3) + ", num_ver: " + csvRecord.get(4);
					System.out.println("extracted: "+val);
					obj.put("Value", val);
					jsonwrite.write(obj.toJSONString());

					// need to add dis record to json
				}
				i++;
			}
			del_file.delete();

			jsonwrite.flush();
			// jsonwrite.close();

			/*
			 * //firing next command System.out.println("cluster id:"
			 * +clusterid); String cmd2=
			 * "python /home/adeshgupta/scholar.py-master/scholar.py --csv-header -C "
			 * +clusterid+" > /home/adeshgupta/finalcsv.csv"; ProcessBuilder
			 * proc9= new ProcessBuilder("bash","-c",cmd2); Process process5 =
			 * proc9.start(); int errCode5 = process5.waitFor();
			 * System.out.println("err:"+errCode5); System.out.println(
			 * "Echo command executed, any errors? " + (errCode5 == 0 ? "No" :
			 * "Yes")); System.out.println("Echo Output:\n"); File csvData2 =
			 * new File("/home/adeshgupta/finalcsv.csv"); CSVParser csvparser2 =
			 * CSVParser.parse(csvData2, Charset.defaultCharset(),
			 * CSVFormat.DEFAULT);
			 * 
			 * 
			 * for (CSVRecord csvRecord2 : csvparser2) {
			 * 
			 * System.out.println("url:"+csvRecord2.get(1));
			 * System.out.println("title:"+csvRecord2.get(2));
			 * 
			 * //need to add dis record to jso
			 * 
			 * }
			 * 
			 * 
			 * 
			 */
			// Writing to Json needs to be done

			System.out.println("done");

			System.out.println("---------------------------------------------------------------------------");

			jsonwrite.close();
			Thread.sleep(60000);

		}

	}

	private static String output(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + System.getProperty("line.separator"));
			}
		} finally {
			br.close();
		}
		return sb.toString();

	}

	private static String getDOIforFile(String path) throws ParseException, FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner scn = new Scanner(new File("Mapping_File.json"), "UTF-8");

		while (scn.hasNext()) {

			String nextLine = scn.nextLine();
			if (nextLine.startsWith("[") || nextLine.startsWith("]"))
				nextLine = scn.nextLine();

			JSONObject obj = (JSONObject) new JSONParser().parse(nextLine);
			String name = (String) obj.get("abs_path");
			if (name.equals(path)) {
				String DOI = (String) obj.get("DOI");
				// System.out.println("path is: "+path);
				return DOI;
			}
		}
		return null;
	}

}
