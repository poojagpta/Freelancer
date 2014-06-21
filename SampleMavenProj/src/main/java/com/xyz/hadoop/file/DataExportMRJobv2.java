package com.xyz.hadoop.file;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

public class DataExportMRJobv2 {

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

		job.setJarByClass(DataExportMRJobv2.class);
		job.setMapperClass(DataMapperv2.class);
		job.setNumReduceTasks(0);
		
		job.setMapOutputKeyClass(DBOutputFileWritable.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		
		job.setOutputFormatClass(DBOutputFormat.class);

		FileInputFormat.setInputPaths(job, new Path("C:\\dev\\freelancer\\log"));

		DBOutputFormat.setOutput(job, "outputv2",
				new String[] { "name", "count" });

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}


}
