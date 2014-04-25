package com.uwea.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.searchtechnologies.cloudsearch.api.CloudSearchClient;
import com.searchtechnologies.cloudsearch.api.CloudSearchDocResult;
import com.searchtechnologies.cloudsearch.api.CloudSearchQuery;
import com.searchtechnologies.cloudsearch.api.CloudSearchQueryException;
import com.searchtechnologies.cloudsearch.api.CloudSearchResult;
import com.uwea.util.SearchDocMessages;

@WebServlet(value = "/searchOldApi")
public class SearchDocument extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// Get the query string
		String searchType = req.getParameter("rSearch"); //$NON-NLS-1$
		String queryString=""; 
		CloudSearchQuery query = new CloudSearchQuery();
		if (searchType.equals("q")) { //$NON-NLS-1$
			String searchQuery = req.getParameter("query"); //$NON-NLS-1$
			if (searchQuery != null) {
				query.setQuery(searchQuery);
			}
			queryString="q="+searchQuery;
			
		} else if (searchType.equals("bq")) { //$NON-NLS-1$

			String facetField = req.getParameter("facetField"); //$NON-NLS-1$
			String facetValue = req.getParameter("facetValue"); //$NON-NLS-1$
			String facetInclude = req.getParameter("incEx"); //$NON-NLS-1$
			boolean includeFlag = false;
			//Include query String
			queryString="bq="+facetField+":'"+facetValue+"'";
			//Exclude query String
			if (("true").equals(facetInclude)) { //$NON-NLS-1$
				includeFlag = true;
				queryString="bq="+facetField+":'-"+facetValue+"'";
			}

			if (facetField != null && facetValue != null) {
				query.addFilter(facetField, facetValue, includeFlag);
			}
			
			
		}

		// Get result from CloudSearch
		Map<String,String> searchMap = null;
		try {
			searchMap = getCloudSearchDoc(query);
		} catch (Exception e) {

			System.out.println("Error" + e.getMessage()); //$NON-NLS-1$
			e.printStackTrace();
		}

		// Convert the CloudSearchDocumentId to actual path
		Map<String, String> linkDoc = documentLink(searchMap);

		// Display result to UI

	/*	PrintWriter pr = res.getWriter();
		for (Map.Entry entry : linkDoc.entrySet()) {
			pr.print("<a href=" + entry.getKey() + ">" + entry.getValue()
					+ "</a><hr>");
		}*/

		req.setAttribute("listName", linkDoc); //$NON-NLS-1$
		req.setAttribute("queryStr", queryString);
		System.out.println("I am come till link document as going to forward the request to same page"); //$NON-NLS-1$
		req.getRequestDispatcher("index.jsp").forward(req, res); //$NON-NLS-1$
	}

	public Map<String,String> documentLink(Map<String, String> searchMap) {

		Map<String, String> linkDoc = new HashMap<String, String>();

		for (Map.Entry entry : searchMap.entrySet()) {
			String documentId = (String) entry.getKey();
			String documentName = (String) entry.getValue();

			// Need to match the document Name with document id and then split
			// the documentId with directory
			Pattern ip_pattern = Pattern.compile(documentName.substring(0, 3).toLowerCase());
			String[] documentDir = ip_pattern.split(documentId);

			String interStr = documentDir[0].replace("_", "/") //$NON-NLS-1$ //$NON-NLS-2$
					.replace("////", ".").replace("///", "://"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			documentId = interStr + documentName;
			System.out.println("DOCUMENTID-->"+documentId+"DOCUMENT_NAME--->"+documentName); //$NON-NLS-1$ //$NON-NLS-2$
			linkDoc.put(documentId, documentName);
		}
		return linkDoc;
	}

	public Map<String, String> getCloudSearchDoc(CloudSearchQuery query) throws Exception {

		query.addResultField("resourcename"); //$NON-NLS-1$
		String searchEndpoint = SearchDocMessages.getString("SearchDocument"); //$NON-NLS-1$

		query.setStart(0);
	    query.setSize(25);
	    
		System.out.println("Executing Query: " //$NON-NLS-1$
				+ query.toHttpQuery(searchEndpoint));

		CloudSearchClient client = new CloudSearchClient(searchEndpoint);

		CloudSearchResult results = null;
		try {
			results = client.search(query);
		} catch (CloudSearchQueryException csqe) {
			if (csqe.getServerMessage() != null)
				System.out.println("Error executing query: " //$NON-NLS-1$
						+ csqe.getServerMessage());
			csqe.printStackTrace();
			return null;
		}

		Map<String, String> searchmap = new HashMap<String, String>();
		for (int i = 0; i < results.getNumResultsReturned(); i++) {
			CloudSearchDocResult doc = results.getDoc(i);

			System.out.printf(doc.getId().toString() + " Value of resource--->" //$NON-NLS-1$
					+ doc.getFieldValues("resourcename").get(0)); //$NON-NLS-1$
			System.out.printf("    URL: %s\n", doc.getFieldValues("url")); //$NON-NLS-1$ //$NON-NLS-2$
			searchmap.put(doc.getId(), doc.getFieldValues("resourcename") //$NON-NLS-1$
					.get(0));
		}

		return searchmap;
	}

}
