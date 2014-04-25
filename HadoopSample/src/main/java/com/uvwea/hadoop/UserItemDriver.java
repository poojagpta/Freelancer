package com.uvwea.hadoop;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class UserItemDriver {

	private static final Log log = LogFactory.getLog(UserItemDriver.class);

	public static void main(String[] args) throws Exception {

		final Configuration conf = new Configuration();
				
	/*conf.set("fs.file.impl",
				"com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");
*/

		String envt = null;

		if (args.length > 0) {
			envt = args[0];
		} else {
			envt = "dev";
		}

		Properties prop = new Properties();

		try {
			envt = "dev";			
			// load a properties file from class path, inside static method
			prop.load(UserItemDriver.class.getClassLoader()
					.getResourceAsStream("config-" + envt + ".properties"));

		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		log.debug("Message : " + prop.getProperty("message"));

		log.debug("Conf: " + conf);

		args = new GenericOptionsParser(conf, args).getRemainingArgs();

		final Job job = new Job(conf,
				"Count the items from bugs bunny data on hdfs in \"input\" path.");

		job.setJarByClass(UserItemDriver.class);

		job.setMapperClass(UserItemMapper.class);
		
		//job.setCombinerClass(UserItemReducer.class);
		job.setReducerClass(UserItemReducer.class);

		job.setMapOutputKeyClass(UserItem.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		//FileInputFormat.setInputPaths(job, new Path("inputUserItem"));
		//FileOutputFormat.setOutputPath(job, new Path("output"));

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}
