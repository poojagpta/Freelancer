package com.xyz.ReduceSort;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class RSJoinPartitioner  extends Partitioner<RSJoinCompositeKey, Text> {

	@Override
	public int getPartition(RSJoinCompositeKey key, Text val, int numReduceTask) {
		
		return key.getPlaceId().hashCode()%numReduceTask;
	}

}
