<%@page import="com.quicksale.vo.UserVO"%>
<html>
<head>
</head>
<body>
Hello
<br/> 
<%
UserVO userVO = (UserVO)request.getSession().getAttribute("user");
if(userVO != null){
	out.println(userVO.getFirstName()+" "+userVO.getLastName());
}
else{
	out.println("Sorry, your are not logged in!");
}

%>
<h2>Cookies are set into this browser.</h2> 
<a href="search.jsp">Search</a>
<a href="fullSearch.jsp">Full Search</a>
<div align="right"><a href="logout" >Logout</a></div>
</body>
</html>