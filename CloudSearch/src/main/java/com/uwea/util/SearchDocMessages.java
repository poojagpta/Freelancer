package com.uwea.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import com.uwea.Servlet.Messages;

public class SearchDocMessages {
	public static final String FILE_NAME = Messages.getString("SearchDocFile"); //$NON-NLS-1$
	private Properties prop= new Properties();


	private SearchDocMessages() {
		
		 FileReader reader;
		try {
			reader = new FileReader(FILE_NAME);
			prop.load(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	}

	public static String getString(String key) {
		try {
			SearchDocMessages message=new SearchDocMessages();
			return message.prop.getProperty(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
