package com.xyz.ReduceSort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class RSJoinDriver {

	public static void main(String[] args) throws Exception{
		
		// TODO Auto-generated method stub
				Configuration conf = new Configuration();
				
				
				args = new GenericOptionsParser(conf, args).getRemainingArgs();
				
				final Job job = new Job(conf,
						"Record the temperature.");

				
				job.setJarByClass(RSJoinDriver.class);
				conf=job.getConfiguration();
				job.setMapperClass(RSJoinMapper.class);		
				job.setReducerClass(RSJoinReducer.class);
				job.setPartitionerClass(RSJoinPartitioner.class);
				//job.setSortComparatorClass(CompositeKey.class);
				job.setGroupingComparatorClass(RSJoinGroupComparator.class);

				//Output format from mapper/reducer
				job.setMapOutputKeyClass(RSJoinCompositeKey.class);
				job.setMapOutputValueClass(Text.class);
				job.setOutputKeyClass(NullWritable.class);
				job.setOutputValueClass(Text.class);

				//Input format to mapper and output foramt from reducer
				job.setInputFormatClass(TextInputFormat.class);
				job.setOutputFormatClass(TextOutputFormat.class);

				/*
				DistributedCache
				.addCacheArchive(
						new URI(
								"/user/akhanolk/joinProject/data/departments_map.tar.gz"),
						conf);*/
				
				conf.setInt("part-ph", 1);// Set Photo file to 1
				conf.setInt("part-pl", 2);// Set Place file to 2
				conf.setInt("part-sh", 3);// Set Historical salary file to 3
				
				
				StringBuilder inputPaths = new StringBuilder();
				inputPaths.append("/home/hduser/dev/UWEAWeather/data/ReducerData/part-ph.txt").append(",")
						.append("/home/hduser/dev/UWEAWeather/data/ReducerData/part-pl.txt");
				
				//Setting path of input file and output file
				FileInputFormat.setInputPaths(job, new Path(inputPaths.toString()));
				FileOutputFormat.setOutputPath(job, new Path("/home/hduser/dev/data/output"));
				
				//if(Files.deleteIfExists(new File(args[1]).toPath())){
				  System.exit(job.waitForCompletion(true) ? 0 : 1);
				//}

	}
}
