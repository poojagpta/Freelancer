<html>
<head>
<title>Aviation Search Demo - AWS Domain Creation</title>
<meta content="chrome=1;IE=edge" http-equiv="X-UA-Compatible">
<meta content="text/html; charset=utf-8" http-equiv="Content-type">
<link type="text/css" rel="stylesheet" href="style/cs-demo-common.css" />
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<div class="titleBox">
				<div class="demoTitle">
					<h1>Aviation Search Demo</h1>
				</div>
				<div class="demoDesc">
					<p>Create Domain Feature.</p>
					<p>Hint: Please provide  Domain Name like 'uwea-data',Region like 'us-east-1',Index Field  like 'author'
					   Note: For now domain with new API version 2013-01-01 and will have all access permission and index will be created of type "TEXT" 
					 </p>
				</div>
			</div>
		</div>
		<form name="domain" action="createDomain" method="post">
			<div id="searchBox">
				<div class="searchwrapper">
			       <% String error= (String)request.getAttribute("error"); 
			       if(error!=null){%>
			         <font size="3" color="red"><%=error%></font><br> <br>
			       <% }%>
			       
			       Domain Name <input type="text" name="domainName"  value=<%=request.getParameter("domainName")%>></input><br> <br>
			         Region<input type="text" name="region"  value=<%=request.getParameter("region")%>></input><br> <br>
					Index Field <input type="text" name="indexField" value=<%=request.getParameter("indexField")%>></input><br><br> 
					<input type="submit" id="sumitbtn" class="bluebtn" value="Domain" /><br><br>					
				</div>
			</div>
		</form>
	</div>
</body>
</html>
