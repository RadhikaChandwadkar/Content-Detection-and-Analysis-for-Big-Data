//Code for creating the CSV file needed by the R program that makes the trained model
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Content_Detection {

	public static void main(String[] args) throws IOException {
		
		FileWriter writer= new FileWriter(new File("my_test_258.csv")); //name and path of the output file
		
		
		for(int i=1;i<258;i++)
		{
				writer.append("V"+i+",");
		}
		writer.append("label");
		
		long file_cnt=1;
		
		//input directory for positive examples
		File directory= new File("C:\\Classified_Dataset\\EXTRA_CREDIT\\Tila_CD_test\\positive"); 
		for(File file1:directory.listFiles())
		{
			System.out.println("file:"+file_cnt);
			byte[] barray = new byte[1024];
			int nGet;
			double max=0;
			double[] FileS = new double[256];
			double[] NCF = new double[256];
			RandomAccessFile store = new RandomAccessFile(file1.getAbsolutePath(), "rw");
			FileChannel channel = store.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int numOfBytesRead;
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
						
					}
					
				}
				
				buffer.clear( );
				
				
			}//while
			writer.append("\n"+file_cnt);
			file_cnt++;
			for(int i=0;i<256;i++)
			{
				writer.append(","+FileS[i]);
			}
			writer.append(",1");
		}//for
		
		
		//input directory for negative examples
		directory= new File("C:\\Classified_Dataset\\EXTRA_CREDIT\\Tila_CD_test\\negative"); 
		
		
		for(File file1:directory.listFiles())
		{
			System.out.println("file:"+file_cnt);
			byte[] barray = new byte[1024];
			int nGet;
			double max=0;
			double[] FileS = new double[256];
			double[] NCF = new double[256];
			RandomAccessFile store = new RandomAccessFile(file1.getAbsolutePath(), "rw");
			FileChannel channel = store.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int numOfBytesRead;
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
						
					}
					
					
				}
				
				buffer.clear( );
			
				
			}//while
			writer.append("\n"+file_cnt);
			file_cnt++;
			for(int i=0;i<256;i++)
			{
				writer.append(","+FileS[i]);
			}
			writer.append(",0");
		}//for

		writer.close();
	}//main
}//class
