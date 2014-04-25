package com.uwea.testdemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * 
 * curl -X POST --upload-file 1.sdf
 * doc-uwea-data-ev34j2bopvdsut6jkrpnrckkqm.us-west
 * -1.cloudsearch.amazonaws.com/2011-02-01/documents/batch --header
 * "Content-Type:application/json"
 * 
 * @author pooja
 * 
 */
public class UploadSDFCloudSearch {

	public static void main(String str[]) throws Exception {
		UploadSDFCloudSearch uploadSDFCloudSearch = new UploadSDFCloudSearch();
		uploadSDFCloudSearch.uploadDocument();
	}

	public void uploadDocument() throws Exception {
		
		File file = new File(
				"C:\\Users\\ashok\\Desktop\\Aviation\\SDF\\Batch\\doc.sdf");
		//FileInputStream fileInputStream = new FileInputStream(file);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		
		String output;
		StringBuffer stringBuffer=new StringBuffer();
		while ((output = bufferedReader.readLine()) != null) {
			stringBuffer.append(output);
		}
 
		System.out.println(stringBuffer.toString());

		String method = "POST";

		HttpURLConnection httpConn = null;
		URL url = new URL(
				"http://doc-test-p3djlcb5a3wbkfpnqymzax7w3y.us-east-1.cloudsearch.amazonaws.com/2013-01-01/documents/batch");
		               
		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setDoOutput(true);

		httpConn.setRequestMethod(method);
		httpConn.setRequestProperty("Content-Type", "application/json");
				
		DataOutputStream outputStream = new DataOutputStream(
				httpConn.getOutputStream());
		outputStream.write(stringBuffer.toString().getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
				
		if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ httpConn.getResponseCode());
		}
 
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(httpConn.getInputStream())));
 
		
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
		httpConn.disconnect();

	}
	
	

}
