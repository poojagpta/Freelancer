package com.quicksale.util;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

public class AppUtil {
	private static final Logger log = Logger.getLogger(AppUtil.class.getName());
	public static Date calculateDate(int days){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
	public static Integer parseInteger(String s){
		int i=0;
		try{
			i = Integer.parseInt(s);
		}catch(NumberFormatException e){log.warning(e.getMessage());}
		return  new Integer(i);
	}
	
}
