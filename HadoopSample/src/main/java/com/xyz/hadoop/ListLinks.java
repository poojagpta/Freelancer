package com.xyz.hadoop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListLinks {
	public static void main(String[] args) throws Exception {
		String url = "http://www1.ncdc.noaa.gov/pub/data/noaa/1992/";
		String fileDirectory = "/home/hduser/dev/data/ClimateData/1992/";
		print("Fetching %s...", url);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"), link.text());
			String urltext = link.attr("abs:href");

			if (!("/").equals(urltext.charAt(urltext.length() - 1) + "")) {
				URL urlObj = new URL(urltext);
				File fileObj = new File(fileDirectory + link.text());
				//Configuration conf = HBaseConfiguration.create();
				 Boolean testB = fileObj.createNewFile();

				InputStream webIS = urlObj.openConnection().getInputStream();

				FileOutputStream fo = new FileOutputStream(fileObj);
				int c = 0;
				do {
					c = webIS.read();
					//System.out.println("==============> " + c);
					if (c != -1){
						fo.write((byte) c);						
					}
						
					
				} while (c != -1);

				//Put data to HBase data source
				//addRecord(conf,"ext_faa",link.text(),"name","info",getStringFromInputStream(webIS));
				
				webIS.close();
				fo.close();

			}
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}
	
	// convert InputStream to String
		private static String getStringFromInputStream(InputStream is) {
	 
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
		}

	
	
	/**
	 * Put (or insert) a row
	 */
	public static void addRecord(Configuration conf,String tableName, String rowKey,
			String family, String qualifier, String value) throws Exception {
		HTable table = null;
		try {
			table = new HTable(conf, tableName);
			Put put = new Put(Bytes.toBytes(rowKey));
			put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
					Bytes.toBytes(value));
			table.put(put);
			System.out.println("insert recored " + rowKey + " to table "
					+ tableName + " ok.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			table.close();
		}
	}

}