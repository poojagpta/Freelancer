package com.jbksoft.algorithm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class Demo {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		String string = "08/30/2012";
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		//format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "PST")));
		Date date = format.parse(string);
		System.out.println(date);
		System.out.println(date.getTime());
		date = format.parse("08/31/2012");
		System.out.println(date);
		System.out.println(date.getTime());
		System.out.println(new Date().getTime());
	}

}
