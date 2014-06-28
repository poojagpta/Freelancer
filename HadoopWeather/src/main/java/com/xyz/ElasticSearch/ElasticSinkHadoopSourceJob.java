package com.xyz.ElasticSearch;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;

public class ElasticSinkHadoopSourceJob {

public static void main(String str[]) throws IOException, ClassNotFoundException, InterruptedException{
		
		Configuration conf = new Configuration();
		conf.set("es.resource", "movies/movie"); 
				
		final Job job = new Job(conf,
				"Get information from elasticSearch.");
		
		job.setJarByClass(ElasticSinkHadoopSourceJob.class);
		job.setMapperClass(ElasticSinkHadoopSourceMapper.class);
		
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(EsOutputFormat.class);
		job.setNumReduceTasks(0);
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(MapWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path("data/ElasticSearchData"));
				
		 System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
