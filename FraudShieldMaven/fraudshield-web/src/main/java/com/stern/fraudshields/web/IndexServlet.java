package com.stern.fraudshields.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.stern.fraudshields.model.RawData;
import com.stern.fraudshields.model.Customer;
import com.stern.fraudshields.server.MockDAO;
import com.stern.fraudshields.server.UserRepository;

/**
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory
			.getLogger(IndexServlet.class);

	private final UserRepository userRepository = new UserRepository();

	private static final int DEFAULT_BUFFER_SIZE = 10240;

	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		if (log.isDebugEnabled()) {
			log.debug("doGet");
		}

		// delete
		if (request.getParameter("id") != null) {
			deleteUser(request);

			response.sendRedirect("index");

			return;
		}

		// get
		final Collection<Customer> users = this.userRepository.getAll();
		request.setAttribute("customers", users);

		if (log.isDebugEnabled()) {
			log.debug("customers: " + users);
		}
		
		MockDAO mockDAO=new MockDAO();
		
		forward(request, response, "index.jsp");
	}

	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		if (log.isDebugEnabled()) {
			log.debug("doPost");
		}

		// create
		/*
		 * createUser(request); response.sendRedirect("index");
		 */

		// Persisting data to datastore...
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Credentials", "false");
		response.setHeader("Access-Control-Allow-Headers", "X-Requested-With");
		response.setHeader("Access-Control-Max-Age", "86400");

		// Fetch the raw data
		BufferedReader reader = request.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line + "\n");
			line = reader.readLine();
		}
		reader.close();
		String data = sb.toString();
		System.out.println("Data received---->" + data);
		RawData rawData = new RawData();
		rawData.setRemote_ip("localhost");
		Blob blogField = new Blob(data.getBytes());
		rawData.setPost(blogField);
		rawData.setTimestamp(new Date());
		rawData.setPost(blogField);
		Objectify ofy = ObjectifyService.begin();
		ofy.put(rawData);
	}

	/*@Override
	public void service(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		BufferedReader reader = req.getReader();
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			sb.append(line + "\n");
			line = reader.readLine();
		}
		reader.close();
		String data = sb.toString();


		if (null != data && !("").equals(data)) {

			RawData rawData = new RawData();
			rawData.setRemote_ip("localhost");
			Blob blogField = new Blob(data.getBytes());
			rawData.setPost(blogField);
			rawData.setTimestamp(new Date());
			rawData.setPost(blogField);

			try {
				JSONObject jsonObject = new JSONObject(sb.toString());
				Iterator iterKey = jsonObject.keys(); // create the iterator for
														// the json object.
				while (iterKey.hasNext()) {
					String jsonKey = (String) iterKey.next(); // retrieve every
																// key ex: name,
																// age
					if (jsonKey.contains("name")) {
						rawData.setFirstName(jsonObject.getString(jsonKey));
					} else if (jsonKey.contains("add")) {
						rawData.setAddress(jsonObject.getString(jsonKey));
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Exception occured" + e.getMessage());
			}

			Objectify ofy = ObjectifyService.begin();
			ofy.put(rawData);
			processResponse(req, response);
		}

	}*/

	protected void createUser(final HttpServletRequest request) {
		final String text = request.getParameter("text");

		if (log.isDebugEnabled()) {
			log.debug("creating user with text: " + text);
		}

		final Customer user = new Customer();
		this.userRepository.create(user);
	}

	protected void deleteUser(final HttpServletRequest request) {
		final Long id = Long.valueOf(request.getParameter("id"));

		if (log.isDebugEnabled()) {
			log.debug("deleting user with id: " + id);
		}

		this.userRepository.deleteById(id);
	}

	/**
	 * Forwards request and response to given path. Handles any exceptions
	 * caused by forward target by printing them to logger.
	 * 
	 * @param request
	 * @param response
	 * @param path
	 */
	protected void forward(final HttpServletRequest request,
			final HttpServletResponse response, final String path) {
		try {
			final RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		} catch (final Throwable tr) {
			if (log.isErrorEnabled()) {
				log.error("Cought Exception: " + tr.getMessage());
				log.debug("StackTrace:", tr);
			}
		}
	}

	public void processResponse(HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		String filePath = "testImg.gif";

		File file = new File(req.getSession().getServletContext()
				.getRealPath(filePath));

		// Check if file actually exists in filesystem.
		if (!file.exists()) {
			// Do your thing if the file appears to be non-existing.
			// Throw an exception, or send 404, or show default/warning page, or
			// just ignore it.
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		// Get content type by filename.
		String contentType = getServletContext().getMimeType(file.getName());

		// If content type is unknown, then set the default value.
		// For all content types, see:
		// http://www.w3schools.com/media/media_mimeref.asp
		// To add new content types, add new mime-mapping entry in web.xml.
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		// Init servlet response.

		response.reset();
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file.getName() + "\"");

		// To solve cross browser issues
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setContentType(contentType);

		// Prepare streams.
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			// Open streams.
			input = new BufferedInputStream(new FileInputStream(file),
					DEFAULT_BUFFER_SIZE);
			output = new BufferedOutputStream(response.getOutputStream(),
					DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				// Do your thing with the exception. Print it, log it or mail
				// it.
				e.printStackTrace();
			}
		}
	}
}