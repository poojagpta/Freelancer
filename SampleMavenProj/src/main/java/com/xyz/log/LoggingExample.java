package com.xyz.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {
	
	private static Logger logger = LoggerFactory.getLogger(LoggingExample.class);
	
	public static void main(String[] args) {
		logger.info("Hello, World!");
	}

}
