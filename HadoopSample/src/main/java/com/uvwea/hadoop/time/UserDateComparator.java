package com.uvwea.hadoop.time;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class UserDateComparator extends WritableComparator {
	protected UserDateComparator() {
		super(UserDate.class, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable w1, WritableComparable w2) {

		UserDate key1 = (UserDate) w1;
		UserDate key2 = (UserDate) w2;

		// (first check on UserId)
		int compare = key1.getUserId().compareTo(key2.getUserId());

		if (compare == 0) {
			// only if we are in the same input group should we try and sort by
			// value (datetime)
			return key1.getDatetime() == key2.getDatetime() ? 0 : (key1
					.getDatetime() == key2.getDatetime() ? -1 : 1);
		}

		return compare;
	}

}
