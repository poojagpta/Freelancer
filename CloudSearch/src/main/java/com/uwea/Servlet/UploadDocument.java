package com.uwea.Servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.uwea.util.DocumentUtil;
import com.uwea.util.S3StorageUtil;
import com.uwea.util.SearchDocMessages;


@WebServlet(value = "/uploadDoc")
@MultipartConfig
public class UploadDocument extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -944755615251271429L;

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String fileName = null;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;

		// Verify the content type
		String contentType = req.getContentType();
		if ((contentType.indexOf("multipart/form-data") >= 0)) { //$NON-NLS-1$

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);
			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File(Messages.getString("TempFile"))); //$NON-NLS-1$

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// maximum file size to be uploaded.
			upload.setSizeMax(maxFileSize);

			try {
				// Parse the request to get file items.
				List fileItems = upload.parseRequest(req);
				Iterator i = fileItems.iterator();
				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (!fi.isFormField()) {
						// Get the uploaded file parameters
						/*
						 * String fieldName = fi.getFieldName(); fileName =
						 * fi.getName(); boolean isInMemory = fi.isInMemory();
						 * long sizeInBytes = fi.getSize(); BufferedReader br =
						 * new BufferedReader( new
						 * InputStreamReader(fi.getInputStream())); String
						 * output;
						 * 
						 * while ((output = br.readLine()) != null) {
						 * stringBuffer.append(output); }
						 */

						// need to save the input file
						String name = new File(fi.getName()).getName();
						fileName = Messages.getString("TempFile")
								+ File.separator + name;
						fi.write(new File(fileName));
					}
				}
			} catch (Exception e) {

				System.out.println("Error Occured" + e.getMessage());
			}
		}

		if (fileName != null) {

			String urlpath = null;
			
			// Persist the data to S3 object
			urlpath = S3StorageUtil.putObjecttos3(fileName);
			

			// Generate SDF file
			StringWriter stringWriter = DocumentUtil.generateJsonObject(
					fileName, urlpath);

			String method = "POST"; //$NON-NLS-1$

			HttpURLConnection httpConn = null;
			URL url = new URL(
					"http://" + SearchDocMessages.getString("DocUpload") //$NON-NLS-1$ 
							+ "/2013-01-01/documents/batch"); //$NON-NLS-1$

			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);

			httpConn.setRequestMethod(method);
			httpConn.setRequestProperty("Content-Type", "application/json"); //$NON-NLS-1$

			DataOutputStream outputStream = new DataOutputStream(
					httpConn.getOutputStream());
			outputStream.write(stringWriter.toString().getBytes("UTF-8")); //$NON-NLS-1$
			outputStream.flush();
			outputStream.close();

			if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " //$NON-NLS-1$
						+ httpConn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(httpConn.getInputStream())));

			System.out.println("Output from Server .... \n"); //$NON-NLS-1$
			String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpConn.disconnect();

		}

		//Removed the saved file 
		File fileDel=new File(fileName);
		fileDel.delete();
		
		res.sendRedirect("search.jsp"); //$NON-NLS-1$
	}

}
