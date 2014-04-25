package com.uwea.jacksondemo;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Jackson tree is like the DOM parser which take in memeory.
 * Useful when want to reterive few nodes.
 * Edit Add/Remove nodes from data
 * @author ashok
 *
 */
public class JacksonTree {
	public static void main(String[] str) throws JsonProcessingException, IOException{
				 
		//create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();
		 
		//read JSON like DOM Parser
		JsonNode rootNode = objectMapper.readTree(JacksonObjectMapperExample.class.getResourceAsStream("/employeeList.txt"));
		Iterator<JsonNode> iterator=rootNode.elements();
		int count=0;
		while(iterator.hasNext()){
		
			JsonNode idNode = rootNode.get(count).path("id");
			System.out.println("id = "+idNode.asInt());
			 
			JsonNode phoneNosNode = rootNode.get(count).path("phoneNumbers");
			Iterator<JsonNode> elements = phoneNosNode.elements();
			while(elements.hasNext()){
			    JsonNode phone = elements.next();
			    System.out.println("Phone No = "+phone.asLong());
			}

			iterator.next();
			count++;
		}
		
		//Add Node (Test)
		//remove Node (role)
		//Edit value of node "id"
		 
		//create JsonNode
		rootNode = objectMapper.readTree(JacksonObjectMapperExample.class.getResourceAsStream("/employeeList.txt"));
		 
		//update JSON data
		((ObjectNode) rootNode.get(0)).put("id", 500);
		//add new key value
		((ObjectNode) rootNode.get(0)).put("test", "test value");
		//remove existing key
		((ObjectNode) rootNode.get(0)).remove("role");
		((ObjectNode) rootNode.get(0)).remove("properties");
		objectMapper.writeValue(new File("C:\\Users\\ashok\\Desktop\\Aviation\\updated_emp.txt"), rootNode);
		
	}

}
