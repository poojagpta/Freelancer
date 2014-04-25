package com.uvwea.hadoop;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UserItemMapper extends Mapper<LongWritable, Text, UserItem, Text> {

	private static final Log log = LogFactory.getLog(UserItemMapper.class);

	private UserItem compositeKey = new UserItem();
	private Text valueItem = new Text();

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		log.debug("key: " + key + " value: " + value);

		// item#,userId,time

		String[] token = value.toString().split("\\,");

		if (token.length > 3 && StringUtils.isValid(token[1])
				&& StringUtils.isValid(token[5])
				&& StringUtils.isValid(token[8])) {
			String date = null;
			try {
				date = StringUtils.getDate(token[1]);
			} catch (ParseException e) {
				System.err.println(token[1] + " : " + token[5] + " : "
						+ token[8]);
			}
			if (date != null) {
				compositeKey.setDatetime(date);
				compositeKey.setUserId(token[5]);

				valueItem.set(token[8]);
				log.debug(token[5] + " : " + token[8] + " : " + date);
				context.write(compositeKey, valueItem);
			}
		}
	}

}
