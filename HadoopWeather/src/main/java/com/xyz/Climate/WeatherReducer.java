package com.xyz.Climate;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

public class WeatherReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable>{

	public static Logger log = Logger.getLogger(WeatherReducer.class);
	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> values,
			Context context)
			throws IOException, InterruptedException {
		
		log.info("Start of method reduce()");
		int max=Integer.MIN_VALUE;
		for(IntWritable value:values){
			max=Math.max(max, value.get());
		}
		
		log.info("End of message reduce");
		context.write(key, new IntWritable(max));
	}
	

}
