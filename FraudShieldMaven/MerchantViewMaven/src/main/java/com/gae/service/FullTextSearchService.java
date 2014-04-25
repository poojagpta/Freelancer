package com.gae.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Field.FieldType;
import com.google.appengine.api.search.GeoPoint;
import com.google.appengine.api.search.GetRequest;
import com.google.appengine.api.search.GetResponse;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

public class FullTextSearchService {
	
	private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	 
	public Document buildDocument(String documentId,List<Field> fieldList){
		
		Document.Builder documentBuilder = Document.newBuilder().setId(documentId);
		for (Field field : fieldList) {
			documentBuilder.addField(field);
		}
						
		
		return documentBuilder.build();
	}
	
	public Field buildField(String name,Object value,FieldType fieldType){
		
		Field.Builder fieldBuilder = null;
		try {
			 fieldBuilder = Field.newBuilder();
			fieldBuilder.setName(name);
			 switch (fieldType) {
		        case TEXT: 
		        	fieldBuilder.setText(value == null ? null: String.valueOf(value));
		        	break;
		        case HTML: 
		        	fieldBuilder.setHTML(value == null ? null: String.valueOf(value)); 
		        	break;
		        
		        case ATOM:
		        	fieldBuilder.setAtom(value == null ? null: String.valueOf(value)); 
		        	break;
		        	
		        case DATE: 
		        	fieldBuilder.setDate(value == null ? null: dateFormatter.parse(dateFormatter.format(value)));
		        	break;
		        case NUMBER:
		        	fieldBuilder.setNumber((Double)value);
		        	break;
		        case GEO_POINT:
		        	fieldBuilder.setGeoPoint((GeoPoint)value);
		    }
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fieldBuilder.build();
		
	}
	
	public Index getIndex(String name) {
	    IndexSpec indexSpec = IndexSpec.newBuilder().setName(name).build();
	    
	    return SearchServiceFactory.getSearchService().getIndex(indexSpec);
	}
	
	
	
	
	public void indexDocument(Document document, Index index){
		try {
		    // Put the document.
		    getIndex(index.getName()).put(document);
		} catch (PutException e) {
		    if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
		        // retry putting the document
		    }
		}
	}
	
	
	public void deleteDocument(Index index){
	
		try {
		    while (true) {
		        
		    	List<String> docIds = new ArrayList<String>();
		        GetRequest request = GetRequest.newBuilder().setReturningIdsOnly(true).build();
		        GetResponse<Document> response = getIndex(index.getName()).getRange(request);
		        if (!response.getResults().isEmpty()) {
		        	for (Document doc : response) {
			            docIds.add(doc.getId());
			        }
		        }
		        
		        getIndex(index.getName()).delete(docIds);
		    }
		} catch (RuntimeException e) {
		   e.printStackTrace();
		}
	}
	
	
	public Results<ScoredDocument> findDocuments(String queryString, int limit, Cursor cursor, Index index, QueryOptions options) {
		Query query = null;
		
		try {
			query = Query.newBuilder().setOptions(options).build(queryString);
		 
			} catch (SearchException e) {
				e.printStackTrace();
				return null;
		}
	    return getIndex(index.getName()).search(query);
	}
	
	public Object getOnlyField(ScoredDocument document,String fieldName){
		
		try {
			Field field = null;
			field = document.getOnlyField(fieldName);
				
			switch (field.getType()) {
				case TEXT:
					return field.getText();
					
				case DATE:
					return  field.getDate();
					
					
				case NUMBER:
					return field.getNumber();
					
					
				case ATOM:
					return field.getAtom();
					
				case HTML:
					return field.getHTML();
					
				
				case GEO_POINT:
					return field.getGeoPoint();
					
	
				default:
					return field.getText();
					
			}
				
		} catch (IllegalArgumentException e) {
			 e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	

}
