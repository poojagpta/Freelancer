package com.xyz.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

public class DataExportMRJob {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();

		conf.set("fs.file.impl",
				"com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");

		DBConfiguration.configureDB(conf, "com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test", "root", "root");

		Job job = new Job(conf);

		job.setJarByClass(DataExportMRJob.class);
		job.setMapperClass(DataMapper.class);
		job.setReducerClass(DataReducer.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(DBOutputWritable.class);
		job.setOutputValueClass(NullWritable.class);
		
		job.setInputFormatClass(DBInputFormat.class);
		job.setOutputFormatClass(DBOutputFormat.class);

		DBInputFormat.setInput(job, DBInputWritable.class, "studentinfo", null,
				null, new String[] { "id", "name" });
		
		DBOutputFormat.setOutput(job, "output",
				new String[] { "name", "count" });

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}


}
