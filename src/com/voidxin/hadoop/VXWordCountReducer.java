package com.voidxin.hadoop;

import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSClient.Conf;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class VXWordCountReducer extends Reducer<Text, Text, Text, IntWritable> {
	protected void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		int count = 0;
		
		String targetStr = "voidxin";
		for (Text textWritable : values) {
	    	
	    	@SuppressWarnings("resource")
			Scanner scan = new Scanner(textWritable.toString()).useDelimiter(" |,|\\?|\\.|'");
	    	while(scan.hasNext()) {
	    		if(scan.next().equals(targetStr)) {
	    			count ++;
	    		}
	    	}
			
		}

		context.write(key, new IntWritable(count));

	}

}
