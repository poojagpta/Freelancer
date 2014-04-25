package com.uwea.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.uwea.bo.DocumentBO;
import com.uwea.cache.IndexFieldCache;

public class DocumentUtil {

	public static StringWriter generateJsonObject(String filepath,String urlpath) {

		List<String> filesNames = new ArrayList<String>();
		File file = new File(filepath);
		filesNames = listFilesForFolder(file, filesNames);

		try {

			ContentHandler handler = new DefaultHandler();
			List<DocumentBO> documentBOs = new ArrayList<DocumentBO>();

			// Loop through all the files
			for (String fileName : filesNames) {

				// Find extension of all files
				// String fileExtension=documentData.findExtension(fileName);

				Metadata metadata = new Metadata();
				InputStream input = new FileInputStream(new File(fileName));
				DocumentBO documentBO = new DocumentBO();
				documentBO.setId(fileName.replace(file.separatorChar + "", "_")
						.replace(".", "_").replace(":", "_").toLowerCase());
				documentBO.setLang("en");
				documentBO.setType("add");
				documentBO.setVersion(1);

				Parser parser = new AutoDetectParser(); // OfficeParser
				ParseContext parseCtx = new ParseContext();
				parser.parse(input, handler, metadata, parseCtx);
				input.close();

				// List all metadata
				String[] metadataNames = metadata.names();
				Map<String, Object> fields = new HashMap<String, Object>();
				for (String name : metadataNames) {
					String newName=name.replace("-", "_").replace(":", "_").replace(" ", "").toLowerCase();
					boolean includeFieldName=findIndexNames(newName);
					if(includeFieldName){
						fields.put(newName, metadata.get(name));
					}					
				}

				// Get the content of file and put it into fields
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(new File(fileName)));

				String output;
				StringBuffer stringBuffer = new StringBuffer();
				while ((output = bufferedReader.readLine()) != null) {
					stringBuffer.append(output);
				}

				fields.put("content", stringBuffer.toString().getBytes("UTF-8"));
				fields.put("full_path", urlpath);

				documentBO.setFields(fields);
				documentBOs.add(documentBO);
			}

			return generateJsonObject(documentBOs);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<String> listFilesForFolder(final File folder,
			List<String> fileNames) {

		if (folder.isDirectory()) {
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					listFilesForFolder(fileEntry, fileNames);
				} else {
					fileNames.add(fileEntry.getAbsolutePath());
				}
			}
		} else {
			fileNames.add(folder.getAbsolutePath());
		}

		return fileNames;
	}

	public static StringWriter generateJsonObject(List<DocumentBO> documentBOs)
			throws JsonGenerationException, JsonMappingException, IOException {
		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();
		// configure Object mapper for pretty print
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		// writing to console, can write to any output stream such as file
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, documentBOs);
      return stringWriter;
		
	}
	
	public static boolean findIndexNames(String findName){
		
		List<String> cacheFields=IndexFieldCache.getcacheIndexFields();
		for(String fieldName:cacheFields){
			if(findName.equals(fieldName.toLowerCase())){
				return true;
			}
			
		}
		
		return false;
	}
	
	public static void generateDocObject(String json){
		
	}

}
