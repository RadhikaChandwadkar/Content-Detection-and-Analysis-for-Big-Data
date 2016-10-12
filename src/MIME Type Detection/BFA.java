/*Byte Frequency Analysis*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.json.simple.JSONObject;

public class BFA {

	static double OFPS[];
	static double OCS[];
	static long PNF = 0;

	public static void main(String[] args) throws IOException {

		File directory = new File(args[0]);

		{
			PNF = 0;
			OFPS = new double[256];
			OCS = new double[256];

			//Reading the directory that conatins the input files for a certain MIME type 
			File directory1 = new File(args[0]);
			File[] list = directory.listFiles();
			long my_num = 0;
			//reading all files one by one
			for (File file1 : list) {
				System.out.println(my_num++);
				byte[] barray = new byte[1024];
				int nGet;
				double max = 0;
				double[] FileS = new double[256];
				double[] NCF = new double[256];
				RandomAccessFile store = new RandomAccessFile(file1.getAbsolutePath(), "rw");
				FileChannel channel = store.getChannel();
				ByteBuffer buffer = ByteBuffer.allocate(1024);
				int numOfBytesRead;
				while ((numOfBytesRead = channel.read(buffer)) != -1) {
					if (numOfBytesRead == 0)
						continue;
					buffer.position(0);
					buffer.limit(numOfBytesRead);
					while (buffer.hasRemaining()) {
						nGet = Math.min(buffer.remaining(), 1024);
						buffer.get(barray, 0, nGet);
						for (int i = 0; i < nGet; i++) {
							int k = barray[i] & 0xff;
							FileS[k]++;
							if (max < FileS[k])
								max = FileS[k];

						}
					}
					buffer.clear();
				}

				for (int k = 0; k < 256; k++) {
					// 1. Normalization
					System.out.println("FileS:" + FileS[k]);
					if (max == 0)
						FileS[k] = 0;
					else
						FileS[k] = FileS[k] / max;

					// 2. Companding Functions
					FileS[k] = Math.pow(FileS[k], 1 / 1.5);


					// 3. Combining to Old fingerprint
					OFPS[k] = (FileS[k] + (PNF * OFPS[k])) / (PNF + 1);

					System.out.println(OFPS[k]);
				}
				PNF++;
			}
			
			//writing to the JSON file
			FileWriter newFile = new FileWriter("BFA_FingerPrint_JsonFiles\\" + directory.getName() + "_BFA.json");
			newFile.write("[\n");
			for (int i = 0; i < 256; i++) {
				JSONObject obj = new JSONObject();
				obj.put("byte", new Integer(i));
				obj.put("frequency", new Double(OFPS[i]));
				newFile.write(obj.toJSONString());
				if (i != 255)
					newFile.write(",\n");
			}
			newFile.write("]");
			newFile.flush();
			newFile.close();

		}
	}
}
