import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;
import org.apache.hadoop.util.GenericOptionsParser;


public class customSort {
	
	public static void main(String[] args) throws Exception{ 
	
		///////////////////////////////////////////////////
		///////////// First Round MapReduce ///////////////
		////// where you might want to do some sampling ///
		///////////////////////////////////////////////////
		String input = "/lab3/input-500k";
		String temp = "/lab3/temp/";
		String output = "/lab3/output/";
		
		int reduceNumber = 3;
		
		Configuration conf = new Configuration();		
		
		//String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		
//		if (otherArgs.length != 2) {
//			System.err.println("Usage: Patent <in> <out>");
//			System.exit(2);
//		}
		
		Job job = Job.getInstance(conf, "Exp2");
		
		job.setJarByClass(customSort.class);
		job.setNumReduceTasks(reduceNumber);
	
		job.setMapperClass(mapOne.class);
		//job.setCombinerClass(combinerOne.class);
		job.setReducerClass(reduceOne.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setInputFormatClass(KeyValueTextInputFormat.class); 
		job.setOutputFormatClass(TextOutputFormat.class);
		
		
		FileInputFormat.addInputPath(job, new Path(input));

		FileOutputFormat.setOutputPath(job, new Path(temp));
		
		job.waitForCompletion(true);
		
		
		
		///////////////////////////////////////////////////
		///////////// Second Round MapReduce //////////////
		///////////////////////////////////////////////////
		Job job_two = Job.getInstance(conf, "Round Two");
        job_two.setJarByClass(customSort.class);
        
        // Providing the number of reducers for the second round
        job_two.setNumReduceTasks(reduceNumber);

        // Should be match with the output datatype of mapper and reducer
        job_two.setMapOutputKeyClass(Text.class);
        job_two.setMapOutputValueClass(Text.class);
         
        job_two.setOutputKeyClass(Text.class);
        job_two.setOutputValueClass(Text.class);
      
         
        job_two.setMapperClass(mapTwo.class);
        job_two.setReducerClass(reduceTwo.class);
        
        
        // Partitioner is our custom partitioner class
        job_two.setPartitionerClass(MyPartitioner.class);
        Configuration conf1 = job_two.getConfiguration();
		String partitionFile = TotalOrderPartitioner.getPartitionFile(conf1);
		URI partitionUri = new URI(partitionFile);
		job_two.addCacheFile(partitionUri);
        
        // Input and output format class
        job_two.setInputFormatClass(KeyValueTextInputFormat.class);
        job_two.setOutputFormatClass(TextOutputFormat.class);
         
        // The output of previous job set as input of the next
        FileInputFormat.addInputPath(job_two, new Path(temp));
        FileOutputFormat.setOutputPath(job_two, new Path(output));
         
        // Run the job
 		System.exit(job_two.waitForCompletion(true) ? 0 : 1);
		
	}
	
	public static class mapOne extends Mapper<Text, Text, IntWritable, Text> {
		private Text k  = new Text();
		private Text v = new Text();
		
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			// The TextInputFormat splits the data line by line.
			// So each map method receives one line from the input
			// Implement your functionality to split input into key and value
			String line = value.toString();
			StringTokenizer tokens = new StringTokenizer(line);
			k.set(tokens.nextToken());
			v.set(tokens.nextToken());
			context.write(k, v);
		} 
	}
	
	public static class reduceOne extends Reducer<IntWritable, Text, Text, NullWritable> {
		private Text word = new Text();

		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			
			for(Text val:values)
			{
				word.set(val.toString());
				context.write(key, word);
			}
		} 
	} 
	
	// Compare each input key with the boundaries we get from the first round
	// And add the partitioner information in the end of values
	public static class mapTwo extends Mapper<Text, Text, Text, Text> {
		private Text k = new Text();
		private Text v = new Text();

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer tokens = new StringTokenizer(line);
			k.set(tokens.nextToken());
			v.set(tokens.nextToken());
			context.write(k, v);
		} 
	}
	
	public static class reduceTwo extends Reducer<Text, Text, Text, Text> {
     	private Text word = new Text();
		public void reduce(Text key, Iterable<Text> values, Context context)throws IOException, InterruptedException {
     		for(Text val:values) {
				word.set(val.toString());
				context.write(key, word);
			}
     	}
	 }
	
	// Extract the partitioner information from the input values, which decides the destination of data
	public static class MyPartitioner extends Partitioner<Text,Text>{
		
	   public int getPartition(Text key, Text value, int numReduceTasks){
		   return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
//		   String[] desTmp = value.toString().split(";");
//		   return Integer.parseInt(desTmp[1]);
		   
	   }
	}
}
