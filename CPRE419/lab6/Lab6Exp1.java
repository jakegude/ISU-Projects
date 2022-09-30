import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class Lab6Exp1 {
	private final static int numOfReducers = 2;

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		
		// Check the number of arguments being passed
		if (args.length != 2) {
			System.err.println("Usage: Github <input> <output>");
			System.exit(1);
		}
		
		// Set configuration and context
		SparkConf sparkConf = new SparkConf().setAppName("Lab6Exp1 in Spark");
		JavaSparkContext context = new JavaSparkContext(sparkConf);
		
		// Get the input file
		JavaRDD<String> inputFile = context.textFile(args[0]);
		
		// Produce line by line String
		JavaRDD<String> gitHubCsv = inputFile.flatMap(new FlatMapFunction<String, String>() {
			public Iterator<String> call(String s) {
				return Arrays.asList(s.split("\n")).iterator();
			}
		});
		
		// Generate JavaPairRDD that would have language as the key
		JavaPairRDD<String, String> gitHubPairRdd = gitHubCsv.mapToPair(new PairFunction<String, String, String>() {
			public Tuple2<String, String> call(String s) {
				String[] lineSplit = s.split(",");
				String language = lineSplit[1];
				String repo = lineSplit[0];
				String stars = lineSplit[12];
				
				return new Tuple2<String, String>(language, repo + " " + stars);
			}
		});
		
		// Generate JavaPairRDD that grouped by language
		JavaPairRDD<String, Iterable<String>> groupedRdd = gitHubPairRdd.groupByKey();
		
		// Generate new log file that would have the format as asked
		JavaRDD<String> newLog = groupedRdd.map(keyValue -> {
			String getLanguage = keyValue._1();
			Iterable<String> getValue = keyValue._2();
			
			int repoCount = 0;
			int maxRate = -1;
			String champRepo = "";
			
			for(String value: getValue) {
				String[] valueSplit = value.split(" ");
				String repoName = valueSplit[0];
				int repoRate = Integer.valueOf(valueSplit[1]);
				
				if(repoRate >= maxRate) {
					maxRate = repoRate;
					champRepo = repoName;
				}
				
				repoCount++;
			}
			
			String retVal = getLanguage + " " + Integer.toString(repoCount) + " " + champRepo + " " + Integer.toString(maxRate);
			
			return retVal;
		});
		
		// Generate new JavaPairRDD that would have repository count as the key
		JavaPairRDD<Integer, String> newLogPair = newLog.mapToPair(new PairFunction<String, Integer, String>() {
			public Tuple2<Integer, String> call(String s) {
				String[] lineSplit = s.split(" ");
				String language = lineSplit[0];
				int repoCount = Integer.valueOf(lineSplit[1]);
				String champRepo = lineSplit[2];
				String highestRate = lineSplit[3];
				
				
				return new Tuple2<Integer, String>(repoCount, language + " " + champRepo + " " + highestRate);
			}
		});
		
		// Sort the log by the key in descending manner
		JavaPairRDD<Integer, String> sortedLog = newLogPair.sortByKey(false);
		
		// Generate final format of the log file
		JavaRDD<String> finalLog = sortedLog.map(line -> {
			String repoCount = Integer.toString(line._1());
			String[] lineSplit = line._2().split(" ");
			String language = lineSplit[0];
			String champRepo = lineSplit[1];
			String highestRate = lineSplit[2];
			
			return language + " " + repoCount + " " + champRepo + " " + highestRate;
			
		});
		
		finalLog.saveAsTextFile(args[1]);
		context.stop();
		context.close();
		
	}

}
