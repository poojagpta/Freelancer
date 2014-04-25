package com.uwea.testdemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

public class DocumentMetaData {
	
	public static void main(String[] args) {


		String folder = "C:\\Users\\ashok\\Desktop\\Aviation\\CloudSearch.doc";
		DocumentMetaData documentData=new DocumentMetaData();
		List<String> filesNames=new ArrayList<String>();
		File file= new File(folder);
		filesNames = documentData.listFilesForFolder(file,filesNames);
		
		try {

			ContentHandler handler = new DefaultHandler();
			List<DocumentBO> documentBOs=new ArrayList<DocumentBO>();
			
			//Loop through all the files
			for(String fileName:filesNames){
				
				//Find extension of all files
				//String fileExtension=documentData.findExtension(fileName);

				Metadata metadata = new Metadata();
				InputStream input = new FileInputStream(new File(fileName));
				DocumentBO documentBO=new DocumentBO();
				documentBO.setId(fileName.replace(file.separatorChar+"", "_").replace(".", "_").replace(":", "_").toLowerCase());
				documentBO.setLang("en");
				documentBO.setType("add");
				documentBO.setVersion(1);
				
				Parser parser = new AutoDetectParser();  //OfficeParser
				ParseContext parseCtx = new ParseContext();
				parser.parse(input, handler, metadata, parseCtx);
				input.close();

				// List all metadata
				String[] metadataNames = metadata.names();
				Map<String,Object> fields =new HashMap<String, Object>();
				for (String name : metadataNames) {
					fields.put(name.replace("-", "_").replace(":", "_").replace(" ", "").toLowerCase(), metadata.get(name));
				}
				
				//Get the content of file and put it into fields
				BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
				
				String output;
				StringBuffer stringBuffer=new StringBuffer();
				while ((output = bufferedReader.readLine()) != null) {
					stringBuffer.append(output);
				}
		 
				fields.put("content", stringBuffer.toString().getBytes("UTF-8"));
				fields.put("full_path", fileName);
				
				documentBO.setFields(fields);				
				documentBOs.add(documentBO);
			}
			
			documentData.generateJsonObject(documentBOs);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> listFilesForFolder(final File folder,List<String> fileNames) {
		
		if(folder.isDirectory()){
			for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		            listFilesForFolder(fileEntry,fileNames);
		        } else {
		        	fileNames.add(fileEntry.getAbsolutePath());
		        }
		    }	
		}else{
			fileNames.add(folder.getAbsolutePath());
		}
		
		return fileNames;
	}

	public String findExtension(String fileName){
	
		String extension = "";

		File file=new File(fileName);
		int i = fileName.lastIndexOf('.');
		int p = fileName.lastIndexOf(file.pathSeparator);

		if (i > p) {
		    extension = fileName.substring(i+1);
		}
		
		
		return extension;
	}
	
	public void generateJsonObject(List<DocumentBO> documentBOs) throws JsonGenerationException, JsonMappingException, IOException{
		//create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        //configure Object mapper for pretty print
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
         
        //writing to console, can write to any output stream such as file
        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, documentBOs);
        
        //write the object to file
    
        File fileOutput=new File("C:\\Users\\ashok\\Desktop\\Aviation\\SDF\\Batch\\doc.sdf");
        
        FileOutputStream fileOutputStream=new FileOutputStream(fileOutput);
        fileOutputStream.write(stringWriter.toString().getBytes("UTF-8"));
        //fileOutput.
        
        System.out.println("Done"+stringWriter);
	}
	
	

}
