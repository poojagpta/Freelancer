package com.xyz.SecondarySort;


import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class CompositeGroupingComparator extends WritableComparator{

	public CompositeGroupingComparator() {
        super(CompositeKey.class, true);
    }
    @Override
    public int compare(WritableComparable tp1, WritableComparable tp2) {
    	CompositeKey temperaturePair = (CompositeKey) tp1;
    	CompositeKey temperaturePair2 = (CompositeKey) tp2;
        return temperaturePair2.getYearMonth().compareTo(temperaturePair.getYearMonth());
    }
	

}
