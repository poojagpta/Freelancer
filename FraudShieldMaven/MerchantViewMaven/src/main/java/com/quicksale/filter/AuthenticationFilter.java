package com.quicksale.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.quicksale.bl.BusinessLayer;
import com.quicksale.encrypt.AESEncryptManager;
import com.quicksale.encrypt.QuickSaleBase64;
import com.quicksale.vo.UserLoginVO;
import com.quicksale.vo.UserVO;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class AuthenticationFilter implements Filter {
	
	private FilterConfig fc;
	private ServletContext ctx;
	
    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
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
	
		Cookie[] cookies   = req.getCookies();
		if(cookies.length == 0  || cookies == null){
			chain.doFilter(req, res);
		}
		
		else{
			String  decryptedStringValue = null;
			BusinessLayer bl = new BusinessLayer();
			
			for(Cookie cookie : cookies){
				
				if(cookie.getName().equals("UserId")){
					String userId = null;
					try{
						userId = AESEncryptManager.decrypt(cookie.getValue());
						request.setAttribute("userId", userId);
						//userVO = bl.findUserById(Long.parseLong(userId));
					}catch(Exception e){
						chain.doFilter(req, res);
					}
				}else{
					chain.doFilter(request, response);
				}
			}
			
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
