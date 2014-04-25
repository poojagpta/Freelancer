package com.uvwea.hadoop.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class UserDate implements WritableComparable<UserDate> {

	private String userId;
	private long datetime;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the datetime
	 */
	public long getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime
	 *            the datetime to set
	 */
	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}

	@Override
	public void readFields(DataInput dataInput) throws IOException {
		userId = dataInput.readUTF();
		datetime = dataInput.readLong();
	}

	@Override
	public void write(DataOutput dataOutput) throws IOException {
		dataOutput.writeUTF(userId);
		dataOutput.writeLong(datetime);
	}

	@Override
	public int compareTo(UserDate otherObject) {
		int cmp = this.userId.compareTo(otherObject.userId);
		if (cmp != 0) {
			return cmp;
		} else if (this.datetime != otherObject.datetime) {
			return this.datetime < otherObject.datetime ? -1 : 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object ob) {
		if (ob == null || this.getClass() != ob.getClass())
			return false;

		UserDate k = (UserDate) ob;
		if (this.datetime == k.datetime)
			return false;
		if (k.userId != null && this.userId != null
				&& !k.userId.equals(this.userId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = userId != null ? userId.hashCode() : 0;
		return 31 * result;

	}

	@Override
	public String toString() {
		return userId + "\t" + datetime;
	}

}
