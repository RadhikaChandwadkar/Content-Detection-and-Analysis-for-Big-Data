//File Header Trailer analysis
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.json.simple.JSONObject;

public class FHT {
	public static void main(String[] args) throws IOException {

		File directory = new File(args[0]); // directory
		int H = Integer.parseInt(args[1]);
		int T = Integer.parseInt(args[1]);
		double header_FP[][] = new double[H][256];
		double trailer_FP[][] = new double[T][256];
		long PNF=0;
		double header_OFPA[][]= new double[H][256];
		double trailer_OFPA[][]= new double[T][256];

		double header_OCS[][]= new double[H][256];
		double trailer_OCS[][]= new double[T][256];
long num=0;
		for (File file : directory.listFiles()) 
		{
			System.out.println("num= "+num++);
			int total_bytes = (int) file.length();

			if (H > total_bytes) // file is smaller than H and T
			{
				for (int i = total_bytes; i < H; i++) {
					for (int j = 0; j < 256; j++) {
						header_FP[i][j] = -1;
						trailer_FP[i][j] = -1;
					}
				}
			}

			// main computation
			byte[] barray = new byte[H];
			int nGet;
			double max = 0;
			double header_NCF[][]= new double[H][256];
			double trailer_NCF[][]= new double[T][256];

			RandomAccessFile store = new RandomAccessFile(file.getAbsolutePath(), "rw");
			FileChannel channel = store.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(H);
			int numOfBytesRead;

			// header computation
			double max_h=0;
			while ((numOfBytesRead = channel.read(buffer)) != -1) {
				if (numOfBytesRead == 0)
					continue;
				buffer.position(0);
				buffer.limit(numOfBytesRead);
				while (buffer.hasRemaining()) {
					nGet = Math.min(buffer.remaining(), H);
					buffer.get(barray, 0, nGet);
					
					for (int i = 0; i < nGet; i++) {
						int k = barray[i] & 0xff;
						header_FP[i][k]++;
						if(max_h<header_FP[i][k])
						{
							max_h=header_FP[i][k];
						}
					}
				}
				buffer.clear();
			}

		
			// trailer computation
			
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
			if((file.length()-T)<0)
				randomAccessFile.seek(0);
			else
				randomAccessFile.seek(file.length() - T); 
			randomAccessFile.read(barray, 0, T);
			double max_t=0;
			for (int i = 0; i < T; i++) 
			{
				int k = barray[i] & 0xff;
				trailer_FP[i][k]++;
				if(max_t<trailer_FP[i][k])
				{
					max_t=trailer_FP[i][k];
				}
			}

			
			//combining in fingerprint
			
			for(int i=0;i<H;i++)
			{
				for(int j=0;j<256;j++)
				{
					header_FP[i][j]=header_FP[i][j]/max_h;
					trailer_FP[i][j]=trailer_FP[i][j]/max_t;
					
					//calculate diff between earlier fingerprint and new array 
					double x=Math.abs(header_FP[i][j]-header_OFPA[i][j]);
					double y=Math.abs(trailer_FP[i][j]-trailer_OFPA[i][j]);
					
					//update fp
					header_OFPA[i][j]=((header_OFPA[i][j]*PNF)+header_FP[i][i])/(PNF+1);
					trailer_OFPA[i][j]=((trailer_OFPA[i][j]*PNF)+trailer_FP[i][i])/(PNF+1);
				
					
					// 4. Correlation factor
					//double numerator=Math.pow(x, b)
					header_NCF[i][j]=Math.pow(2.718,(-Math.pow(x,2))/(2*Math.pow(0.0375,2)));
					trailer_NCF[i][j]=Math.pow(2.718,(-Math.pow(y,2))/(2*Math.pow(0.0375,2)));
			
					
					// 5. Combine the corr=-elation strength
					header_OCS[i][j]=(header_NCF[i][j]+(PNF*header_OCS[i][j]))/(PNF+1);
					trailer_OCS[i][j]=(trailer_NCF[i][j]+(PNF*trailer_OCS[i][j]))/(PNF+1);
				}
			}
			
			PNF=PNF+1;
		}//for
		
		
		//Writing to CSV files
		
		FileWriter newFile_h=new FileWriter("FHT_JsonFiles\\"+directory.getName()+"_"+H+"_header.csv");
		FileWriter newFile_t=new FileWriter("FHT_JsonFiles\\"+directory.getName()+"_"+T+"_trailer.csv");

		for(int  i=0;i<=256;i++)
		{
			if(i<256)
				newFile_h.append(i+",");
			else
				newFile_h.append(i+"");
		}
		
		for(int  i=0;i<=256;i++)
		{
			if(i<256)
				newFile_t.append(i+",");
			else
				newFile_t.append(i+"");
		}
		for(int i=0;i<H;i++)
		{
			newFile_h.append("\n"+i);
			newFile_t.append("\n"+i);
			for(int j=0;j<256;j++)
			{
				newFile_h.append(","+header_OCS[i][j]);
				newFile_t.append(","+trailer_OCS[i][j]);
				
			
			}
		}
		
	//writing to csv file
		
		newFile_h.close();
		newFile_t.close();
	}
}