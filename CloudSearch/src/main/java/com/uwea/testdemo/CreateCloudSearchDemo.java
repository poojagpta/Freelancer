package com.uwea.testdemo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.SigningAlgorithm;

public class CreateCloudSearchDemo {

	private javax.crypto.spec.SecretKeySpec signingKey = null;
	private javax.crypto.Mac mac = null;
	private String keyId = null;

	public javax.crypto.spec.SecretKeySpec getSigningKey() {
		return signingKey;
	}

	public void setSigningKey(javax.crypto.spec.SecretKeySpec signingKey) {
		this.signingKey = signingKey;
	}

	public javax.crypto.Mac getMac() {
		return mac;
	}

	public void setMac(javax.crypto.Mac mac) {
		this.mac = mac;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public static void main(String str[]) throws Exception {
		CreateCloudSearchDemo cloudSearchDemo = new CreateCloudSearchDemo();
		cloudSearchDemo.createDomain();

	}

	public void createDomain() throws Exception {

		String method = "GET";

		// S3 timestamp pattern.
		//2012-07-12T21:41:29.094Z
		String fmt = "yyyyMMdd'T'HHmmss'Z'";
		SimpleDateFormat df = new SimpleDateFormat(fmt, Locale.US);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		String date = df.format(new Date()); 
		
		HttpURLConnection httpConn = null;
		URL url = new URL("https://cloudsearch.us-west-1.amazonaws.com");
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setDoInput(true);
		httpConn.setDoOutput(true);
		httpConn.setUseCaches(false);
		httpConn.setDefaultUseCaches(false);
		httpConn.setAllowUserInteraction(true);
		httpConn.setRequestMethod(method);
		httpConn.setRequestProperty("Action", "CreateDomain");
		httpConn.setRequestProperty("DomainName", "uwea-data");
		httpConn.setRequestProperty("Version", "2011-02-01");
		httpConn.setRequestProperty("Algorithm", "AWS4-HMAC-SHA256");
		
		//Action=CreateDomain&DomainName=movies&Version=2013-01-01&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIOSFODNN7EXAMPLE/20120712/us-east-1/cloudsearch/aws4_request&X-Amz-Date=2012-07-12T21:41:29.094Z&X-Amz-SignedHeaders=host
		AWS4Signer signer=new AWS4Signer();
		signer.setRegionName("us-west-1");
		signer.setServiceName("cloudsearch");
		String stringData="Action=CreateDomain&&DomainName=uwea-data&Version=2011-02-01&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIZUT5PW6K6NZLXOA/20120712/us-east-1/cloudsearch/aws4_request&X-Amz-Date=2012-07-12T21:41:29.094Z&X-Amz-SignedHeaders=host";
		
	//	signer.sign(stringData, "PZ7AM4nrFfNJjZ1TfTimVgP3gahsr9jJkddAEJ/C".getBytes(), SigningAlgorithm.HmacSHA256);
		httpConn.setRequestProperty("Date", date);

		if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ httpConn.getResponseCode());
		}
	}


}
