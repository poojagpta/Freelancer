package com.uwea.jacksondemo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class JacksonObjectMapperExample {
	public static void main(String[] args) throws IOException {
        
     //read json file data to String
      BufferedInputStream bin=new BufferedInputStream(JacksonObjectMapperExample.class.getResourceAsStream("/employee.txt"));
		
		String strFileContents=null; 
		 byte[] contents = new byte[1024];
        int bytesRead=0;
        while( (bytesRead = bin.read(contents)) != -1){ 
            strFileContents = new String(contents, 0, bytesRead);               
        }
         
        byte[] jsonData= strFileContents.getBytes();
        
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
         
        //convert json string to object
        List<Employee> mylist = new ArrayList<Employee>();
        EmployeeList empList=null;
        empList = objectMapper.readValue(jsonData, EmployeeList.class);
        mylist=objectMapper.readValue(JacksonObjectMapperExample.class.getResourceAsStream("/employeeList.txt"), new TypeReference<ArrayList<Employee>>() {});
         
        System.out.println("Employee Object\n"+empList.getEmployeeList().get(0));
        System.out.println("Employee Object\n"+empList.getEmployeeList().get(1));
        System.out.println("Employee List\n"+mylist.get(0));
        System.out.println("Employee List\n"+mylist.get(1));
		
        //convert Object to json string
        Employee emp1 = createEmployee(100);
        Employee emp2 = createEmployee(200);
       List<Employee> employeeList=new ArrayList<Employee> ();
        employeeList.add(emp1);
        employeeList.add(emp2);
        //configure Object mapper for pretty print
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
         
        //writing to console, can write to any output stream such as file
        StringWriter stringEmp = new StringWriter();
        objectMapper.writeValue(stringEmp, employeeList);
      System.out.println("Employee JSON is\n"+stringEmp); 
    }
     
    public static Employee createEmployee(int id) {
 
        Employee emp = new Employee();
        emp.setId(id);
        emp.setName("David");
        emp.setPermanent(false);
        emp.setPhoneNumbers(new long[] { 123456, 987654 });
        emp.setRole("Manager");
 
        Address add = new Address();
        add.setCity("Bangalore");
        add.setStreet("BTM 1st Stage");
        add.setZipcode(560100);
        emp.setAddress(add);
 
        List<String> cities = new ArrayList<String>();
        cities.add("Los Angeles");
        cities.add("New York");
        emp.setCities(cities);
 
        Map<String, String> props = new HashMap<String, String>();
        props.put("salary", "1000 Rs");
        props.put("age", "28 years");
        emp.setProperties(props);
 
        return emp;
    }
 
}