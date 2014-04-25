package com.uwea.testdemo;

import com.searchtechnologies.cloudsearch.api.CloudSearchClient;
import com.searchtechnologies.cloudsearch.api.CloudSearchDocResult;
import com.searchtechnologies.cloudsearch.api.CloudSearchFacetResult;
import com.searchtechnologies.cloudsearch.api.CloudSearchFacetValue;
import com.searchtechnologies.cloudsearch.api.CloudSearchQuery;
import com.searchtechnologies.cloudsearch.api.CloudSearchQueryException;
import com.searchtechnologies.cloudsearch.api.CloudSearchResult;

public class CloudSearchQueryExample {
	
	 public static void main(String[] args) throws Exception {

		 CloudSearchQuery query = new CloudSearchQuery();
		  		  
		    //** Construct the query 
		    query.setQuery("testing");
		    query.addResultField("resourcename");
		    query.setStart(0);
		    query.setSize(25);
		    //query.addFilter("author", "pooja", false);
		    
		      query.addFacet("author");
		  /*  query.addFacet("-f_year");      // note: "-" means sort facet values alpha descending
		    query.addResultField("title");
		    query.addResultField("categories");
		    query.addResultField("url");
		    query.addResultField("tease
		    query.addSort("len2_boost", ascending false);  // sort by rank expression
*/
		    String searchEndpoint = "search-uwea-meta-z4zlv3ysh7rt3y2vyqqhmwrkye.us-west-2.cloudsearch.amazonaws.com";
		    
		    System.out.println("Executing Query: " + query.toHttpQuery(searchEndpoint));
		    
		    //** Execute the query 
		    CloudSearchClient client = new CloudSearchClient(searchEndpoint);

		    CloudSearchResult results = null;
		    try {
		      results = client.search(query);
		    }
		    catch(CloudSearchQueryException csqe) {
		      if(csqe.getServerMessage() != null)
		        System.out.println("Error executing query: " + csqe.getServerMessage());
		      csqe.printStackTrace();
		      return;
		    }

		    //*** Print out the results
		    
		    System.out.println("Total Results Found: " + results.getTotalHits());
		    System.out.println();
		    System.out.println("DOCUMENTS:");
		    for(int i = 0 ; i < results.getNumResultsReturned() ; i++) {
		      CloudSearchDocResult doc = results.getDoc(i);
		      	      
		      System.out.printf(doc.getId().toString()+" Value of resource--->"+ doc.getFieldValues("resourcename").get(0));
		      System.out.printf("    URL: %s\n", doc.getFieldValues("url"));
		      if(doc.getFieldValues("categories") != null) {
		        System.out.print("    Categories:  ");
		        for(String cat : doc.getFieldValues("categories")) {
		          System.out.print(cat + ";");
		        }
		        System.out.println();
		      }
		      System.out.println();
		    }

		    System.out.println();
		    System.out.println("FACETS:");
		    for(CloudSearchFacetResult facet : results.getFacets()) {
		      System.out.println("  " + facet.getField());
		      for(int i = 0 ; i < facet.getValueCount() ; i++) {
		        CloudSearchFacetValue facetValue = facet.getValue(i);
		        System.out.printf("    %s (%d)\n", facetValue.getValue(), facetValue.getCount());
		      }
		      System.out.println();
		    }
		    
		   // CloudSearchFacetQuery  facQuery=new CloudSearchFacetQuery(CloudSearchFacetResult.FacetSortType.VALUE_ASC,"author");
		    
		    
		    
		  }

}
