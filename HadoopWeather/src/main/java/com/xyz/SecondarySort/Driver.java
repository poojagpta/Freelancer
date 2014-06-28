package com.xyz.SecondarySort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
import org.apache.log4j.Logger;

public class Driver {

	private static final Logger log =Logger.getLogger(Driver.class);
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		final Configuration conf = new Configuration();
		
		
		args = new GenericOptionsParser(conf, args).getRemainingArgs();
		
		final Job job = new Job(conf,
				"Record the temperature.");

		job.setJarByClass(Driver.class);

		job.setMapperClass(CompositeMapper.class);		
		job.setReducerClass(CompositeReducer.class);
		job.setPartitionerClass(CompositePartitioner.class);
		//job.setSortComparatorClass(CompositeKey.class);
		job.setGroupingComparatorClass(CompositeGroupingComparator.class);

		//Output format from mapper/reducer
		job.setMapOutputKeyClass(CompositeKey.class);
		job.setMapOutputValueClass(NullWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		//Input format to mapper and output foramt from reducer
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		//Setting path of input file and output file
		FileInputFormat.setInputPaths(job, new Path("/home/hduser/dev/UWEAWeather/data/SecondarySortData"));
		FileOutputFormat.setOutputPath(job, new Path("/home/hduser/dev/data/output"));
		
		//if(Files.deleteIfExists(new File(args[1]).toPath())){
		  System.exit(job.waitForCompletion(true) ? 0 : 1);
		//}
	}

}
