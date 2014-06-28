package com.xyz.Climate;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;

public class WeatherJob extends Configured implements Tool{
	
	public static Logger log=Logger.getLogger(WeatherJob.class);
	public static void main(String args[]) throws Exception{
		 int res = ToolRunner.run(new Configuration(), new WeatherJob(), args);
	        System.exit(res);
		
		
	}
	public int run(String[] str) throws Exception {
		
		Configuration conf=this.getConf();
		conf.setInt(
				NLineInputFormat.LINES_PER_MAP, 500);
		Job job=new Job(conf,"Weather Job"); //new Configuration(), "WeatherJob"
		job.setJarByClass(WeatherJob.class);
		
		//Set Mapper and Reducer
		job.setMapperClass(WeatherMapper.class);
		job.setReducerClass(WeatherReducer.class);
		job.setInputFormatClass(NLineInputFormat.class);
				
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(str[0])); //
		FileOutputFormat.setOutputPath(job, new Path(str[1]));//str[1]
		
		
		return job.waitForCompletion(true)?0:1;
	}

}
