<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Entity</title>
 <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">

$(document).ready(function() {

	$.ajax({
		url: "/rest/kind/get/list",
		}).done(function(data) {
			//alert("----"+data.list[0]);
			
		}).fail(function(jqXHR, textStatus) {
			alert( "Request failed: " + textStatus );
		});
	
    $("#addProperty").click(function () {
      	$('#propertyDiv').clone().appendTo($('#propertyContainer'));
   });
    
    $("#addChild").click(function () {
      	$('#childKindDiv').clone().appendTo($('#childKindContainer'));
   });
    
    $("#addKind").click(function () {
      	$('#kindDiv').show();
   });
}); 

</script>
</head>
<body>
Add Entity
<form action="/create/entity" method="post">
	<div>
	<!-- 	<select name="kind" >
			<option selected="selected">Select Kind<option>
			<option value="Merchant">Merchant<option>
			<option value="User">User<option>
			<option value="Address">Address<option>
		</select> -->
	</div>
	<a href="#" id="addKind">Add new Kind</a>
	<div id="kindDiv" style="display: none">
		Kind <input type="text" name="kind"/>
	</div>
	<br/>
	<div id="childKindContainer">
		<div id="childKindDiv">
			Referenced Property/Key <input type="text" name="childEntity"/>
		</div>
	</div>
	<a href="#" id="addChild">Add Child Reference</a>
	<div id="propertyContainer">
		<div id="propertyDiv">
			Property/Value<input type="text" name="property"/> <span></span>
			<!-- Value<input type="text" name="value"/> -->
			<br/>		
		</div>
		
	</div>
	<a href="#" id="addProperty">Add Property</a>
	<br/>
	<input type="submit" value="Add Entity"/>
</form>

</body>
</html>