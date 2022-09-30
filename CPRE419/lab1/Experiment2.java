import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class Experiment2 {

	public static void main(String[] args) throws Exception {
		//The system configuration
		Configuration conf = new Configuration();
		
		//Get an instance of the file system
		FileSystem fs = FileSystem.get(conf);
		
		String path_name = "/lab1/bigdata";
		Path path = new Path(path_name);
		
		//The output data stream to write into
		FileChecksum file = fs.getFileChecksum(path);
		
		
		if(file != null) {
			System.out.println("Checksum: " + file.toString());
		} else {
			System.out.println("File Null");
		}
		
		fs.close();
	}
	
}
