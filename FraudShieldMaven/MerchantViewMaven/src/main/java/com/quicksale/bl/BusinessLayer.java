package com.quicksale.bl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.quicksale.datastore.DataStoreDAOImpl;
import com.quicksale.jdo.Qsession;
import com.quicksale.jdo.User;
import com.quicksale.jdo.UserLogin;
import com.quicksale.vo.QsessionVO;
import com.quicksale.vo.UserLoginVO;
import com.quicksale.vo.UserVO;



public class BusinessLayer {	
	public User saveUser(UserVO userVO){
		User user = null;
		try{
		user = makeUserJDO(userVO);
		user = DataStoreDAOImpl.saveUser(user);
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}
	
	
	public User makeUserJDO(UserVO userVO){
		User user = new User();
		user.setAddress(userVO.getAddress());
		user.setCity(userVO.getCity());
		user.setCountry(userVO.getCountry());
		user.setEmail(userVO.getEmail());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setMobile(userVO.getMobile());
		user.setState(userVO.getState());
		user.setCreatedOn(userVO.getCreatedOn());
		user.setVerified(userVO.getVerified());
		user.setIsActive(userVO.getIsActive());
		user.setModifiedOn(userVO.getModifiedOn());
		return user;
	}
	
	
	public UserLogin saveUserLogin(UserLoginVO userLoginVO){
		UserLogin userLogin = null;
		try{
			userLogin = makeUserLogin(userLoginVO);
			userLogin = DataStoreDAOImpl.saveUserLogin(userLogin);
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return userLogin;
	}
	
	public UserLogin saveUserLogin(UserLoginVO userLoginVO,User user){
		UserLogin userLogin = null;
		try{
			userLogin = makeUserLogin(userLoginVO);
			userLogin = DataStoreDAOImpl.saveUserLogin(userLogin,user);
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return userLogin;
	}
	
	
	public Qsession saveQsession(QsessionVO sessionVO,UserLogin userLogin){
		Qsession qSession = null;
		try{
			qSession = makeQsession(sessionVO);
			qSession = DataStoreDAOImpl.saveQsession(qSession, userLogin);
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return qSession;
	}
	
	
	public UserLoginVO findByUserName(String userName){
		UserLoginVO vo = null;
		try{
			vo =  DataStoreDAOImpl.findByUserName(userName);	
		}catch(NullPointerException e){
			throw new NullPointerException("Could not find User");
		}
		return vo;
	}
	
	public UserVO findUserByLoginId(Long userLoginId){
		UserVO vo = null;
		try{
			vo = DataStoreDAOImpl.findUserByLoginId(userLoginId);
		}catch(NullPointerException e){
			throw new NullPointerException("Could not find User");
		}
		return vo;
	}
	
	
	public  QsessionVO updateSession(QsessionVO vo){
		return DataStoreDAOImpl.updateSession(vo);
	}
	
	
	public List<QsessionVO> findQsessionByUserLoginId(Long userLoginId){
		List<QsessionVO> sessionList = null;
		try{
			sessionList = DataStoreDAOImpl.findQsessionByUserLoginId(userLoginId);
		}catch(NullPointerException e){
			throw new NullPointerException("Could not find Session");
		}
		return sessionList;
	}
	
	public QsessionVO findQsessionByIdName(String key){
		QsessionVO qSessionVO = null;
		try{
			qSessionVO = DataStoreDAOImpl.findQsessionByIdName(key);
		}catch(EntityNotFoundException e){
			throw new RuntimeException(e.getMessage());
		}catch(RuntimeException e){
			throw new RuntimeException(e.getMessage());
		}
		return qSessionVO;
	}
	
	
	
	public String handleSessionLogic(String cookies,String userCookie){
		try{
			String[] array = cookies.split("####");
			for(String s : array){
				
			}
		}catch(NullPointerException e){
			
		}
		return null;
		
	}
	
	
	public UserVO findUserById(Long userId)throws EntityNotFoundException{
		UserVO userVO = null;
		userVO = DataStoreDAOImpl.findUserById(userId);
		if(userVO == null){
			throw new RuntimeException();
		}
		return userVO;
	}
	
	
	public UserLogin makeUserLogin(UserLoginVO userLoginVO){
		UserLogin userLogin = new UserLogin();
		userLogin.setLoginName(userLoginVO.getLoginName());
		userLogin.setPassword(userLoginVO.getPassword());
		userLogin.setUserId(userLoginVO.getUserId());
		userLogin.setSessionName(userLoginVO.getSessionId());
		userLogin.setExpirationDate(userLoginVO.getExpirationDate());
		userLogin.setCurrentIp(userLoginVO.getCurrentIp());
		return userLogin;
	}
	
	
	public Qsession saveQsession(QsessionVO sessionVO)throws IOException{
		Qsession qSession = makeQsession(sessionVO);
		return DataStoreDAOImpl.saveQsession(qSession);
	}
	
	public Qsession makeQsession(QsessionVO sessionVO){
		Qsession qSession = new Qsession();
		qSession.setSessionName(sessionVO.getSessionName());
		qSession.setSession(sessionVO.getSession());
		qSession.setUserId(sessionVO.getUserId());
		qSession.setLastIp(sessionVO.getLastIp());
		qSession.setUserLoginId(sessionVO.getUserLoginId());
		return qSession;
	}
	
	public QsessionVO makeQsessionVO(Qsession qsession){
		QsessionVO vo = new QsessionVO();
		vo.setSessionName(qsession.getSessionName());
		vo.setSession(qsession.getSession());
		vo.setUserId(qsession.getUserId());
		vo.setLastIp(qsession.getLastIp());
		vo.setUserLoginId(qsession.getUserLoginId());
		return vo;
	}
	
	/** parsing session items and providing in Map  **/
	// Why it is returning a map . Either for different user agents ?? Need to check it out.
	public  List<Map<String,String>> generateCookieMapFromSession(String sessionValue){
		Map<String,String> cookieMap = new HashMap<String,String>();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String[] sessions = sessionValue.split("####");
		for(String item : sessions){
			String[] arr1 = item.split("~~~~");
			String userAgentName = arr1[0];
			cookieMap.put("userAgentUserName",userAgentName);
			String[] keyValues = arr1[1].split(";;;;");
			for(String str : keyValues){
				String[] arr = str.split("::::");
				cookieMap.put(arr[0], arr[1]);
			}
			list.add(cookieMap);
		}
		return list;
	}
	
	/*public static Map<String,String> getMapFromSession(QsessionVO sessionVO)throws Exception{
			Map<String,String> cMap = new HashMap<String,String>();
			cMap.put("Id/Name", sessionVO.getSessionName());
			cMap.put("userId", AESEncryptManager.encrypt(sessionVO.getUserId().toString()));
			cMap.put("session", sessionVO.getSession());
			
		return null;
	}*/
	
	/*public static String getValueFromText(Text text , String name){
			String[] arr1 = text.getValue().split("====");
			String userAgentName = arr1[0];
			cookies.put("userAgentUserName",userAgentName);
			String[] keyValues = arr1[1].split(";;;;");
			for(String str : keyValues){
				String[] arr = str.split("::::");
				cookies.put(arr[0], arr[1]);
			}
			list.add(cookies);
		
		return null;
	}*/
	

	
}
