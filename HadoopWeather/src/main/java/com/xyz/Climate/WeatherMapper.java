package com.xyz.Climate;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class WeatherMapper extends
		Mapper<LongWritable, Text, IntWritable, IntWritable> {

	private static final int MISSING = 9999;
	public static Logger log = Logger.getLogger(WeatherMapper.class);

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		log.info("Start of method map");
		String line = value.toString();
		int year = Integer.parseInt(line.substring(15, 19));
		int airTemperature;
		if (line.charAt(87) == '+') { // parseInt doesn't like leading plus
										// signs
			airTemperature = Integer.parseInt(line.substring(88, 92));
		} else {
			airTemperature = Integer.parseInt(line.substring(87, 92));
		}
		String quality = line.substring(92, 93);
		if (airTemperature != MISSING && quality.matches("[01459]")) {
			context.write(new IntWritable(year),
					new IntWritable(airTemperature));
		}

		log.info("End of method map");
	}

}
