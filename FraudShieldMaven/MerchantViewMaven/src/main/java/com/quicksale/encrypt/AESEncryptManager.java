package com.quicksale.encrypt;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import sun.misc.*;
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

   public static String decrypt(String encryptedData) throws Exception {
       Key key = generateKey();
       Cipher c = Cipher.getInstance(ALGO);
       c.init(Cipher.DECRYPT_MODE, key);
       byte[] decordedValue = Base64.decodeBase64(encryptedData);
       byte[] decValue = c.doFinal(decordedValue);
       String decryptedValue = new String(decValue);
       return decryptedValue;
   }
   
   private static Key generateKey() throws Exception {
       Key key = new SecretKeySpec(keyValue, ALGO);
       return key;
   }

}
