package com.uvwea.hadoop.time;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.uvwea.hadoop.StringUtils;

public class UserDateReducer extends Reducer<UserDate, Text, Text, Text> {

	private static final Log log = LogFactory.getLog(UserDateReducer.class);

	private Text itemList = new Text();
	private Text userIdDate = new Text();

	@Override
	public void reduce(UserDate key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		String str = "";
		for (final Text val : values) {
			if (StringUtils.isValid(val.toString()))
				str = str + "," + val.toString();
		}
		str = str.replaceFirst(",", "");
		log.debug("######### " + str);
		log.debug("######### " + key.getUserId());
		String userIdDateStr = null;
		try {
			userIdDateStr = key.getUserId() + "\t"
					+ StringUtils.getDateStr(key.getDatetime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		itemList.set(str);
		userIdDate.set(userIdDateStr);
		context.write(userIdDate, itemList);
	}

}
