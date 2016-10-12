//CrossCorrelation Matrix

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class CrossCorelation {

	public static void main(String[] args) throws IOException {

		File directory = new File(args[1]);
		double my_array[] = null;
		CrossCorelation c = new CrossCorelation();
		try {
			my_array = c.readJSONFile(args[0]);
		} catch (FileNotFoundException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double correlationMatrix[][] = new double[256][256];
		double fileCorrelationMatrix[][] = new double[256][256];
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < 256; j++) {
				if (i == j) {
					correlationMatrix[i][j] = 0;
				} else if (i < j) {
					correlationMatrix[i][j] = Math.abs(my_array[i] - my_array[j]);
					correlationMatrix[j][i] = Math.pow(2.718,
							(-Math.pow(correlationMatrix[i][j], 2)) / (2 * Math.pow(0.0375, 2)));
					correlationMatrix[i][j] =correlationMatrix[j][i]; 
					
				} else {
					continue;
				}
			}
		}		
		
		double[] OCS = new double[256];
		byte[] barray = new byte[1024];
		int nGet;
		long PNF=0;
		double max=0;
	
		for(File file1:directory.listFiles())
		{
			double[] FileS = new double[256];
			double[] NCF = new double[256];
			RandomAccessFile store = null;
			try {
				store = new RandomAccessFile(file1, "rw");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileChannel channel = store.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int numOfBytesRead;
			try {
				while( (numOfBytesRead = channel.read(buffer))!=-1)
				{
					if ( numOfBytesRead == 0 )
						continue;
					buffer.position( 0 );
					buffer.limit( numOfBytesRead );
					while( buffer.hasRemaining( ) )
					{
						nGet = Math.min( buffer.remaining( ), 1024 );
						buffer.get( barray, 0, nGet );
						for ( int i=0; i<nGet; i++ )
						{
							int k=barray[i] & 0xff;
							FileS[ k ]++;
							if(max<FileS[k])
								max=FileS[k];
							
						}
					}
					buffer.clear( );    
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int k=0;k<256;k++)
			{
				// 1. Normalization
				FileS[k]=FileS[k]/max;
			
				// 	2. Companding Functions
				FileS[k]=Math.pow(FileS[k],1/1.5);
			}
				for (int i = 0; i < 256; i++)
				{
					for (int j = 0; j < 256; j++)
					{
						if (i == j)
						{
							fileCorrelationMatrix[i][j] = 0;
						} 
						else if (i < j) 
						{
							fileCorrelationMatrix[i][j] = Math.abs(FileS[i] - FileS[j]);
							fileCorrelationMatrix[j][i] = Math.pow(2.718,
									(-Math.pow(fileCorrelationMatrix[i][j], 2)) / (2 * Math.pow(0.0375, 2)));
							fileCorrelationMatrix[i][j] =fileCorrelationMatrix[j][i]; 
							
						}
						else 
						{
							continue;
						}
					}
				}

				for (int i = 0; i < 256; i++)
				{
					for (int j = 0; j < 256; j++)
					{
						if(i<j)
						{	
							correlationMatrix[i][j]=Math.abs(fileCorrelationMatrix[i][j]-correlationMatrix[i][j]);

						}
						else
						if(i>j)
						{
							correlationMatrix[i][j]=(fileCorrelationMatrix[i][j]+(PNF*correlationMatrix[i][j]))/(PNF+1);
						}
						
					
					}
				}
				
					
			PNF++;
		}
		
		
		
		
		FileWriter writer = new FileWriter("CrossCorrelationMatrix_JsonFiles\\"+directory.getName()+"_CrossCorrelation.csv");
		
		//writer.append(",");
		for(int  i=0;i<=256;i++)
		{
			if(i<256)
			writer.append(i+",");
			else
				writer.append(i+"");
		}
		for(int i=0;i<256;i++)
		{
			writer.append("\n"+i);
			for(int j=0;j<256;j++)
			{//if(i!=256)
				writer.append(","+correlationMatrix[i][j]);
			}
		}
		
		
		writer.close();
	}

	public double[] readJSONFile(String fileName) throws FileNotFoundException, ParseException {
		Scanner scn = new Scanner(new File(fileName), "UTF-8");
		ArrayList<JSONObject> json = new ArrayList<JSONObject>();

		// Reading and Parsing Strings to Json
		while (scn.hasNext()) {

			String nextLine = scn.nextLine();
			if (nextLine.startsWith("[") || nextLine.startsWith("]"))
				nextLine = scn.nextLine();
			nextLine = nextLine.substring(0, nextLine.length() - 1);
			System.out.println(nextLine);
			JSONObject obj = (JSONObject) new JSONParser().parse(nextLine);
			json.add(obj);
		}
		double[] OFPS = new double[256];
		// Here Printing Json Objects
		for (JSONObject obj : json) {
			int k = (int) (long) obj.get("byte");
			OFPS[k] = (double) obj.get("frequency");
		}
		System.out.println(OFPS.length);
		return OFPS;

	}

}
