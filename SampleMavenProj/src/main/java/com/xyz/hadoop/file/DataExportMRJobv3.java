package com.xyz.hadoop.file;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

public class DataExportMRJobv3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();

		conf.set("fs.file.impl",
				"com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");

		DBConfiguration.configureDB(conf,
				"com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://HRDVFPWSW01.universalweather.rdn:1433", "sa",
				"Universal1!");

		Job job = new Job(conf);

		job.setJarByClass(DataExportMRJobv3.class);
		job.setMapperClass(DataMapperv2.class);
		job.setNumReduceTasks(0);

		job.setMapOutputKeyClass(DBOutputFileWritable.class);
		job.setMapOutputValueClass(NullWritable.class);

		job.setInputFormatClass(TextInputFormat.class);

		job.setOutputFormatClass(DBOutputFormat.class);

		FileInputFormat
				.setInputPaths(job, new Path("C:\\dev\\freelancer\\log"));

		DBOutputFormat.setOutput(job, "ext_faa", new String[] { "name",
				"count" });

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}
