package com.quicksale.datastore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Transaction;
import com.quicksale.jdo.Qsession;
import com.quicksale.jdo.User;
import com.quicksale.jdo.UserLogin;
import com.quicksale.vo.QsessionVO;
import com.quicksale.vo.UserLoginVO;
import com.quicksale.vo.UserVO;

public class DataStoreDAOImpl {
	static DatastoreService datastore ;
	static{
		 datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	public static User saveUser(User user) throws IOException {
		Entity userEntity = makeUserEntity(user);
		datastore.put(userEntity);
		Key key = userEntity.getKey();
		user.setId(key.getId());
		return user;
	}

	
	public static UserLogin saveUserLogin(UserLogin userLogin) throws IOException {
		Entity userLoginEntity = makeUserLoginEntity(userLogin);
		datastore.put(userLoginEntity);
		Key key = userLoginEntity.getKey();
		//userLogin.setUserLoginId(key.getId());
		return userLogin;
	}

	
	public static UserLogin saveUserLogin(UserLogin userLogin, User user)
			throws IOException {
		Entity userEntity = null;
		Key userEntityKey = null;
		try {
			userEntityKey = KeyFactory.createKey("User", user.getId());
			userEntity = datastore.get(userEntityKey);
			Key key = KeyFactory.createKey("UserLogin", userLogin.getLoginName());
			Entity entity = datastore.get(key);
			return null;
		} catch (EntityNotFoundException e) {
			Entity userLoginEntity = makeUserLoginEntity(userLogin);
			userLoginEntity.setProperty("userId", user.getId());
			datastore.put(userLoginEntity);
			Key userLoginKey = userLoginEntity.getKey();
			userEntity.setProperty("userLoginId", userLoginKey.getId());
			//Entity userEntity = makeUserEntity(user);
			datastore.put(userEntity);
			userLogin.setId(userLoginKey.getId());
			return userLogin;
		}
	}

	
	public static Qsession saveQsession(Qsession qSession) throws IOException {
			try{
				Key key = KeyFactory.createKey("Qsession", qSession.getSessionName());
				Entity entity = datastore.get(key);
				return null;
			}catch(EntityNotFoundException e){
				Entity qSessionEntity = makeQsessionEntity(qSession);
				datastore.put(qSessionEntity);
				return qSession;
			}
		
	}

	
	public static Qsession saveQsession(Qsession qSession, UserLogin userLogin)
			throws IOException {
		Entity userLoginEntity = null;
			try{
				Key userLoginKey = KeyFactory.createKey("UserLogin", userLogin.getId());
				userLoginEntity = datastore.get(userLoginKey);
				Key key = KeyFactory.createKey("Qsession", qSession.getSessionName());
				Entity entity = datastore.get(key);
				return null;
			}catch(EntityNotFoundException e){
				Entity qSessionEntity = makeQsessionEntity(qSession);
				qSessionEntity.setProperty("userLoginId", userLogin.getId());
				datastore.put(qSessionEntity);
				userLoginEntity.setProperty("sessionName",qSession.getSessionName());
				datastore.put(userLoginEntity);
				return qSession;
			}
	}
	
	
	public static UserLoginVO findByUserName(String userName)throws NullPointerException{
			Entity entity = null;
			Query query = new Query("UserLogin").addFilter("loginName", Query.FilterOperator.EQUAL, userName);
			entity = datastore.prepare(query).asSingleEntity();
			if(entity == null){
				//throw new NullPointerException("Could not find Entity");
				return null;
			}
			return makeUserLogin(entity);
	}
	
	
	
	public static UserVO findUserById(Long userId)throws EntityNotFoundException{
		Key key = KeyFactory.createKey("User", userId);
		Entity entity = datastore.get(key);
		if(entity == null){
			throw new RuntimeException("Entity Not Found Exception");
		}
		return makeUserVO(entity);
	}
	
	
	
	public static java.util.List<QsessionVO> findQsessionByUserLoginId(Long userLoginId)throws NullPointerException{
		List<QsessionVO> sessionList = new ArrayList<QsessionVO>();
		Query query = new Query("Qsession").addFilter("userLoginId", Query.FilterOperator.EQUAL, userLoginId);
		Iterable<Entity> entities = datastore.prepare(query).asIterable();
		if(entities == null){
			throw new NullPointerException("could not find any previous session with userLoginId"+ userLoginId);
		}
		for(Entity entity : entities){
			sessionList.add(makeQsessionVO(entity));
		}
		return sessionList;
	}
	
	public static QsessionVO updateSession(QsessionVO vo){
		Transaction txn = datastore.beginTransaction();
		Entity entity = makeSessionEntity(vo);
		System.out.println(entity.getKey().getName());
		try{
		datastore.put(entity);
		txn.commit();
		}finally{
			if(txn.isActive()){
				txn.rollback();
			}
		}
		return makeQsessionVO(entity);
	}
	
	public static Entity makeSessionEntity(QsessionVO vo){
		Key key = KeyFactory.createKey("Qsession", vo.getSessionName());
		Entity entity = new Entity("Qsession",key.getName());
		entity.setProperty("sessionText", vo.getSession());
		entity.setProperty("userLoginId", vo.getUserLoginId());
		entity.setProperty("lastIp", vo.getLastIp());
		entity.setProperty("userId", vo.getUserId());
		return entity;
	}
	
	public static QsessionVO findQsessionByIdName(String name)throws EntityNotFoundException{
		Key key = KeyFactory.createKey("Qsession", name);
		Entity entity = datastore.get(key);
		if(entity == null){
			//throw new RuntimeException("Entity Not Found Exception");
			return null;
		}
		return makeQsessionVO(entity);
	}
	
	
	
	public static UserVO findUserByLoginId(Long userLoginId)throws NullPointerException{
		Entity entity = null;
		Query query = new Query("User").addFilter("userLoginId", Query.FilterOperator.EQUAL, userLoginId);
		entity = datastore.prepare(query).asSingleEntity(); 
		if(entity == null){
			throw new NullPointerException ("could not find user with LoginId = "+userLoginId);
		}
		return makeUserVO(entity);
	}
	

	
	
	public static QsessionVO makeQsessionVO(Entity entity){
		QsessionVO qSessionVO = new QsessionVO();
		qSessionVO.setSessionName(entity.getKey().getName());
		qSessionVO.setSession((Text)entity.getProperty("sessionText"));
		qSessionVO.setUserId((Long)entity.getProperty("userId"));
		qSessionVO.setUserLoginId((Long)entity.getProperty("userLoginId"));
		qSessionVO.setLastIp(entity.getProperty("lastIp").toString());
		return qSessionVO;
	}
	
	
	 
	public static UserLoginVO makeUserLogin(Entity entity){
		UserLoginVO userLoginVO = new UserLoginVO();
		userLoginVO.setId(entity.getKey().getId());
		userLoginVO.setCurrentIp(entity.getProperty("currentIp").toString());
		Object object = entity.getProperty("expirationDate");
		Date date = null;
		if(object != null){
			 date = (Date)object;
			 userLoginVO.setExpirationDate(date);
		}
		else{
			userLoginVO.setExpirationDate(null);
		}
		userLoginVO.setLoginName(entity.getProperty("loginName").toString());
		userLoginVO.setPassword(entity.getProperty("password").toString());
		userLoginVO.setSessionId(entity.getProperty("sessionName").toString());
		userLoginVO.setUserId((Long)entity.getProperty("userId"));
		return userLoginVO; 
	}
	
	
	public static Entity makeUserEntity(User user){
		Entity entity = new Entity("User");
		//entity.setProperty("userId",user.getUserId());
		entity.setProperty("firstName",user.getFirstName());
		entity.setProperty("lastName", user.getLastName());
		entity.setProperty("address", user.getAddress());
		entity.setProperty("city", user.getCity());
		entity.setProperty("state", user.getState());
		entity.setProperty("country", user.getCountry());
		entity.setProperty("email", user.getEmail());
		entity.setProperty("mobile", user.getMobile());
		entity.setProperty("verified", user.getVerified());
		entity.setProperty("userLoginId", user.getUserLoginId());
		entity.setProperty("isActive", user.getIsActive());
		entity.setProperty("createdOn", user.getCreatedOn());
		entity.setProperty("modifiedOn", user.getModifiedOn());
		return entity;
	}
	
	
	public static Entity makeUserLoginEntity(UserLogin userLogin){
		Entity entity = new Entity("UserLogin");
		entity.setProperty("loginName", userLogin.getLoginName());
		entity.setProperty("password", userLogin.getPassword());
		entity.setProperty("currentIp", userLogin.getCurrentIp());
		entity.setProperty("sessionName", userLogin.getSessionName());
		entity.setProperty("expirationDate",userLogin.getExpirationDate());
		entity.setProperty("userId",userLogin.getUserId());
		return entity;
	}
	
	public static Entity makeQsessionEntity(Qsession qSession){
		Entity entity = new Entity("Qsession",qSession.getSessionName());
		entity.setProperty("sessionText", qSession.getSession());
		entity.setProperty("userLoginId",qSession.getUserLoginId());
		entity.setProperty("userId",qSession.getUserId());
		entity.setProperty("lastIp", qSession.getLastIp());
		return entity;
	}
	
	public static UserVO makeUserVO(Entity entity){
		UserVO vo = new UserVO();
		
		vo.setId((Long)entity.getKey().getId());
		vo.setFirstName(entity.getProperty("firstName").toString());
		vo.setLastName(entity.getProperty("lastName").toString());
		vo.setAddress(entity.getProperty("address").toString());
		vo.setCity(entity.getProperty("city").toString());
		vo.setState(entity.getProperty("state").toString());
		vo.setCountry(entity.getProperty("country").toString());
		vo.setEmail(entity.getProperty("email").toString());
		vo.setMobile(entity.getProperty("mobile").toString());
		vo.setVerified(entity.getProperty("verified").toString());
		vo.setUserLoginId((Long)entity.getProperty("userLoginId"));
		vo.setIsActive(entity.getProperty("isActive").toString());
		vo.setCreatedOn((Date)entity.getProperty("createdOn"));
		vo.setModifiedOn((Date)entity.getProperty("modifiedOn"));
		return vo;
	}
}
