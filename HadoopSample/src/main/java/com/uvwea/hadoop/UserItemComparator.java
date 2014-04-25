package com.uvwea.hadoop;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class UserItemComparator extends WritableComparator {
	protected UserItemComparator() {
		super(UserItem.class, true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int compare(WritableComparable w1, WritableComparable w2) {

		UserItem key1 = (UserItem) w1;
		UserItem key2 = (UserItem) w2;

		// (first check on UserId)
		int compare = key1.getUserId().compareTo(key2.getUserId());

		if (compare == 0) {
			// only if we are in the same input group should we try and sort by
			// value (datetime)
			return key1.getDatetime().compareTo(key2.getDatetime());
			// return key2.getDatetime().compareTo(key1.getDatetime());
		}

		return compare;
	}

}
