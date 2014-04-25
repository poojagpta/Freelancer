package com.uvwea.hadoop.time;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.uvwea.hadoop.StringUtils;

public class UserDateMapper extends Mapper<LongWritable, Text, UserDate, Text> {

	private static final Log log = LogFactory.getLog(UserDateMapper.class);

	private UserDate compositeKey = new UserDate();
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
			log.debug(token[1] + " : " + token[5] + " : "
					+ token[8]);
			long date = 0;
			try {
				date = StringUtils.getDateLong(token[1]);
			} catch (ParseException e) {
				System.err.println(token[1] + " : " + token[5] + " : "
						+ token[8]);
				System.err.println(e.getMessage());
			}
			if (date != 0) {
				compositeKey.setDatetime(date);
				compositeKey.setUserId(token[5]);

				valueItem.set(token[8]);
				log.debug(token[5] + " : " + token[8] + " : " + date);
				context.write(compositeKey, valueItem);
			}
		}
	}

}
