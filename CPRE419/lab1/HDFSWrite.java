

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;

public class HDFSWrite {
	
	public static void main(String[] args) throws Exception {
		//The system configuration
		Configuration conf = new Configuration();
		
		//Get an instance of the file system
		FileSystem fs = FileSystem.get(conf);
		
		String path_name = "/lab1/newfile";
		Path path = new Path(path_name);
		
		//The output data stream to write into
		FSDataOutputStream file = fs.create(path);
		
		//Write some data
		file.writeChars("the first hadoop program!");
		
		//close the file and file system
		file.close();
		fs.close();
	}
}
