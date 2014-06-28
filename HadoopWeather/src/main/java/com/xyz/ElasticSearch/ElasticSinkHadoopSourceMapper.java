package com.xyz.ElasticSearch;

import java.io.IOException;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ElasticSinkHadoopSourceMapper extends Mapper<LongWritable, Text, NullWritable, MapWritable>{

	@Override
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		
	    String[] splitValue=value.toString().split(",");
		MapWritable doc = new MapWritable();
		
		doc.put(new Text("year"), new IntWritable(Integer.parseInt(splitValue[0])));
		doc.put(new Text("title"), new Text(splitValue[1]));
		doc.put(new Text("director"), new Text(splitValue[2]));
		String genres=splitValue[3];
		
		if(genres!=null){
			String[] splitGenres=genres.split("\\$");		
			ArrayWritable genresList=new ArrayWritable(splitGenres);		
			doc.put(new Text("genres"), genresList);		
				
		}
		
		context.write(NullWritable.get(), doc);
	}
	

}
