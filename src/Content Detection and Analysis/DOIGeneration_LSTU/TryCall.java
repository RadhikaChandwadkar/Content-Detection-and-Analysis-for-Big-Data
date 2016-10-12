import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TryCall {
	static FileWriter newFile;
	public static void main(String args[]) throws IOException, ParseException{
		File directory = new File(args[0]); 
		System.out.println(args[0]);//the directory path that contains the unclassified files
	//	newFile = new FileWriter("Mapping_File.json");
	newFile = new FileWriter("Mapping_File.json", true);
		//newFile.write("[\n");
		
		if (directory.isDirectory()) {
			processDirectory(directory);
		} else {
			process_File(directory);
		}
		
		//newFile.write("]");
		newFile.flush();
		newFile.close();

		

	}
	
//accessing the nested folders recursively
	private static void processDirectory(File directory) throws ClientProtocolException, IOException, ParseException {
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

	private static void process_File(File file) throws ClientProtocolException, IOException, ParseException {
		// TODO Auto-generated method stub
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/a");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("lsturl","http://0981.com/"+file.getAbsolutePath()));
		nvps.add(new BasicNameValuePair("format", "json"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response2 = httpclient.execute(httpPost);
		HttpEntity entity=response2.getEntity();
		BufferedReader br=new BufferedReader(new InputStreamReader(entity.getContent()));
		//System.out.println(br.readLine());
		String my_short_url= br.readLine();
		System.out.println(my_short_url);
		JSONObject obj= (JSONObject) new JSONParser().parse(my_short_url);
		String value=(String) obj.get("short");
		value="polar.usc.edu"+value;
		String newstring=value.substring(20);
		System.out.println("new str="+newstring);
		JSONObject obj1 = new JSONObject();
		obj1.put("DOI",newstring);
		obj1.put("abs_path",file.getAbsolutePath());
		newFile.write(obj1.toJSONString());
		newFile.write("\n");
		System.out.println(obj1.toString());
		
		
		System.out.println("value is:"+value);
		try {
			System.out.println(response2.getStatusLine());
			HttpEntity entity2 = response2.getEntity();
			
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity2);
		} finally {
			response2.close();
		}
	}
	
}

