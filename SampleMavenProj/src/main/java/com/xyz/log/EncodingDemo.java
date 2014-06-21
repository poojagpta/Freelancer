package com.xyz.log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class EncodingDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Default Charset=" + Charset.defaultCharset());
		String defaultCharacterEncoding = System.getProperty("file.encoding");
		System.out.println("defaultCharacterEncoding by property: "
				+ defaultCharacterEncoding);
		
		//System.out.println(getDefaultCharEncoding());
		String message = "password";
		byte[] byteArray = message.getBytes();
		// for(byte b : byteArray)
		// System.out.println(b+" : "+(char) (b & 0xff)+" : "+(char) (b));
		System.out.println(byteArray.length);
		byteArray = message.getBytes("UTF-8");
		// for(byte b : byteArray)
		// System.out.println(b+" : "+(char) (b & 0xff)+" : "+(char) (b));
		System.out.println(byteArray.length);
		byteArray = message.getBytes("UTF-16");
		// for(byte b : byteArray)
		// System.out.println(b+" : "+(char) (b & 0xff)+" : "+(char) (b));
		System.out.println(byteArray.length);
	}

	public static String getDefaultCharEncoding() {
		byte[] bArray = { 'w' };
		InputStream is = new ByteArrayInputStream(bArray);
		InputStreamReader reader = new InputStreamReader(is);
		String defaultCharacterEncoding = reader.getEncoding();
		return defaultCharacterEncoding;
	}

}
