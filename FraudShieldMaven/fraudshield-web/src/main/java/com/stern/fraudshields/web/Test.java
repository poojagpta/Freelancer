package com.stern.fraudshields.web;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Test {

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws UnsupportedEncodingException 
	 */
	public static  void main(String[] args) throws ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		
		SecureRandom rnd = new SecureRandom();

        String text = "awString" ;
        byte[] textData = text.getBytes();

        IvParameterSpec iv = new IvParameterSpec("101112131415161718191a1b1c1d1e1f".getBytes("UTF-8")); //rnd.generateSeed(16)

        //KeyGenerator generator = KeyGenerator.getInstance("AES");
        //generator.init(128);
        SecretKey k =  new SecretKeySpec("000102030405060708090a0b0c0d0e0f".getBytes("UTF-8"), "AES"); //generator.generateKey();

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
       c.init(Cipher.ENCRYPT_MODE, k, iv);
        //byte[] someData = c.update(textData);
        byte[] data = c.doFinal(textData);

        System.out.println("E: " + data);

        //c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, k,iv);
        //c.update(someData);
        String encrypStr= new String(c.doFinal(data));

        System.out.println("R: " + encrypStr);
			
	}

	 public static String bytesToHex(byte[] data) {
	      if (data==null) {
	         return null;
	      } else {
	         int len = data.length;
	         String str = "";
	         for (int i=0; i<len; i++) {
	            if ((data[i]&0xFF)<16) str = str + "0" 
	               + java.lang.Integer.toHexString(data[i]&0xFF);
	            else str = str
	               + java.lang.Integer.toHexString(data[i]&0xFF);
	         }
	         return str.toUpperCase();
	      }
	   } 
	
}
