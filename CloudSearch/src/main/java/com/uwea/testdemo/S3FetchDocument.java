package com.uwea.testdemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class S3FetchDocument {

	private javax.crypto.spec.SecretKeySpec signingKey = null;
	private javax.crypto.Mac mac = null;
	private String keyId = null;

	public static void main(String str[]) {
		S3FetchDocument t = new S3FetchDocument();
      
		try {		
			t.setKeyId("AKIAIZUT5PW6K6NZLXOA");
			t.setKey("PZ7AM4nrFfNJjZ1TfTimVgP3gahsr9jJkddAEJ/C");
			t.fetchDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void fetchDocument() throws Exception {

		// S3 timestamp pattern.
		String fmt = "EEE, dd MMM yyyy HH:mm:ss ";
		SimpleDateFormat df = new SimpleDateFormat(fmt, Locale.US);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Data needed for signature
		String method = "GET";
		String contentMD5 = "";
		String contentType = "";
		String date = df.format(new Date()) + "GMT";
		String bucket = "/universalweatherbigdata/error.html";

		// Generate signature
		StringBuffer buf = new StringBuffer();
		buf.append(method).append("\n");
		buf.append(contentMD5).append("\n");
		buf.append(contentType).append("\n");
		buf.append(date).append("\n");
		buf.append(bucket);
		String signature = sign(buf.toString());

		
		// Connection to s3.amazonaws.com
        HttpURLConnection httpConn = null;
        URL url = new URL("http","s3.amazonaws.com",80,bucket);
		httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setDoInput(true);
        httpConn.setDoOutput(true);
        httpConn.setUseCaches(false);
        httpConn.setDefaultUseCaches(false);
        httpConn.setAllowUserInteraction(true);
        httpConn.setRequestMethod(method);
        httpConn.setRequestProperty("Date", date);
        httpConn.setRequestProperty("Content-Length", "0");
        String AWSAuth = "AWS " + keyId + ":" + signature;
        httpConn.setRequestProperty("Authorization", AWSAuth);
        // Send the HTTP PUT request.
        int statusCode = httpConn.getResponseCode();
        
        
        BufferedReader bufReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = bufReader.readLine()) != null) {
            out.append(line);
        }
        System.out.println(out.toString());   //Prints the string content read from input stream
        bufReader.close();
		
	}

	// This method creates S3 signature for a given String.
	public String sign(String data) throws Exception
	{
	  // Signed String must be BASE64 encoded.
	  byte[] signBytes = mac.doFinal(data.getBytes("UTF8"));
	  String signature = encodeBase64(signBytes);
	  return signature;
	}
	public String encodeBase64(byte[] data)
	{
		String base64 =  new sun.misc.BASE64Encoder().encodeBuffer(data);
		if (base64.endsWith("\r\n")) base64 = base64.substring(0,base64.length()-2);
		return base64;
	}
	
	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	// This method converts AWSSecretKey into crypto instance.
	public void setKey(String AWSSecretKey) throws Exception {
		mac = Mac.getInstance("HmacSHA1");
		byte[] keyBytes = AWSSecretKey.getBytes("UTF8");
		signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
		mac.init(signingKey);
	}
	
}
