package com.xyz.hadoop.file;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;



public class DataMapperv2 extends
		Mapper<LongWritable, Text, DBOutputFileWritable, NullWritable> {

	protected void map(LongWritable id, Text value, Context ctx) {
		System.out.println("Input : "+ value);
		try {
//			String[] keys = value.toString().split("|");

//			for (String key : keys) {
				ctx.write(new DBOutputFileWritable(value.toString(), 1), NullWritable.get());
//			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
