package com.gae.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.quicksale.vo.UserVO;

public class DataStoreServlet extends HttpServlet{
	 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
       doPost(request, response);
    }
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
    /* UserService userService = UserServiceFactory.getUserService();
     User user = userService.getCurrentUser();
*/
     // We have one entity group per Guestbook with all Greetings residing
     // in the same entity group as the Guestbook to which they belong.
     // This lets us run a transactional ancestor query to retrieve all
     // Greetings for a given Guestbook.  However, the write rate to each
     // Guestbook should be limited to ~1/second.
     /*String guestbookName = "Test guestbook";
     Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
     String content = "You are welcome";
     Date date = new Date();
     Entity greeting = new Entity("Greeting", guestbookKey);
     //greeting.setProperty("user", user);
     greeting.setProperty("date", date);
     greeting.setProperty("content", content);
     
     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
     datastore.put(greeting);
*/
		String kind = request.getParameter("kind");
		
		String[] requestParams = request.getParameterValues("property");
		String temp[] = new String[2];
		String propertyName = null;
		String propertyValue = null;
		Entity entity = null;
		if(requestParams != null){
			//Key key = KeyFactory.createKey(kind,requestParams[0].split("/")[1]);
			entity = new Entity(kind,requestParams[0].split("/")[1]);
			for (String property : requestParams) {
				if(!property.equals("")){
					temp = property.split("/");
					propertyName = temp[0];
					propertyValue = temp[1];
					entity.setProperty(propertyName, propertyValue);
				}
			}
		}	
		if(request.getParameterValues("childEntity") != null && 
				!request.getParameterValues("childEntity").equals("")){
		String[] childEntity = request.getParameterValues("childEntity");
		Map<String,List<Key>> childEntityMap = new HashMap<String,List<Key>>();
		List<Key> childEntityList = null;
	
		for (String string : childEntity) {
			if(!string.equals("")){
				temp = string.split("/");
				if(childEntityMap.get(temp[0]) != null){
					childEntityList = childEntityMap.get(temp[0]);
					
				}else{
					childEntityList = new ArrayList<Key>();
					
				}
				childEntityList.add(KeyFactory.stringToKey(temp[1]));
			}
			childEntityMap.put(temp[0], childEntityList);
		}
		
			for (String referencedKind : childEntityMap.keySet()) {
				entity.setProperty(referencedKind,childEntityMap.get(referencedKind));
			}
	
		}
		UserVO user = (UserVO)request.getSession().getAttribute("user") ;
		if(user != null){
			entity.setProperty("userId", user.getId());
		}else{
			response.getWriter().write("<html><h3><body>Please login to create entity<h3></body></html>");
			response.sendRedirect("/index.jsp");
			return;
		}
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entity);
		response.sendRedirect("/success.jsp");
 }

}
