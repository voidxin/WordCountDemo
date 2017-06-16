package com.voidxin.hadoop;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class VXWordCountMapper extends Mapper<LongWritable,Text , Text, Text> {
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String keyStr = key.toString();
		context.write(new Text(keyStr), new Text(line));
	}

}
