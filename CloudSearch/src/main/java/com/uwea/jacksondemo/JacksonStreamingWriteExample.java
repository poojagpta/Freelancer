package com.uwea.jacksondemo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

public class JacksonStreamingWriteExample {
	public static void main(String[] args) throws IOException {
		Employee emp1 = JacksonObjectMapperExample.createEmployee(100);
		Employee emp2 = JacksonObjectMapperExample.createEmployee(200);
		List<Employee> empList = new ArrayList<Employee>();
		empList.add(emp1);
		empList.add(emp2);

		JsonGenerator jsonGenerator = new JsonFactory()
				.createGenerator(new FileOutputStream(
						"C:\\Users\\ashok\\Desktop\\Aviation\\stream_emp.txt"));
		// for pretty printing
		jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());

		jsonGenerator.writeStartObject(); // start root object
		jsonGenerator.writeArrayFieldStart("EmployeeList");
		for (Employee emp : empList) {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeNumberField("id", emp.getId());
			jsonGenerator.writeStringField("name", emp.getName());
			jsonGenerator.writeBooleanField("permanent", emp.isPermanent());

			jsonGenerator.writeObjectFieldStart("address"); // start address
															// object
			jsonGenerator.writeStringField("street", emp.getAddress()
					.getStreet());
			jsonGenerator.writeStringField("city", emp.getAddress().getCity());
			jsonGenerator.writeNumberField("zipcode", emp.getAddress()
					.getZipcode());
			jsonGenerator.writeEndObject(); // end address object

			jsonGenerator.writeArrayFieldStart("phoneNumbers");
			for (long num : emp.getPhoneNumbers())
				jsonGenerator.writeNumber(num);
			jsonGenerator.writeEndArray();

			jsonGenerator.writeStringField("role", emp.getRole());

			jsonGenerator.writeArrayFieldStart("cities"); // start cities array
			for (String city : emp.getCities())
				jsonGenerator.writeString(city);
			jsonGenerator.writeEndArray(); // closing cities array.

			jsonGenerator.writeObjectFieldStart("properties");
			Set<String> keySet = emp.getProperties().keySet();
			for (String key : keySet) {
				String value = emp.getProperties().get(key);
				jsonGenerator.writeStringField(key, value);
			}
			jsonGenerator.writeEndObject(); // closing properties
			jsonGenerator.writeEndObject();
		}

		jsonGenerator.writeEndArray(); // Ending of iterator employee
		jsonGenerator.writeEndObject(); // closing root object

		jsonGenerator.flush();
		jsonGenerator.close();
	}
}
