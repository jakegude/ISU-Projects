import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.flink.api.common.functions.FlatMapFunction;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.util.Collector;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

@SuppressWarnings("serial")
public class Lab7Exp1 {
        
    public static void main(String[] args) throws Exception {

        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        // <PATH_TO_DATA>: The path to input data, e.g., "/home/cpre419/Downloads/shakespeare"
        DataStream<String> text = env.readTextFile("/Users/jakegude/Desktop/Projects/Eclipse/cpre419lab7/shakespeare");

        DataStream<Tuple2<String, Integer>> counts =
              // split up the lines in pairs (2-tuples) containing: (word,1)
              text.flatMap(new Tokenizer()).keyBy(0).sum(1);
              // group by the tuple field "0" and sum up tuple field "1"

         // emit result
         counts.addSink(new CustomSink());
 
         env.execute();
    }  
    
    public static final class CustomSink extends RichSinkFunction<Tuple2<String, Integer>> {
    	
//    	int totalsum = 0;
    	Map<String, Integer> input = new HashMap<String, Integer>();
    	
    	public void invoke(Tuple2<String, Integer> s) throws Exception {
    		if (input.get(s.f0) != null) {
    			input.put(s.f0, input.get(s.f0) + 1);
    		} else {
    			input.put(s.f0, 1);
    		}
    	}
    	
    	@Override
    	public void close() throws IOException {
    		//sort and find top 10 based on freq
    		CustomSortAndPrintTop10(input);
    	}
    	
    	private static void CustomSortAndPrintTop10(Map<String, Integer> unsortedMap) {
			Set<Entry<String, Integer>> entrySet = unsortedMap.entrySet();
			List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(entrySet);
			Collections.sort(list, new Comparator<Entry<String, Integer>>() {	
				public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
					return obj1.getValue().compareTo(obj2.getValue());
				}
			});
			
			for (int i = list.size() - 1; i > list.size() - 11; i--) {
				System.out.println(list.get(i).getKey() + "\t" + list.get(i).getValue());
			}
    	}
    }

    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {

        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            // normalize and split the line
            String[] tokens = value.toLowerCase().split("\\W+");

            // emit the pairs
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }

}