<%@ page import="java.util.Map"%>
<html>
<head>
<title>Aviation Search Demo - Amazon CloudSearch</title>
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
					<p>Search Document Features.</p>
					<p>Hint: There is few documents. 1. Query String can be like
						'testing' present in document text. 2. For Facet search can use
						Field 'author' value 'pooja'</p>
				</div>
			</div>
		</div>
		<form name="input" action="search" method="post">
			<div id="searchBox">
				<div class="searchwrapper">
					<input type="radio" id="q" name="rSearch" value="q">Query
					Based<br> Query String <input type="text" name="query"
						value=<%=request.getParameter("query")%>></input><br> <br>
					<input type="radio" id="bq" name="rSearch" value="bq">Facet
					Based<br> Exclude <input type="checkbox" name="incEx" id="incEx"
						value="true">&nbsp; Facet Field <input type="text"
						name="facetField" value=<%=request.getParameter("facetField")%>></input>&nbsp;
					&nbsp; Facet Value<input type="text" name="facetValue"
						value=<%=request.getParameter("facetValue")%>></input> <input
						type="submit" id="sumitbtn" class="bluebtn" value="Search" /><br>
					<br>
					<%
						Map<String, String> linkDoc = (Map) request
								.getAttribute("listName");
						if (linkDoc != null) {
							for (Map.Entry entry : linkDoc.entrySet()) {
					%>
					<a href=<%=entry.getKey()%>><%=entry.getValue()%></a>
					<hr>
					<%
						}

					}
					%>
				</div>
			</div>
		</form>
		<script>
			<%
				String searchCheckbox = request.getParameter("rSearch");
				String incExStr = request.getParameter("incEx");
				String restfulStr = (String)request.getAttribute("queryStr");
								
				if (("q").equals(searchCheckbox)) {
			%>
		
			document.getElementById("q").checked=true;
			document.getElementById("bq").checked=false;
			<%
				} else {
			%>
			
			document.getElementById("bq").checked=true;
			document.getElementById("q").checked=false;
			<%
				}
				if (("true").equals(incExStr)) {
			%>
			document.getElementById("incEx").checked=true;
			<%
				} else {
			%>
			document.getElementById("incEx").checked=false;
			<%
				}			 
		     %>	
		 
		</script>
		  <%if(restfulStr!=null && restfulStr.length()>0){ %>
			<a href="rest/searchdoc/<%=restfulStr%>">search</a>
			<%}%>
	</div>
</body>
</html>
