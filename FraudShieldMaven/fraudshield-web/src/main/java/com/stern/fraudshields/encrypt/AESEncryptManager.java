package com.stern.fraudshields.encrypt;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESEncryptManager {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = 
       new byte[] {'T','h','e','B','e','s','t','K','e','p','t','F','a','i','t','h'};
   

public static String encrypt(String Data) throws Exception {
       Key key = generateKey();
       Cipher c = Cipher.getInstance(ALGO);
       c.init(Cipher.ENCRYPT_MODE, key);
       byte[] encVal = c.doFinal(Data.getBytes());
       String encryptedValue = Base64.encodeBase64String(encVal);
       return encryptedValue;
   }

   public static String decrypt(String seed, String encrypted) throws Exception {
	   byte[] keyb = seed.getBytes("UTF-8");
	   //MessageDigest md = MessageDigest.getInstance("MD5");
	   //byte[] thedigest = md.digest(keyb);
	   SecretKeySpec skey = new SecretKeySpec(keyb,"AES/CBC/PKCS5Padding"); //thedigest, 
	   Cipher dcipher = Cipher.getInstance(ALGO); //"AES/ECB/PKCS7Padding"
	   dcipher.init(Cipher.DECRYPT_MODE, skey);

	   byte[] clearbyte = dcipher.doFinal(Base64.decodeBase64(encrypted));
	   return new String(clearbyte);
	 }
   
   
   public static byte[] toByte(String hexString) {
	   int len = hexString.length()/2;
	   byte[] result = new byte[len];
	   for (int i = 0; i < len; i++)
	     result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
	   return result;
	 }
   
   private static Key generateKey() throws Exception {
       Key key = new SecretKeySpec(keyValue, ALGO);
       return key;
   }

}
