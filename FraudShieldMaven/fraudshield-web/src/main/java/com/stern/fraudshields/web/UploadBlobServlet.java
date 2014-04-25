package com.stern.fraudshields.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.stern.fraudshields.model.MerchantMapping;
import com.stern.fraudshields.service.MerchantMappingService;
import com.stern.fraudshields.service.MerchantMappingServiceImpl;

public class UploadBlobServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory
			.getLogger(UploadBlobServlet.class);

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		log.debug("Enter in uploadBlobServlet doGet method");
		try {
			uploadMapping(req, resp);
			resp.sendRedirect("/success.html");
		} catch (Exception e) {
			log.error("Error Message in upoad xml" + e.getMessage());
			resp.getWriter().print(e.getMessage());
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		log.debug("Enter in uploadBlobServlet doPost method");
		try {
			uploadMapping(req, resp);
			resp.sendRedirect("/success.html");
		} catch (Exception e) {
			log.error("Error Message in upoad xml" + e.getMessage());
			resp.getWriter().print(e.getMessage());
		}
	}

	private void uploadMapping(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, SAXException {

		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		BlobKey blobKey = blobs.get("mappingfile");

		// Need to validate the xml file first
		String xmlfileContent = new String(blobstoreService.fetchData(blobKey,
				0, BlobstoreService.MAX_BLOB_FETCH_SIZE - 1));

		validateXMLwithXSD(CustomMessages.getString("XSD_SCHEMA_PATH"), xmlfileContent);

		// Persisting to datastore
		MerchantMapping merchantMap = new MerchantMapping();
		merchantMap.setDescription(req.getParameter("desc"));
		merchantMap.setName(req.getParameter("name").toLowerCase());
		merchantMap.setXmlUrl(blobKey.getKeyString());

		MerchantMappingService service = new MerchantMappingServiceImpl();
		service.createMappingFile(merchantMap);
	}

	private boolean validateXMLwithXSD(String xsdLocation, String xmlfile)
			throws SAXException, IOException {
		// 1. Lookup a factory for the W3C XML Schema language
		SchemaFactory factory = SchemaFactory.newInstance(CustomMessages
				.getString("W3C_XML_SCHEMA_NS_URI"));

		// 2. Compile the schema.
		// Here the schema is loaded from a java.io.File, but you could use
		// a java.net.URL or a javax.xml.transform.Source instead.
		Source schemaLocation = new StreamSource(getServletContext().getResourceAsStream(xsdLocation));
		Schema schema = factory.newSchema(schemaLocation);

		// 3. Get a validator from the schema.
		Validator validator = schema.newValidator();

		// 4. Parse the document you want to check.
		Source source = new StreamSource(new StringReader(xmlfile));

		// 5. Check the document
		validator.validate(source);
		
		return true;
	}

}
