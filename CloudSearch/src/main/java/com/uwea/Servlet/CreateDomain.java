package com.uwea.Servlet;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearchClient;
import com.amazonaws.services.cloudsearchv2.model.CreateDomainRequest;
import com.amazonaws.services.cloudsearchv2.model.CreateDomainResult;
import com.amazonaws.services.cloudsearchv2.model.DefineIndexFieldRequest;
import com.amazonaws.services.cloudsearchv2.model.DefineIndexFieldResult;
import com.amazonaws.services.cloudsearchv2.model.DescribeDomainsRequest;
import com.amazonaws.services.cloudsearchv2.model.DescribeDomainsResult;
import com.amazonaws.services.cloudsearchv2.model.DescribeIndexFieldsRequest;
import com.amazonaws.services.cloudsearchv2.model.DomainStatus;
import com.amazonaws.services.cloudsearchv2.model.IndexField;
import com.amazonaws.services.cloudsearchv2.model.ServiceEndpoint;
import com.amazonaws.services.cloudsearchv2.model.UpdateServiceAccessPoliciesRequest;
import com.amazonaws.services.cloudsearchv2.model.UpdateServiceAccessPoliciesResult;
import com.amazonaws.services.ec2.model.Region;
import com.uwea.cache.IndexFieldCache;
import com.uwea.util.SearchDocMessages;

@WebServlet(value = "/createDomain")
public class CreateDomain  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4534687022493449496L;
	
	private String domainName; 
	private ServiceEndpoint docEndpoint;
	private ServiceEndpoint searchEndpoint;
	private String arn;
	private String indexField;
	private String region="us-east-1";
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public ServiceEndpoint getDocEndpoint() {
		return docEndpoint;
	}

	public void setDocEndpoint(ServiceEndpoint docEndpoint) {
		this.docEndpoint = docEndpoint;
	}

	public ServiceEndpoint getSearchEndpoint() {
		return searchEndpoint;
	}

	public void setSearchEndpoint(ServiceEndpoint searchEndpoint) {
		this.searchEndpoint = searchEndpoint;
	}

	public String getArn() {
		return arn;
	}

	public void setArn(String arn) {
		this.arn = arn;
	}

	public String getIndexField() {
		return indexField;
	}

	public void setIndexField(String indexField) {
		this.indexField = indexField;
	}

	private static Logger logger = LoggerFactory.getLogger(CreateDomain.class);
	
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.info("");
		domainName=req.getParameter("domainName");
		String region1=req.getParameter("region");
		indexField=req.getParameter("indexField");
		
		if(region1!=null){
			region=region1;
		}
		
		//Create Domain
		String realEndpoint = "cloudsearch."+getRegion()+".amazonaws.com";
		InputStream stream = CreateDomain.class.getResourceAsStream("/credentials.txt");
		PropertiesCredentials creds = new PropertiesCredentials(stream);
		ClientConfiguration clientConfig = new ClientConfiguration();
		
		AmazonCloudSearchClient configService=new AmazonCloudSearchClient(creds,clientConfig);  		
		
		configService.setEndpoint(realEndpoint, "cloudsearch", region);
		CreateDomainRequest createDomainRequest = new CreateDomainRequest();
		createDomainRequest.setDomainName(getDomainName());
		CreateDomainResult result = configService
				.createDomain(createDomainRequest);
		pollForCompletion(configService, getDomainName());
		
		//Access policy
		BufferedInputStream in= new BufferedInputStream(CreateDomain.class.getResourceAsStream("/accesspolicy.txt"));        
		byte[] contents = new byte[1024];

		int bytesRead=0;
		String policyDocument=""; 
		 while( (bytesRead = in.read(contents)) != -1){ 
			 policyDocument = new String(contents, 0, bytesRead);               
		 }
		
		 policyDocument=policyDocument.replace("arn", getArn());
		
		UpdateServiceAccessPoliciesRequest updateServiceAccessPoliciesRequest = new UpdateServiceAccessPoliciesRequest();
		updateServiceAccessPoliciesRequest.setDomainName(getDomainName());
		updateServiceAccessPoliciesRequest.setAccessPolicies(policyDocument);
        UpdateServiceAccessPoliciesResult updateServiceAccessPoliciesResult = configService.updateServiceAccessPolicies(updateServiceAccessPoliciesRequest);
        System.out.println(updateServiceAccessPoliciesResult.getAccessPolicies());
        
        
        //Create index fields
        if(null !=getIndexField()&&!("").equals(getIndexField().trim())&&!("null").equalsIgnoreCase(getIndexField())){
        	DefineIndex(configService,getIndexField());        	
        }
        DefineIndex(configService,"full_path");
        DefineIndex(configService,"content");
        
        DescribeIndexFieldsRequest descindexfieldreq=new DescribeIndexFieldsRequest();
        descindexfieldreq.setDomainName(getDomainName());
        List list=descindexfieldreq.getFieldNames();
        IndexFieldCache.put(list);
        
        //Create Properties File
        Properties prop = new Properties();
        prop.setProperty("SearchDocument", getSearchEndpoint().getEndpoint());
        prop.setProperty("DocUpload", getDocEndpoint().getEndpoint());
        prop.store(new FileWriter(SearchDocMessages.FILE_NAME), "Search/Upload Document path");  
        
        Properties propTest= System.getProperties();
        
        
        
        res.sendRedirect("upload.jsp");
	}
	
	
	public boolean pollForCompletion(AmazonCloudSearchClient configService,
			String domainName) {

		long start = System.currentTimeMillis();
		 
		long now = System.currentTimeMillis();
		long lastPrintTime = now;


		DescribeDomainsRequest ddReq = new DescribeDomainsRequest();
		List domainNames = new ArrayList();
		domainNames.add(domainName);
		ddReq.setDomainNames(domainNames);
		boolean allEndpointsPresent = checkStatus(configService,ddReq);
		
		do {
			if (allEndpointsPresent)
				break;
			now = System.currentTimeMillis();
			if (now - start > 0x36ee80L)
				break;
			if (now - lastPrintTime > 60000L) {
				lastPrintTime = now;				
			}else{
				continue;
			}
			allEndpointsPresent = checkStatus(configService,ddReq);

		} while (true);
		
		return allEndpointsPresent;

	}

	private boolean checkStatus(AmazonCloudSearchClient configService,DescribeDomainsRequest ddReq){
		
        //Check after some interval of time
		DescribeDomainsResult res = configService.describeDomains(ddReq);
		List statuses = res.getDomainStatusList();
		if (statuses.size() > 1) {
			new Exception("Multiple Domain exists");
		}
		
		DomainStatus domainStatus = null;
		if (statuses != null) {
			domainStatus = (DomainStatus) statuses.get(0);
			setDocEndpoint(domainStatus.getDocService());
			setSearchEndpoint(domainStatus.getSearchService());
			setArn(domainStatus.getARN());
			if(getDocEndpoint().getEndpoint()!=null){
				return true;	
			}				
		}
		return false;
	}
	
	private void DefineIndex(AmazonCloudSearchClient configService,String indField){
		DefineIndexFieldRequest defineIndexFieldRequest = new DefineIndexFieldRequest();
        defineIndexFieldRequest.setDomainName(getDomainName());
        IndexField field = new IndexField();
        field.setIndexFieldName(indField);
        field.setIndexFieldType("text");
        
        defineIndexFieldRequest.setIndexField(field);
        DefineIndexFieldResult defineIndexFieldResult = configService.defineIndexField(defineIndexFieldRequest);
        System.out.println(defineIndexFieldResult.toString());
	}
}
