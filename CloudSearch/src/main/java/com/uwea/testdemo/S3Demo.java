package com.uwea.testdemo;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class S3Demo {

	private javax.crypto.spec.SecretKeySpec signingKey = null;
	private javax.crypto.Mac mac = null;
	private String keyId = null;

	public static void main(String[] args) {
		S3Demo t = new S3Demo();
		try {
			t.setKeyId("YourAWSAccessKeyIDHere");
			t.setKey("YourAWSSecretKeyHere");
			t.createBucket();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createBucket() throws Exception
	{
        // S3 timestamp pattern.
		String fmt = "EEE, dd MMM yyyy HH:mm:ss ";
        SimpleDateFormat df = new SimpleDateFormat(fmt, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        // Data needed for signature
		String method = "PUT";
		String contentMD5 = "";
		String contentType = "";
		String date = df.format(new Date()) + "GMT";
		String bucket = "/onjava";

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
        if ((statusCode/100) != 2)
        {
        	// Deal with S3 error stream.
        	InputStream in = httpConn.getErrorStream();
        	String errorStr = getS3ErrorCode(in);
        	System.out.println("Error: "+errorStr);
        }
        else
        {
        	System.out.println("Bucket created successfully");
        }
	}

	public void setKeyId(String id)
	{
		this.keyId = id;
	}

	// This method converts AWSSecretKey into crypto instance.
	public void setKey(String AWSSecretKey) throws Exception
	{
	  mac = Mac.getInstance("HmacSHA1");
	  byte[] keyBytes = AWSSecretKey.getBytes("UTF8");
	  signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
	  mac.init(signingKey);
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

	public String getS3ErrorCode(InputStream doc) throws Exception
	{
		String code = null;
        SAXParserFactory parserfactory = SAXParserFactory.newInstance();
        parserfactory.setNamespaceAware(false);
        parserfactory.setValidating(false);
        SAXParser xmlparser = parserfactory.newSAXParser();
        S3ErrorHandler handler = new S3ErrorHandler();
        xmlparser.parse(doc, handler);
		code = handler.getErrorCode();
		return code;
	}

	// This inner class implements a SAX handler.
	class S3ErrorHandler extends DefaultHandler
	{
	    private StringBuffer code = new StringBuffer();
	    private boolean append = false;

		public void startElement(String uri, String ln, String qn, Attributes atts)
	    {
	        if (qn.equalsIgnoreCase("Code")) append = true;
	    }

	    public void endElement(String url, String ln, String qn)
	    {
	        if (qn.equalsIgnoreCase("Code")) append = false;
	    }

	    public void characters(char[] ch, int s, int length)
	    {
	        if (append) code.append(new String(ch, s, length));
	    }

	    public String getErrorCode()
	    {
	    	return code.toString();
	    }
	}


}
