package com.uvwea.hadoop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class UserItem implements WritableComparable<UserItem> {

    private String userId;
    private String datetime;

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
    public String getDatetime() {
            return datetime;
    }

    /**
     * @param datetime
     *            the datetime to set
     */
    public void setDatetime(String datetime) {
            this.datetime = datetime;
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
            userId = dataInput.readUTF();
            datetime = dataInput.readUTF();
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
            dataOutput.writeUTF(userId);
            dataOutput.writeUTF(datetime);
    }

    @Override
    public int compareTo(UserItem otherObject) {
            int cmp = this.userId.compareTo(otherObject.userId);
            if (cmp != 0) {
                    return cmp;
            }
            return this.datetime.compareTo(otherObject.datetime);
    }

    @Override
    public boolean equals(Object ob) {
            if (ob == null || this.getClass() != ob.getClass())
                    return false;

            UserItem k = (UserItem) ob;
            if (k.datetime != null && this.datetime != null
                            && !k.datetime.equals(this.datetime))
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
