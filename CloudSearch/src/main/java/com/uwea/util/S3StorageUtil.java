package com.uwea.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.uwea.Servlet.Messages;

public class S3StorageUtil {

public static void main(String str[]) throws IOException{
	S3StorageUtil.putObjecttos3("C:\\Users\\ashok\\Desktop\\Aviation\\Learn.txt");
	
}
	public static String putObjecttos3(String filepath) throws IOException {

		InputStream stream = S3StorageUtil.class
				.getResourceAsStream("/credentials.txt");

		PropertiesCredentials creds = new PropertiesCredentials(stream);
		AWSCredentials credentials = new BasicAWSCredentials(creds.getAWSAccessKeyId(),creds.getAWSSecretKey());
	

		AmazonS3 s3 = new AmazonS3Client(credentials);   
  
		File file = new File(filepath);

		String s3filename = Messages.getString("s3Path");

		s3.putObject(new PutObjectRequest(s3filename, file.getName(), file));

		// create the url fetch
		String urlName = "https://" + Messages.getString("s3Path")
				+ ".s3.amazonaws.com/" + file.getName();

		return urlName;
	}
}
