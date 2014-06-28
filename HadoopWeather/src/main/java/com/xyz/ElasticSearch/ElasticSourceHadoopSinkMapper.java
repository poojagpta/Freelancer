package com.xyz.ElasticSearch;

import java.io.IOException;

import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ElasticSourceHadoopSinkMapper extends Mapper<Object, MapWritable, Text, MapWritable> {

	@Override
	protected void map(Object key, MapWritable value,
			Context context)
			throws IOException, InterruptedException {
		
		context.write(new Text(key.toString()), value);
		
	}

	
}
