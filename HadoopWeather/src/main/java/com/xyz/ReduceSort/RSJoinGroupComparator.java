package com.xyz.ReduceSort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class RSJoinGroupComparator extends WritableComparator{

	protected RSJoinGroupComparator() {
		super(RSJoinCompositeKey.class,true);
		
	}
	@Override
	public int compare(WritableComparable w1, WritableComparable w2) {
		// The grouping comparator is the joinKey (Employee ID)
		RSJoinCompositeKey key1 = (RSJoinCompositeKey) w1;
		RSJoinCompositeKey key2 = (RSJoinCompositeKey) w2;
		return key1.getPlaceId().compareTo(key2.getPlaceId());
	}
}
