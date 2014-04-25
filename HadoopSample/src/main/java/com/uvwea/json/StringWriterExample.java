package com.uvwea.json;



import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class StringWriterExample {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        Map<String,String> map = new HashMap<String,String>();
        map.put("key1","value1");
        map.put("key2","value2");

        StringWriter stringWriter = new StringWriter();

        objectMapper.writeValue(stringWriter, map);

        System.out.println(stringWriter.toString());
        
        Map<String, Object> mapObject = new HashMap<String, Object>();

		mapObject.put("domain", "JavaCodeGeeks.com");
		mapObject.put("interest", "Java");

		mapObject.put("Members", 400);

		List<Object> myList = new ArrayList<Object>();

	/*	myList.add("Jonh");
		myList.add("Jack");
		myList.add("James");

		mapObject.put("names", myList);

		try {
			objectMapper.writeValue(new File(jsonFilePath), mapObject);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
    }
}