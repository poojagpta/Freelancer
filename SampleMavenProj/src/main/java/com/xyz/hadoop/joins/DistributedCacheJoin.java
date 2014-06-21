package com.xyz.hadoop.joins;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * 
 * @author pooja
 * 
 *         1.
 * 
 */
public class DistributedCacheJoin {

	public static class photoMapper extends
			Mapper<LongWritable, Text, Text, Text> {

		Path[] cachefiles = new Path[0]; // To store the path of lookup files
		List places = new ArrayList();// To store the data of lookup files

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.map(key, value, context);
		}

		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			Configuration conf = context.getConfiguration();

			try {

				cachefiles = DistributedCache.getLocalCacheFiles(conf);
				BufferedReader reader = new BufferedReader(new FileReader(
						cachefiles[0].toString()));

				String line;

				while ((line = reader.readLine()) != null) {
					places.add(line); // Data of lookup files get stored in
											// list object
				}

			}

			catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
