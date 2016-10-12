/*Byte Frequency Cross co-relation*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class BFC 
{
	public double[] readJSONFile(String fileName) throws FileNotFoundException, ParseException
	{
		Scanner scn=new Scanner(new File(fileName),"UTF-8");
	    ArrayList<JSONObject> json=new ArrayList<JSONObject>();
	    
	    //Reading the finger-print for a specific MIME type from the JSON created in BFA analysis
	    while(scn.hasNext())
	    {
	    	
	    	String nextLine=scn.nextLine();
	    	if(nextLine.startsWith("[") || nextLine.startsWith("]"))
	    		nextLine=scn.nextLine();
	    	nextLine=nextLine.substring(0,nextLine.length()-1);
	    	System.out.println(nextLine);	
	        JSONObject obj= (JSONObject) new JSONParser().parse(nextLine);
	        json.add(obj);
	    }
	    double[] OFPS=new double[256];
	    //Printing Json Objects
	    for(JSONObject obj : json)
	    {
	        int k= (int)(long)obj.get("byte");    
			OFPS[k]=(double)obj.get("frequency");
	    }
	   System.out.println(OFPS.length);
	   return OFPS;

	}
	
	
	public static void main(String[] args) throws IOException
	{
		File directory= new File(args[0]);
		BFC bfc=new BFC();
		double OFPS[] = null;
		try
		{
			OFPS=bfc.readJSONFile(args[1]);
		} 
		catch (FileNotFoundException | ParseException e)
		{
		
			e.printStackTrace();
		}
		
		double[] OCS = new double[256];
		byte[] barray = new byte[1024];
		int nGet;
		long PNF=0;
		double max=0;
	
		
		{
			PNF = 0;
		File directory1=new File(args[0]);
			String fileName=directory1.getName();
			
			for(File file:directory1.listFiles())
			{
				double[] FileS = new double[256];
				double[] NCF = new double[256];
				RandomAccessFile store = null;
				try {
					store = new RandomAccessFile(file, "rw");
				} catch (FileNotFoundException e) {
		
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
				
					double x=Math.abs(FileS[k]-OFPS[k]);
					System.out.println("x="+x);
					// 4. Correlation factor
					NCF[k]=Math.pow(2.718,(-Math.pow(x,2))/(2*Math.pow(0.0375,2)));
					
					// 5. Combine the correlation strength
					OCS[k]=(NCF[k]+(PNF*OCS[k]))/(PNF+1);
				}
				PNF++;
			}
		}
		Arrays.toString(OCS);
		
		
		//Writing to the JSON file
		FileWriter newFile=new FileWriter("BFC_CorrelationStrength_JsonFiles\\"+directory.getName()+"_BFC.json");
		newFile.write("[\n");
		for(int i=0;i<256;i++)
		{
			JSONObject obj = new JSONObject();
			obj.put("byte",new Integer(i));
			obj.put("correlation",new Double(OCS[i]));
			newFile.write(obj.toJSONString());
			if(i!=255)
				newFile.write(",\n");
		}
		newFile.write("]");
		newFile.flush();
		newFile.close();
	}

	
}
