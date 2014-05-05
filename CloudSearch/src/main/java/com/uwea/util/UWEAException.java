package com.uwea.util;


public class UWEAException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String errorCodeStr;
	private String outputString = null;
	private String errorFieldName = "";
	// Additonal Error Parameter array, To be used if more than two params reqd.
	private String[] errorParameters = null;
	
	/**
	 * Default Constructor  Should not be called without errorcode. Initialises errorCodeStr &
	 * error Message to generic Error Code and Error Message.
	 **/
	public UWEAException()
	{
		this(ErrorDefs.GENERAL_ERROR_OCCURED.getErrorCode());
	}

	/**
	 * Constructor with errorCodeStr as parameter.
	 *
	 * @param errorCodeStr Error Code value
	 **/
	public UWEAException(String errorCodeStr)
	{
		this(errorCodeStr, "", (String[])null);
	}

	/**
	 * Constructor with errorCodeStr as parameter.
	 *
	 * @param errorCodeStr Error Code value
	 **/
	public UWEAException(String errorCodeStr, Throwable cause)
	{
		this(errorCodeStr, "", (String[])null, cause);
	}

	/**
	 * Constructor with errorCodeStr & error parameter.
	 *
	 * @param errorCodeStr Error Code
	 * @param errorFieldName Error field name
	 **/
	public UWEAException(String errorCodeStr,
	                       String errorFieldName)
	{
		this(errorCodeStr, errorFieldName, (String[])null);
	}
	
	
	/**
	 * Constructor with errorCodeStr, error and warning parameter.
	 *
	 * @param errorCodeStr Error Code
	 * @param errorFieldNames Error field name
	 * @param warningFieldNames filed name
	 **/
	
	public UWEAException(String errorCodeStr,
	                       String[] errorFieldNames,String[] warningFieldNames, String [] errorParam)
	{
				
		StringBuffer fieldName = new StringBuffer(); 
		
		if ((errorFieldNames!= null && errorFieldNames.length > 0) || (warningFieldNames!= null && warningFieldNames.length> 0))
		{
			fieldName.append("|||");
		}
		
		if (errorFieldNames!= null && errorFieldNames.length > 0)
		{
			for (int i = 0; i< errorFieldNames.length; i++)
			{
				fieldName.append(errorFieldNames[i] + "|||");
			}
		}
		
		if (warningFieldNames!= null && warningFieldNames.length> 0)
		{
			fieldName.append("|W|" + "|||");
			
			for (int i = 0; i< warningFieldNames.length; i++)
			{
				fieldName.append(warningFieldNames[i] + "|||");
			}
		}
		
		this.errorCodeStr    = errorCodeStr;
		this.errorFieldName  = fieldName.toString();
		this.errorParameters = errorParam;
		this.outputString = this.toString();
	}
	//WO 147239 Fix End
	/**
	 * Constructor with errorCodeStr & error parameter.
	 *
	 * @param errorCodeStr Error Code
	 * @param errorFieldName Error field name
	 **/
	public UWEAException(String    errorCodeStr,
	                       String    errorFieldName,
	                       Throwable cause)
	{
		this(errorCodeStr, errorFieldName, (String[])null, cause);
	}

	/**
	 * Constructor with errorCodeStr & error field & error parameter.
	 *
	 * @param errorCodeStr Error Code
	 * @param errorFieldName Error field name
	 * @param errorParam Error Parameter
	 **/
	public UWEAException(String errorCodeStr,
	                       String errorFieldName,
	                       String errorParam)
	{
		this(errorCodeStr, errorFieldName, new String[] { errorParam });
	}

	/**
	 * Constructor with errorCodeStr & error field & error parameter.
	 *
	 * @param errorCodeStr Error Code
	 * @param errorFieldName Error field name
	 * @param errorParam Error Parameter
	 **/
	public UWEAException(String    errorCodeStr,
	                       String    errorFieldName,
	                       String    errorParam,
	                       Throwable cause)
	{
		this(errorCodeStr, errorFieldName, new String[] { errorParam }, cause);
	}

	/**
	 * Constructor with errorCodeStr & error field & an array of string parameters.
	 *
	 * @param errorCodeStr Error Code
	 * @param errorFieldName Error field name
	 * @param errorParameters Arrary of error parameters
	 **/
	public UWEAException(String   errorCodeStr,
	                       String   errorFieldName,
	                       String[] errorParameters)
	{
		this.errorCodeStr    = errorCodeStr;
		this.errorFieldName  = errorFieldName;
		this.errorParameters = errorParameters;

		this.outputString = this.toString();
	}

	/**
	 * Constructor with errorCodeStr & error field & an array of string parameters.
	 *
	 * @param errorCodeStr Error Code
	 * @param errorFieldName Error field name
	 * @param errorParameters Arrary of error parameters
	 **/
	public UWEAException(String    errorCodeStr,
	                       String    errorFieldName,
	                       String[]  errorParameters,
	                       Throwable cause)
	{
		super(cause);
		this.errorCodeStr    = errorCodeStr;
		this.errorFieldName  = errorFieldName;
		this.errorParameters = errorParameters;

		this.outputString = this.toString();
	}
	


	/**
	 * Constructor for general unexpected exception
	 *
	 * @param ex Unexpected exception
	 **/
	public UWEAException(Throwable ex)
	{
		this(ErrorDefs.GENERAL_ERROR_OCCURED.getErrorCode(), ex.getMessage(), new String[] { ex.getMessage() }, ex);
	}

	/**
	 * Get Error Code
	 *
	 * @return Error Code
	 **/
	public String getErrorCodeStr()
	{
		return errorCodeStr;
	}

	/**
	 * Get Error Field Name
	 *
	 * @return Error field name
	 **/
	public String getErrorFieldName()
	{
		return errorFieldName;
	}

	/**
	 * Get Error Message
	 *
	 * @return errorMessage     Error Message
	 **/
	public String getMessage()
	{
		return toString();
	}

	/**
	 * Get First Error Paramter
	 *
	 * @return errorParam    First Error Paramter
	 **/
	public String getErrorParam()
	{
		if ((errorParameters == null) || (errorParameters.length == 0))
		{
			return "";
		}

		return errorParameters[0];
	}

	/**
	 * Get extra
	 *
	 * @return array of error paramter
	 **/
	public String[] getErrorParameters()
	{
		if (errorParameters == null)
		{
			return new String[0];
		}

		return errorParameters;
	}

	/**
	 * Set Error Code
	 *
	 * @param errorCodeStr Error Code
	 **/
	public void setErrorCodeStr(String errorCodeStr)
	{
		this.errorCodeStr = errorCodeStr;
	}

	/**
	 * Set Error Field Name
	 *
	 * @param errorFieldName Error Field Name
	 **/
	public void setErrorFieldName(String errorFieldName)
	{
		this.errorFieldName = errorFieldName;
	}

	/**
	 * Set Error Paramter
	 *
	 * @param errorParam Error Paramter
	 **/
	public void setErrorParam(String errorParam)
	{
		this.errorParameters    = new String[1];
		this.errorParameters[0] = errorParam;
	}

	/**
	 * Set Error parameter array
	 *
	 * @param errorParameters error parameter array
	 **/
	public void setNextErrorParam(String[] errorParameters)
	{
		this.errorParameters = errorParameters;
	}

	/**
	 * Check for particular error code
	 *
	 * @param errorCodeStr Error code to check
	 *
	 * @return true if this exception includes the indicated error code
	 **/
	public boolean checkErrorCode(String errorCodeStr)
	{
		return this.errorCodeStr.equalsIgnoreCase(errorCodeStr);
	}

	/**
	 * Generates Error String with Error Code & Error Message.
	 *
	 * @return errorString  Exception Error String
	 **/
	public String toString()
	{
		if (outputString != null)
		{
			return outputString;
		}

		String errorCodeDescription = "";

		

		if (errorParameters == null || errorParameters.length == 0)
		{
			return "UWEAException ErrorCodeStr : " + errorCodeStr + " : " + errorCodeDescription +
			       ((errorFieldName != null) ? (" : " + errorFieldName) : "");
		}
		else
		{
			return "UWEAException ErrorCodeStr : " + errorCodeStr + " : " + errorCodeDescription +
			       ((errorFieldName != null) ? (" : " + errorFieldName) : "") + " : " +
			       errorParameters[0];
		}
	}

	/**
	 * Sets the errorParameters.
	 *
	 * @param errorParameters The errorParameters to set
	 **/
	public void setErrorParameters(String[] errorParameters)
	{
		this.errorParameters = errorParameters;
	}

	public String getErrorDescription()
	{
		String retVal = errorCodeStr;
		
		/*RequestContext req = RequestContext.getRequest();
		
		try
		{
			if(req != null && req.getConnection() != null && !req.getConnection().isClosed())
			{
				ErrorInfo ei = customerDAOAccessor.getFieldCodeDAO()
				                                  .getErrorByCode(RequestContext.getRequest(),
				                                                  errorCodeStr);
				if(ei != null)
				{
					retVal = ei.getEnca_Description();
					if(req.getLocale() != null && "fr".equalsIgnoreCase(req.getLocale().getLanguage()))
					{
						retVal = ei.getFrca_Description();
					}
					retVal = retVal.replaceAll("\\$s", "");
				}
			}
		}
		catch (Exception e)
		{
		}*/
		
		//Code to get the error message
		
		if (retVal.indexOf("%") >= 0)
		{
			String   s = null;

			String[] parms = this.getErrorParameters();

			if ((parms != null) && (parms.length > 0))
			{
				s = parms[0];
			}

			if (s == null)
			{
				s = this.getErrorFieldName();
			}

			if (s == null)
			{
				s = "";
			}

			retVal = retVal.replaceAll("%1", s);

			// replace %2..%9 with parms[1]...parms[8]
			for (int i = 2; i < 10; i++)
			{
				if ((parms != null) && (i <= parms.length))
				{
					s = parms[i - 1];
				}
				else
				{
					s = "";
				}

				retVal = retVal.replaceAll("%" + i, s);
			}
		}
		else
		{
			if (this.errorFieldName != null && this.errorFieldName.trim().length() > 0)
			{
				retVal = retVal + "[" + this.errorFieldName + "]";
			}
		}
		return retVal;
	}

}
