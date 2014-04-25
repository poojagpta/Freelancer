package com.stern.fraudshields.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.stern.fraudshields.model.ThirdPartyReq;
import com.stern.fraudshields.service.ThirdPartyReqService;
import com.stern.fraudshields.service.ThirdPartyReqServiceImpl;

public class ProcessOrderServlet extends HttpServlet {
	private static final Logger log = LoggerFactory
			.getLogger(ProcessOrderServlet.class);

	public ThirdPartyReqService thirdPartyReqService = new ThirdPartyReqServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			processOrder(req, resp);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			processOrder(req, resp);
		} catch (IOException e) {

			log.info(e.getMessage());
			throw e;

		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	private void processOrder(HttpServletRequest req, HttpServletResponse resp)
			throws Exception, EntityNotFoundException, SAXException {

		String url = (String) req.getParameter("URL");

		List<ThirdPartyReq> thirdPartyReqList = thirdPartyReqService
				.getThirdPartyPendingReq();

		String result = null;

		for (ThirdPartyReq thirdPartyReq : thirdPartyReqList) {
			result = fetchExternalURL(url, thirdPartyReq.getReqObject());
			if (result != null && !("").equals(result.trim())) {
				thirdPartyReq.setRespObject(result);
				thirdPartyReqService.update(thirdPartyReq);
			}
		}

		resp.getWriter().write("Success");
	}

	public String fetchExternalURL(String urlName, String content)
			throws Exception {

		OutputStream os;
		BufferedReader response;
		HttpURLConnection urlConn = getConnection(urlName);
		os = urlConn.getOutputStream();
		content += "xCommand=Fraud&";
		content += "xKey=xTest_FraudShields&";
		content += "xVersion=4.5.1&";
		content += "xSoftwareName=FraudShieldsGAE&";
		content += "xSoftwareVersion=1.0";
		
		os.write(content.getBytes());
		os.close();
		// Get response data.
		response = new BufferedReader(new InputStreamReader(
				urlConn.getInputStream()));
		String str;
		String result = "";
		while (null != ((str = response.readLine()))) {
			result += str;
		}
		response.close();

		return result;
	}

	public String fetchExternalURL1(String urlName, String content)
			throws IOException {

		URL url = new URL(urlName);

		HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST);

		content += "xCommand=Fraud&";
		content += "xKey=xTest_FraudShields";

		request.setPayload(content.getBytes());
		HTTPHeader header = new HTTPHeader("Content-Type",
				"application/x-www-form-urlencoded");
		request.setHeader(header);

		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		HTTPResponse httpResponse = fetcher.fetch(request);

		StringBuffer sb = new StringBuffer();
		if (httpResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
			sb.append(httpResponse.getContent().toString());
		}

		System.out.println("Receive output" + sb.toString());
		return sb.toString();

	}

	private HttpURLConnection getConnection(String urlname) throws Exception {

		URL url = new URL(urlname);

		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		// urlConn.setUseCaches(false);
		urlConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		return urlConn;
	}

}
