package com.quicksale.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;
import com.quicksale.bl.BusinessLayer;
import com.quicksale.encrypt.AESEncryptManager;
import com.quicksale.encrypt.QuickSaleBase64;
import com.quicksale.vo.QsessionVO;
import com.quicksale.vo.UserLoginVO;
import com.quicksale.vo.UserVO;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			PrintWriter out = response.getWriter();
			try{
				String userName = request.getParameter("userName").trim();
				String password = request.getParameter("password").trim();
				String ip 		= request.getRemoteAddr();
				
				String userAgent = request.getHeader("User-Agent");
				if(userName == null || userName.equals("") || password == null || password.equals("")){
					//response.sendRedirect("index.jsp");
					out.write("Please enter a valid username and password");
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}
				
				BusinessLayer bl = new BusinessLayer();
				UserLoginVO loginVO = null;
				QsessionVO sessionVO = null;
					
				loginVO = bl.findByUserName(userName);
				if(loginVO == null || !loginVO.getPassword().equals(password)){
					out.write("Please enter a valid username and password");
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}
					
				String cookie = QuickSaleBase64.encode(ip+"####"+userName+"####"+userAgent);
				String base64 = QuickSaleBase64.encode(userAgent+"####"+userName);
				String sessionName = AESEncryptManager.encrypt(userName+"####"+password);
				sessionVO = bl.findQsessionByIdName(sessionName);
				
				 List<Map<String,String>> superList = bl.generateCookieMapFromSession(sessionVO.getSession().getValue());
				 System.out.println(superList.size());
				 boolean status  = false;
					
				 for(Map<String,String> cookieMap : superList){
						String value = cookieMap.get("userAgentUserName");
						System.out.println("");
						System.out.println("value   ="+value);
						System.out.println("base64  ="+base64);
						System.out.println("");
				
						if(value.equals(base64)){
							status = true;	
							System.out.println("Yes its equal");
							for(String key : cookieMap.keySet()){
								if(key.equals("session")){
									if(!cookieMap.get(key).equals(cookie)){
										sessionVO.setLastIp(ip);
										bl.updateSession(sessionVO);
									}
								}
								Cookie httpcookie = new Cookie(key,cookieMap.get(key));
								response.addCookie(httpcookie);
										//bl.updateSession(sessionVO);
							}
							UserVO userVO = bl.findUserById(sessionVO.getUserId());
							request.getSession().setAttribute("user",userVO);
							response.sendRedirect("home.jsp");
							return;
						}
				 	}
				 
				 if(!status){
					 	String encUserPassword = AESEncryptManager.encrypt(userName+"####"+password);
					 	StringBuilder cookies = new StringBuilder("Id/Name::::"+encUserPassword);
					 	String encUserId = AESEncryptManager.encrypt(sessionVO.getUserId().toString());
					 	cookies.append(";;;;userId::::"+encUserId);
						cookies.append(";;;;session::::"+cookie);
						StringBuilder sb =  new StringBuilder(sessionVO.getSession().getValue());
						String latestCookies = base64+"~~~~"+cookies.toString();
						Text sessionText = new Text(sb.toString()+"####"+latestCookies);
						sessionVO.setSession(sessionText);
						bl.updateSession(sessionVO);
						Cookie cookie1 = new Cookie("Id/Name",encUserPassword);
						Cookie cookie2 = new Cookie("userId",encUserId);
						Cookie cookie3 = new Cookie("session",cookie);
						Cookie cookie4 = new Cookie("userAgentUserName",base64);
						response.addCookie(cookie1);
						response.addCookie(cookie2);
						response.addCookie(cookie3);
						response.addCookie(cookie4);
						
						
						UserVO userVO = bl.findUserById(sessionVO.getUserId());
						request.getSession().setAttribute("user",userVO);
						response.sendRedirect("home.jsp");
						
						
				}

			}catch(Exception e){
				e.printStackTrace();
			}
	}
}

