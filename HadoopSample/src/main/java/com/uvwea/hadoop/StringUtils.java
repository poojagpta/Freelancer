package com.uvwea.hadoop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class StringUtils {

	private static final Log log = LogFactory.getLog(StringUtils.class);

	public static boolean isValid(final String string) {
		return string != null && !string.isEmpty() && !string.trim().isEmpty();
	}

	public static String getSKU(final String skuId) {
		String sku = null;
		String[] articleSku = skuId.split("\\-");
		sku = articleSku[0].replaceAll("\"", "");
		return sku;
	}

	public static long getDateLong(String dateStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		date = format.parse(dateStr);
		log.debug("getDateLong method--->"+date.getTime());
		return date.getTime();
	}

	public static String getDate(String dateStr) throws ParseException {
		// SimpleDateFormat format = new SimpleDateFormat(
		// "yyyy-MM-dd'T'HH:mm:ss'Z'");
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		// format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0,
		// "IST")));
		Date date = null;
		date = format.parse(dateStr);
		return format.format(date);
	}

	public static String getDateStr(long dateStr) throws ParseException {
		// SimpleDateFormat format = new SimpleDateFormat(
		// "yyyy-MM-dd'T'HH:mm:ss'Z'");
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		// format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0,
		// "IST")));
		Date date = new Date(dateStr);
		return format.format(date);
	}

}
