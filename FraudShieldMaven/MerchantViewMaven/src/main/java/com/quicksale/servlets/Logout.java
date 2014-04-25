package com.quicksale.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doService(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doService(request, response);
	}
		
	public void doService(HttpServletRequest request,HttpServletResponse response)
		throws ServletException , IOException{
		try{
			request.getSession().removeAttribute("user");
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("userId")){
					cookie.setValue(null);
	                cookie.setMaxAge(0);
	                response.addCookie(cookie);
				}
			}
			response.sendRedirect("index.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
