package com.quicksale.encrypt;

import org.apache.commons.codec.binary.Base64;

public class QuickSaleBase64 {
	
	public static String encode(String value){
		byte[] encoded = Base64.encodeBase64(value.getBytes());
		return new String(encoded);
	}
	
	public static String decode(String code){
		byte[] decoded = Base64.decodeBase64(code.getBytes());
		return new String(decoded);
	}

}
