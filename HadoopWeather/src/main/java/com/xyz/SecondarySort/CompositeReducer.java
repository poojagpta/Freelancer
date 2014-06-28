package com.xyz.SecondarySort;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CompositeReducer extends Reducer<CompositeKey, NullWritable, Text, Text>{

	@Override
	protected void reduce(CompositeKey key, Iterable<NullWritable> valList,Context context)
			throws IOException, InterruptedException {
		
		context.write(new Text(key.getYearMonth()), new Text(key.getTemperature()));
	}

	@Override
	protected void setup(org.apache.hadoop.mapreduce.Reducer.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		super.setup(context);
	}

	
}
