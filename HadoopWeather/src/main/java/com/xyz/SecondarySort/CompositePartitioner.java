package com.xyz.SecondarySort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class CompositePartitioner extends Partitioner<CompositeKey, NullWritable> {

	@Override
	public int getPartition(CompositeKey key, NullWritable val, int numPartitions) {
		return key.getYearMonth().hashCode()%numPartitions;
	}

	
}
