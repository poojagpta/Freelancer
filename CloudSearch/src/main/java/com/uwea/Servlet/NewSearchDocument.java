package com.uwea.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.searchtechnologies.cloudsearch.api.CloudSearchQuery;
import com.uwea.util.SearchDocMessages;

@WebServlet(value = "/search")
public class NewSearchDocument extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// Get the query string
		String searchType = req.getParameter("rSearch"); //$NON-NLS-1$
		String queryString = "";
		CloudSearchQuery query = new CloudSearchQuery();
		if (searchType.equals("q")) { //$NON-NLS-1$
			String searchQuery = req.getParameter("query"); //$NON-NLS-1$
			if (searchQuery != null) {
				query.setQuery(searchQuery);
			}
			queryString = "q=" + searchQuery;

		} else if (searchType.equals("bq")) { //$NON-NLS-1$

			String facetField = req.getParameter("facetField"); //$NON-NLS-1$
			String facetValue = req.getParameter("facetValue"); //$NON-NLS-1$
			String facetInclude = req.getParameter("incEx"); //$NON-NLS-1$

			// Include query String
			queryString = "q=" + facetField + ":'" + facetValue
					+ "'&q.parser=structured";
			// Exclude query String
			if (("true").equals(facetInclude)) { //$NON-NLS-1$

				queryString = "q=(not " + facetField + ":'" + facetValue
						+ "')&q.parser=structured";
			}
		}

		String method = "GET";
		HttpURLConnection httpConn = null;
		URL url = new URL("http://" + SearchDocMessages.getString("SearchDocument")
				+ "/2013-01-01/search?" + queryString);

		httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setDoOutput(true);

		httpConn.setRequestMethod(method);

		if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			// throw new RuntimeException("Failed : HTTP error code : "
			// + httpConn.getResponseCode());
			System.out.println("There is error--->"
					+ httpConn.getResponseCode());
		}

		StringBuilder jsonObject = new StringBuilder();

		if (httpConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			System.out.println("Error Message"+httpConn.getResponseCode());
		
		}else{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(httpConn.getInputStream())));
			String output;

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				jsonObject.append(output);
			}
			
		}

		httpConn.disconnect();

		// Map json object and get value
		Map<String, String> linkDoc = new HashMap<String, String>(); // documentLink(jsonObject);
		req.setAttribute("listName", linkDoc); //$NON-NLS-1$
		req.setAttribute("queryStr", queryString);
		System.out
				.println("I am come till link document as going to forward the request to same page"); //$NON-NLS-1$
		req.getRequestDispatcher("search.jsp").forward(req, res); //$NON-NLS-1$

	}

	public static Map<String, String> documentLink(StringBuilder jsonObject) {

		Map<String, String> linkDoc = new HashMap<String, String>();
		String[] documentIds = jsonObject.toString().split("\"id\"");
		String[] documentPaths = jsonObject.toString().split("full_path");

		
		for (int i = 0; i < documentIds.length; i++) {

			String documentPath = documentPaths[i];
			String documentId = documentIds[i];

			if (":".equals(documentPath.charAt(1) + "")
					&& ":".equals(documentId.charAt(0) + "")) {
				linkDoc.put(
						documentPath.substring(1, documentPath.indexOf(",", 1)),
						documentId.substring(2, documentId.indexOf(",", 1)));
			}

		}

		return linkDoc;
	}
}
