package com.uvwea.hadoop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class UserItemReducer extends Reducer<UserItem, Text, Text, Text> {

	private static final Log log = LogFactory.getLog(UserItemReducer.class);

	private Text itemList = new Text();
	private Text userIdDate = new Text();

	@Override
	public void reduce(UserItem key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		String str = "";
		for (final Text val : values) {
			if (StringUtils.isValid(val.toString()))
				str = str + "," + val.toString();
		}
		str = str.replaceFirst(",", "");
		log.debug("######### " + str);
		log.debug("######### " + key.getUserId());
		String userIdDateStr = key.getUserId()+"\t"+key.getDatetime(); 
		itemList.set(str);
		userIdDate.set(userIdDateStr);
		context.write(userIdDate, itemList);
	}

}
