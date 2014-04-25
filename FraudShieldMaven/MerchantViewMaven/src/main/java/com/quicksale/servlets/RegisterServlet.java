package com.quicksale.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;
import com.quicksale.bl.BusinessLayer;
import com.quicksale.encrypt.AESEncryptManager;
import com.quicksale.encrypt.QuickSaleBase64;
import com.quicksale.jdo.Qsession;
import com.quicksale.jdo.User;
import com.quicksale.jdo.UserLogin;
import com.quicksale.vo.QsessionVO;
import com.quicksale.vo.UserLoginVO;
import com.quicksale.vo.UserVO;

public class RegisterServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		try{
			
			String password = req.getParameter("password").trim();
			String userName = req.getParameter("userName").trim();
			String ip = req.getRemoteAddr();
			//String ip = "117.199.154.138";
			String userAgent = req.getHeader("User-Agent");
			
			if (userAgent != null && userAgent.toLowerCase().indexOf("msie") != -1){
				System.out.println(userAgent);
			}
			BusinessLayer bl = new BusinessLayer();
			UserLoginVO userLoginVO = bl.findByUserName(userName);
			
			if(userLoginVO == null){
				
				UserVO userVO = toUserVo(req);
				
				User user = bl.saveUser(userVO);
				
				userLoginVO = toUserLoginVO(req, password,userName, user);
				
				UserLogin userLogin = bl.saveUserLogin(userLoginVO,user);
			
				String encUserPassword = AESEncryptManager.encrypt(userName+"####"+password);
				String base64 = QuickSaleBase64.encode(userAgent+"####"+userName);
				String cookie = QuickSaleBase64.encode(ip+"####"+userName+"####"+userAgent);
				StringBuilder cookies = new StringBuilder("Id/Name::::"+encUserPassword);
				cookies.append(";;;;userId::::"+AESEncryptManager.encrypt(user.getId().toString()));
				cookies.append(";;;;session::::"+cookie);
				Text sessionText = new Text(base64+"~~~~"+cookies.toString());
				
				QsessionVO qSessionVO = toQsessionVO(ip, user, userLogin,
						encUserPassword, sessionText);
				System.out.println("about to save session");
				Qsession qsession = bl.saveQsession(qSessionVO,userLogin);
				out.write("User saved in datastore");
				
			}else{
				out.write("Sorry! we have user with userName "+userName);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		req.getRequestDispatcher("/login").forward(req, resp);
	}

	
	private UserLoginVO toUserLoginVO(HttpServletRequest req, String password,
										String userName, User user) {
		UserLoginVO userLoginVO = new UserLoginVO();
		userLoginVO.setCurrentIp(req.getRemoteAddr());
		userLoginVO.setLoginName(userName);
		userLoginVO.setPassword(password);
		userLoginVO.setUserId(user.getId());
		return userLoginVO;
	}

	private QsessionVO toQsessionVO(String ip, User user, UserLogin userLogin,
									String encUserPassword, Text sessionText) 
	{
		QsessionVO qSessionVO = new QsessionVO();
		qSessionVO.setSessionName(encUserPassword);
		qSessionVO.setSession(sessionText);
		qSessionVO.setUserId(user.getId());
		qSessionVO.setUserLoginId(userLogin.getId());
		qSessionVO.setLastIp(ip);
		return qSessionVO;
	}
	

	private UserVO toUserVo(HttpServletRequest request) 
	{
		String firstName = request.getParameter("firstName").trim();
		String lastName = request.getParameter("lastName").trim();
		String address = request.getParameter("address").trim();
		String city = request.getParameter("city").trim();
		String state = request.getParameter("state").trim();
		String country = request.getParameter("country").trim();
		String email = request.getParameter("email").trim();
		String mobile = request.getParameter("mobile").trim();
		
		UserVO userVO = new UserVO();
		userVO.setFirstName(firstName);
		userVO.setLastName(lastName);
		userVO.setAddress(address);
		userVO.setCity(city);
		userVO.setState(state);
		userVO.setCountry(country);
		userVO.setEmail(email);
		userVO.setMobile(mobile);
		userVO.setVerified("false");
		userVO.setCreatedOn(new Date());
		userVO.setIsActive("false");
		userVO.setModifiedOn(new Date());
		return userVO;
	}
	
	
}
