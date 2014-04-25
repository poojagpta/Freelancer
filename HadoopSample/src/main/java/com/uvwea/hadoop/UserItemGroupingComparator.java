package com.uvwea.hadoop;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class UserItemGroupingComparator extends WritableComparator {

    protected UserItemGroupingComparator() {
            super(UserItem.class, true);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public int compare(WritableComparable w1, WritableComparable w2) {

    	UserItem key1 = (UserItem) w1;
    	UserItem key2 = (UserItem) w2;

            // (check on UserId)
            return key1.getUserId().compareTo(key2.getUserId());
    }

}
