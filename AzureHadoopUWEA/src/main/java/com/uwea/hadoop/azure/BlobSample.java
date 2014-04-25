package com.uwea.hadoop.azure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.microsoft.windowsazure.serviceruntime.RoleEnvironment;
import com.microsoft.windowsazure.services.blob.client.BlobContainerPermissions;
import com.microsoft.windowsazure.services.blob.client.CloudBlobClient;
import com.microsoft.windowsazure.services.blob.client.CloudBlobContainer;
import com.microsoft.windowsazure.services.blob.client.CloudBlockBlob;
import com.microsoft.windowsazure.services.core.storage.CloudStorageAccount;
import com.microsoft.windowsazure.services.core.storage.StorageException;

public class BlobSample {
	public static final String storageConnectionString = "DefaultEndpointsProtocol=http;"
			+ "AccountName=cadimxbis01;"
			+ "AccountKey=Wk3XveVqLja1ypCvmlOTEGtTZ/29YgslcD49mPQDuXYxkliVnc4cWG+r3AsprI6OrESeLWE+DioQkwtoZSNJyA==";

	public static void main(String[] args) {
	System.out.println("This is a test");
		try {
			// Retrieve storage account from connection-string
			String storageConnectionStr = RoleEnvironment
					.getConfigurationSettings().get("StorageConnectionString");

			System.out.println(storageConnectionStr);

			CloudStorageAccount account;
			CloudBlobClient serviceClient;
			CloudBlobContainer container;
			CloudBlockBlob blob;

			account = CloudStorageAccount.parse(storageConnectionString);
			serviceClient = account.createCloudBlobClient();
			// Container name must be lower case.
			container = serviceClient.getContainerReference("blobsample");
			container.createIfNotExist();

			// Set anonymous access on the container.
			BlobContainerPermissions containerPermissions;
			containerPermissions = new BlobContainerPermissions();
			containerPermissions.getPublicAccess();
			container.uploadPermissions(containerPermissions);

			// Upload an image file.
			blob = container.getBlockBlobReference("NewDashboard.png");
			File fileReference = new File(
					"C:\\Users\\ashok\\Desktop\\Aviation\\NewDashboard.png");
			blob.upload(new FileInputStream(fileReference),
					fileReference.length());
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.print("FileNotFoundException encountered: ");
			System.out.println(fileNotFoundException.getMessage());
			System.exit(-1);
		} catch (StorageException storageException) {
			System.out.print("StorageException encountered: ");
			System.out.println(storageException.getMessage());
			System.exit(-1);
		} catch (Exception e) {
			System.out.print("Exception encountered: ");
			System.out.println(e.getMessage());
			System.exit(-1);
		}

	}
}
