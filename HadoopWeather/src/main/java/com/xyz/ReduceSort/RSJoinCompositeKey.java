package com.xyz.ReduceSort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;


public class RSJoinCompositeKey implements WritableComparable<RSJoinCompositeKey>{

	private String placeId;
	private int numsrc;
	
	
	
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	
	

	public int getNumsrc() {
		return numsrc;
	}

	public void setNumsrc(int numsrc) {
		this.numsrc = numsrc;
	}

	public void readFields(DataInput datainput) throws IOException {
		this.placeId=datainput.readUTF();
		this.numsrc=datainput.readInt();
		
	}

	public void write(DataOutput dataoutput) throws IOException {
		dataoutput.writeUTF(this.placeId);
		dataoutput.writeInt(this.numsrc);
	}

	public int compareTo(RSJoinCompositeKey o) {
			
		int c=this.placeId.compareTo(o.getPlaceId());
		
		if(c==0){
			return Integer.compare(this.numsrc,o.getNumsrc());
		}		
		
		return 0;
	}

	
}
