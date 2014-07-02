package com.xyz.hadoop;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.log4j.Logger;

public class SequenceFileTest {

	public static Logger log = Logger.getLogger(SequenceFileTest.class);

	public static class Map extends
			Mapper<LongWritable, Text, LongWritable, Text> {

		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			System.out.println("Key : " + key.toString());
			context.write(key, value);
		}
	}

	public static void main(String[] args) throws Exception {

		if (args.length < 2) {
			log.error("Provide input file/output directory to the program");
			System.exit(1);
		}

		Configuration conf = new Configuration();

		Job job = new Job(conf, "WordCount");
		job.setJarByClass(SequenceFileTest.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(Map.class);

		job.setOutputFormatClass(SequenceFileOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.waitForCompletion(true);
	}
}
