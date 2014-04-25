package com.quicksale.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.quicksale.bl.BusinessLayer;
import com.quicksale.encrypt.AESEncryptManager;
import com.quicksale.vo.UserVO;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
	
	private FilterConfig fc;
	private ServletContext ctx;
	
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		boolean status = false;
		UserVO userVO = null;
		try {
			Cookie[] cookies   = req.getCookies();
			if(cookies == null || cookies.length == 0){
				chain.doFilter(req, res);
			}else{
				BusinessLayer bl = new BusinessLayer();
				
				for(Cookie cookie : cookies){
		
					if(cookie.getName().equals("Id/Name")){
					
						String encryptedValue = cookie.getValue();
						String decryptedValue =  AESEncryptManager.decrypt(encryptedValue);
						String[] array = decryptedValue.split("####");
						if(array[0].equals(userName) && array[1].equals(password)){
							status = true;
						}
					}
					if(cookie.getName().equals("userId")){
						String userId = null;
						userId = AESEncryptManager.decrypt(cookie.getValue());
						userVO = bl.findUserById(Long.parseLong(userId));
					}
			 	}
			
			} 
			if(status && userVO != null){
				req.getSession().setAttribute("user", userVO);
				res.sendRedirect("home.jsp");
				return;
			}else{
			  chain.doFilter(req, res);
			}
		}catch (Exception e) {
				chain.doFilter(req, res);
			}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.fc = fConfig;
		ctx = fc.getServletContext();
	}

}
