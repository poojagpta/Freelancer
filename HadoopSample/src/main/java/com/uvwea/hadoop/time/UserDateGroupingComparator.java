package com.uvwea.hadoop.time;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class UserDateGroupingComparator extends WritableComparator {

	protected UserDateGroupingComparator() {
		super(UserDate.class, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable w1, WritableComparable w2) {

		UserDate key1 = (UserDate) w1;
		UserDate key2 = (UserDate) w2;

		// (check on UserId)
		return key1.getUserId().compareTo(key2.getUserId());
	}

}
