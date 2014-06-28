package com.xyz.SecondarySort;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class CompositeKey implements WritableComparable<CompositeKey>{

	private String yearMonth;
	private String temperature;
	
	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	
	public int compareTo(CompositeKey temperaturePair) {
	        int compareValue = this.yearMonth.compareTo(temperaturePair.getYearMonth());
	        if (compareValue == 0) {
	            compareValue = -Integer.parseInt(this.temperature)+Integer.parseInt(temperaturePair.getTemperature());
	        }
	        return compareValue;
	    }

	public void readFields(DataInput datainput) throws IOException {
		yearMonth=datainput.readUTF();
		temperature=datainput.readUTF();
	}

	public void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeUTF(yearMonth);
		dataoutput.writeUTF(temperature);
		
	}
	
	 @Override
	    public boolean equals(Object ob) {
	            if (ob == null || this.getClass() != ob.getClass())
	                    return false;

	            CompositeKey k = (CompositeKey) ob;
	            if (k.yearMonth != null && this.yearMonth != null
	                            && !k.yearMonth.equals(this.yearMonth))
	                    return false;
	            if (k.temperature != null && this.temperature != null
	                            && !k.temperature.equals(this.temperature))
	                    return false;
	            return true;
	    }

	    @Override
	    public int hashCode() {
	            int result = yearMonth != null ? yearMonth.hashCode() : 0;
	            return 31 * result;

	    }

	    @Override
	    public String toString() {
	            return yearMonth + "\t" + temperature;
	    }
}
