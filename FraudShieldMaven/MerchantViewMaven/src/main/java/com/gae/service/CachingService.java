package com.gae.service;

import java.util.logging.Level;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheService.IdentifiableValue;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class CachingService {

		static MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();	
	
		public static Object readFromCache(Object key){
	
		
			byte[] value;

			// Using the synchronous cache
			
			syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
			value = (byte[]) syncCache.get(key); // read from cache
			if (value == null) {
			      // get value from other source
			      // ........
			    writeToCache(key, null, value, null);
			}

			// Using the asynchronous cache
			
			/*AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService();
			asyncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
			Future<Object> futureValue = asyncCache.get(key); // read from cache
			// ... do other work in parallel to cache retrieval
			value = (byte[]) futureValue.get();
			if (value == null) {
			      // get value from other source
			      // ........

			      // asynchronously populate the cache
			      // Returns a Future<Void> which can be used to block until completion
			      asyncCache.put(key, value);
			}*/
			return value;
			
		}
		
		public static void writeToCache(Object key,IdentifiableValue oldValue,Object value,Expiration expires){
			
			syncCache.putIfUntouched(key, oldValue, value, expires);
		}
		
		
}
