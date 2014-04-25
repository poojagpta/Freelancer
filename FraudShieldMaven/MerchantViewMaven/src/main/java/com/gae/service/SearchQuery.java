package com.gae.service;

import java.lang.reflect.ParameterizedType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gae.annotation.ReferencedName;
import com.gae.annotation.UnIndexSearch;
import com.gae.bean.MerchantDataGrid;
import com.gae.bean.Paging;
import com.gae.bean.SearchInput;
import com.gae.dao.AddressDAOImpl;
import com.gae.dao.OrderDetailDAOImpl;
import com.gae.dao.UserDAOImpl;
import com.gae.enumeration.SearchOption;
import com.gae.model.Address;
import com.gae.model.OrderDetail;
import com.gae.model.OrderStatus;
import com.gae.model.User;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Field.FieldType;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortExpression.SortDirection;
import com.google.appengine.api.search.SortOptions;


public class SearchQuery {
	
	private static int limit = 10; 
	
	 private static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<MerchantDataGrid> searchOnMerchant(SearchInput input,Long userId){
		
		List<MerchantDataGrid> merchantDataList = new ArrayList<MerchantDataGrid>();
					
	
		return merchantDataList;
	}
	
	public List<MerchantDataGrid> searchOnAddress(SearchInput input,Long userId){
		List<MerchantDataGrid> merchantDataList = new ArrayList<MerchantDataGrid>();
		
	
		return merchantDataList;
	}
	
	public List<MerchantDataGrid> search(SearchInput input,Long userId){
		
		List<MerchantDataGrid> merchantDataList = new ArrayList<MerchantDataGrid>();
		MerchantDataGrid merchantDataGrid = null;
		
		StringBuilder querySb = new StringBuilder();
		
		if(input.getEmail() != null && input.getEmail() != ""){
			querySb.append("email:\""+input.getEmail() + "\" ");
		}
		if(input.getUserName() != null && input.getUserName() != ""){
			querySb.append("(loginName:" + input.getUserName() + " OR firstName:"+input.getUserName() + ") ");
		}
		
		if(input.getAddress() != null && input.getAddress() != ""){
			querySb.append("billingAddress:" + input.getAddress() + " OR shippingAddress: " + input.getAddress() + " ");
		}
		
		if(input.getFromDate() != null){
			querySb.append("orderDate >= " + dateFormatter.format(input.getFromDate()) + " ");
		}
		
		if(input.getToDate() != null){
			querySb.append("orderDate <= " + dateFormatter.format(input.getToDate()) + " ");
		}
		if(input.getOrderStatus() != null ){
			querySb.append("orderStatus:" + input.getOrderStatus().getOrderStatus() + " ");
		}
		if(input.getCity() != null && input.getCity() != ""){
			querySb.append("billingAddress:" + input.getCity() + " OR shippingAddress: " + input.getCity() + " ");
		}
		if(input.getState() != null && input.getState() != ""){
			querySb.append("billingAddress:" + input.getState() + " OR shippingAddress: " + input.getState() + " ");
		}
		
		if(input.getCountry() != null && input.getState() != ""){
			querySb.append("billingAddress:" + input.getCountry() + " OR shippingAddress: " + input.getCountry() + " ");
		}
		
		Cursor cursor = getCursor(Paging.getPaging().getCursorString());
		
		FullTextSearchService service = new FullTextSearchService();
		SortOptions sortOptions = buildSortOptions("loginName", "", SortDirection.ASCENDING, 10);
		QueryOptions queryOptions = buildQueryOptions(null, sortOptions, limit, null, cursor);
		Results<ScoredDocument> scoredDocuments =  service.findDocuments(querySb.toString(), 10, cursor, service.getIndex(String.valueOf(userId)), queryOptions);
		
		cursor = scoredDocuments.getCursor();
		if(cursor != null){
			Paging.getPaging().setCursorString(cursor.toWebSafeString());
		}
		
		for (ScoredDocument document : scoredDocuments) {
			
			merchantDataGrid = new MerchantDataGrid();
			try {
			
				merchantDataGrid.setDate((Date)service.getOnlyField(document,"orderDate"));
				merchantDataGrid.setUserName((String)service.getOnlyField(document,"loginName"));
				merchantDataGrid.setBillingAddress((String)service.getOnlyField(document,"billingAddress"));
				merchantDataGrid.setShippingAddress((String)service.getOnlyField(document,"shippingAddress"));
				if((String)service.getOnlyField(document,"orderStatus") != null){
					merchantDataGrid.setOrderStatus(OrderStatus.valueOf((String)service.getOnlyField(document,"orderStatus")));
				}
				merchantDataGrid.setDate((Date)service.getOnlyField(document,"orderDate"));
				merchantDataList.add(merchantDataGrid);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		
		return merchantDataList;
	}
	
	public List<MerchantDataGrid> searchOnOrderDetail(SearchInput input,Long userId){
		
		List<MerchantDataGrid> merchantDataList = new ArrayList<MerchantDataGrid>();
		
		return merchantDataList;
	}
	
	public QueryOptions buildQueryOptions(List<String> snippetFields, SortOptions sortOptions ,int limit, List<String> returnFields,Cursor cursor){
		
		QueryOptions.Builder queryOptionsBuilder  = null;
		try {
			queryOptionsBuilder = QueryOptions.newBuilder();
			queryOptionsBuilder.setLimit(limit);
			if(snippetFields != null){
				for (String snippetField : snippetFields) {
					queryOptionsBuilder.setFieldsToSnippet(snippetField);
				}
			}
			if(returnFields != null){
				for (String returnField : returnFields) {
					queryOptionsBuilder.setFieldsToReturn(returnField);
				}
			}
			
			queryOptionsBuilder.setSortOptions(sortOptions);
			queryOptionsBuilder.setCursor(cursor);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return queryOptionsBuilder.build();
	}
	
	public SortOptions buildSortOptions(String sortExpression, String defaulValue , 
										SortDirection sortDirection, int limit){
		
		SortOptions sortOptions = null;
		
		try {
			sortOptions = SortOptions.newBuilder().addSortExpression(SortExpression.newBuilder()
								.setExpression(sortExpression)
								.setDirection(sortDirection)
								.setDefaultValue(defaulValue))
								.setLimit(limit)
								.build();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		 return sortOptions;
		
	}
	
	public void initialize(Long userId){
		
		setUserIndex(userId);
	}

	private void setUserIndex(Long userId) {
		List<Field> fieldList = null;
		Document document = null;
		//List<Field> childFieldList = null;
		try {
			UserDAOImpl userDao = new UserDAOImpl();
			List<User> userList = userDao.getUserList(userId);     // this userList is basically customer list
			FullTextSearchService searchService = new FullTextSearchService();
			for (User user : userList) {
				fieldList = getDocumentFieldList(user);
				document = searchService.buildDocument(user.getLoginName(), fieldList);
				searchService.indexDocument(document, searchService.getIndex(String.valueOf(userId)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	private List<Field> getChildFields(List<Key> keyList){
		
		if(keyList == null)
			return null;
		Key key = keyList.get(0);
		List<Field> fieldList = new ArrayList<Field>();
		
		AddressDAOImpl addressDAO = null;
		OrderDetailDAOImpl orderDao = null;
		
		try {
			switch (SearchOption.findByValue(key.getKind())) {
			case ADDRESS:
				addressDAO = new AddressDAOImpl();
				Address address = addressDAO.getAddress(key);
				fieldList = getDocumentFieldList(address);
				return fieldList;
				

			case ORDER_DETAIL:
				orderDao = new OrderDetailDAOImpl();
				OrderDetail order = orderDao.getOrderDetail(key);
				fieldList = getDocumentFieldList(order);
				return fieldList;
			
			case MERCHANT:
				break;
			
			default:
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	private List<Field> getChildFields(Key key){
		
		if(key == null)
			return null;
		
		List<Field> fieldList = new ArrayList<Field>();
		AddressDAOImpl addressDAO = null;
		OrderDetailDAOImpl orderDao = null;
		SearchOption option = SearchOption.findByValue(key.getKind());
		try {
			if(option != null){
				switch (option) {
				case ADDRESS:
					addressDAO = new AddressDAOImpl();
					Address address = addressDAO.getAddress(key);
					fieldList = getDocumentFieldList(address);
					return fieldList;
					
	
				case ORDER_DETAIL:
					orderDao = new OrderDetailDAOImpl();
					OrderDetail order = orderDao.getOrderDetail(key);
					fieldList = getDocumentFieldList(order);
					return fieldList;
				
				case MERCHANT:
					break;
				
				default:
					break;
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private List<Field> getDocumentFieldList(Object object){
		
		List<Field> fieldList = new ArrayList<Field>();
		List<Field> childFieldList = null;
		FullTextSearchService searchService = new FullTextSearchService();
		try {
			for (java.lang.reflect.Field property : object.getClass().getDeclaredFields()) {
				property.setAccessible(true);
				
				if(property.getAnnotation(UnIndexSearch.class) == null){
					if(property.getType().equals(List.class)){
						ParameterizedType propertyType = (ParameterizedType) property.getGenericType();
					     Class<?> genericClass = (Class<?>) propertyType.getActualTypeArguments()[0];
					       if(genericClass.equals(Key.class)){
					        	childFieldList = getChildFields((List<Key>)property.get(object));
					        	
					        	if(childFieldList != null){
					        		if(property.getAnnotation(ReferencedName.class) != null){
					        			Field singleField = toSingleDocumentField(childFieldList,property.getName());
					        			childFieldList = new ArrayList<Field>();
					        			childFieldList.add(singleField);
										
									}
					        		fieldList.addAll(childFieldList);
						        }
					        }
					}else if(property.getType().equals(Key.class)){
							childFieldList = getChildFields((Key)property.get(object));
							if(childFieldList != null){
								if(property.getAnnotation(ReferencedName.class) != null){
				        			Field singleField = toSingleDocumentField(childFieldList,property.getName());
				        			childFieldList = new ArrayList<Field>();
				        			childFieldList.add(singleField);
									
								}
				        		fieldList.addAll(childFieldList);
					        }
					}else{
							fieldList.add(searchService.buildField(property.getName(),property.get(object),getDocumentFieldType(property)));
					}
					
					property.setAccessible(false);
				}
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fieldList;
			
	}
	
	public Cursor getCursor(String cursorString){
		
		Cursor.Builder cursorBuilder = null;
		try {
			cursorBuilder = Cursor.newBuilder();
			if(cursorString != null){
				return cursorBuilder.build(cursorString);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cursorBuilder.build();
		
	}
		
	private Field toSingleDocumentField(List<Field> fieldList, String referenceName){
		
		if(fieldList == null){
			return null;
		}
		FullTextSearchService service = new FullTextSearchService();
		StringBuilder sb = new StringBuilder();
		Field fieldInstance = null;
		for (Field field : fieldList) {
		
			switch (field.getType()) {
				case TEXT:
					sb.append((field.getText() == null) ? "": field.getText() + " ");
					break;
				
				case DATE:
					sb.append(field.getDate().toString() == null ? "": field.getDate().toString() + " ");
					break;
					
				case NUMBER:
					sb.append(field.getNumber().toString() == null ? "": field.getNumber().toString() + " ");
					break;
					
				case ATOM:
					sb.append(field.getAtom() == null ? "": field.getAtom() + " ");
					break;
				
				case HTML:
					sb.append(field.getHTML() == null ? "": field.getHTML() + " ");
					break;
				
				case GEO_POINT:
					sb.append(field.getGeoPoint().toString() == null ? "": field.getGeoPoint().toString() + " ");
					break;
	
				default:
					field.getText();
					break;
			}
			
			
		}
		fieldInstance = service.buildField(referenceName,sb.toString(),FieldType.TEXT);
		return fieldInstance;
	}
	
	
	
	private FieldType getDocumentFieldType(java.lang.reflect.Field reflectField){
		Class typeClass = reflectField.getType();
        
		if(reflectField.getType().equals(String.class)){
			return FieldType.TEXT;
		}
		
		if(reflectField.getType().equals(Date.class)){
			return FieldType.DATE;
		}
		
		if(reflectField.getType().equals(Integer.class) || reflectField.getType().equals(Long.class)){
			return FieldType.NUMBER;
		}
		
		return FieldType.TEXT;
	}
	

}
