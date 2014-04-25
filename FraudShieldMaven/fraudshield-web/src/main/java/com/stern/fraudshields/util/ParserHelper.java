package com.stern.fraudshields.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Text;

public class ParserHelper {

	public static Object toObject( @SuppressWarnings("rawtypes") Class clazz, String value ) throws ParseException {
	    if( Boolean.class == clazz ) return Boolean.parseBoolean(value);
	    if( Byte.class == clazz ) return Byte.parseByte(value.replace(" ", ""));
	    if( Short.class == clazz ) return Short.parseShort( value.replace(" ", "") );
	    if( Integer.class == clazz ) return Integer.parseInt( value.replace(" ", "") );
	    if( Long.class == clazz || long.class == clazz) return Long.parseLong( value.replace(" ", "") );
	    if( Float.class == clazz ) return Float.parseFloat( value.replace(" ", "") );
	    if( Double.class == clazz ) return Double.parseDouble( value.replace(" ", "") );
	    if(Date.class == clazz) 
	    {
	    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");	
	    	return df.parse(value);
	    }
	    if(Text.class == clazz) return new Text(value);	
	    if(Blob.class==clazz) return new Blob(value.getBytes());
	    return value;
	}
	
	public static String getMerchantName(String merchantid)
	{
		if(merchantid!=null && !("").equals(merchantid.trim()))
		{
			return merchantid.toLowerCase();
		}
		
		return null;
	}
	
	
}
