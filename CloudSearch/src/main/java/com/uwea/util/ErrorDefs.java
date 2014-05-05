package com.uwea.util;

public enum ErrorDefs {

	GENERAL_ERROR_OCCURED("Please contact administrator for error occured"),NULL_EXCEPTION("Please specify value in fields %1"),FILE_EXCEPTION("File specify %1 doesn't exists"),FILE_UPLOAD("Error encounter when upload file: %1");
	
	private String errorCode;
	private ErrorDefs(String errorCode) {
        this.errorCode=errorCode;
    }
	public String getErrorCode() {
		return errorCode;
	}
		
}
