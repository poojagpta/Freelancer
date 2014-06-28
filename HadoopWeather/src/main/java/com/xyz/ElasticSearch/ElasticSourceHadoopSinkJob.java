package com.xyz.ElasticSearch;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.elasticsearch.hadoop.mr.EsInputFormat;

public class ElasticSourceHadoopSinkJob {
	
	public static void main(String arg[]) throws IOException, ClassNotFoundException, InterruptedException{
		
		Configuration conf = new Configuration();
		conf.set("es.resource", "movies/movie"); 
		//conf.set("es.query", "?q=kill"); 
		
		final Job job = new Job(conf,
				"Get information from elasticSearch.");
		
		job.setJarByClass(ElasticSourceHadoopSinkJob.class);
		job.setMapperClass(ElasticSourceHadoopSinkMapper.class);
		
		
		job.setInputFormatClass(EsInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setNumReduceTasks(0);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(MapWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(arg[0]));
		
		 System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
