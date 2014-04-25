<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
<script type="text/javascript" src="/jquery/jquery.js"></script>
<script type="text/javascript" src="/jquery/jquery.dataTables.min.js"></script>
<!-- <script src="http://code.jquery.com/jquery-1.9.1.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.6/angular.min.js"></script>
<script type="text/javascript" src="/jquery/daterangepicker.js"></script>
<script type="text/javascript" src="/jquery/jquery-ui.js"></script>

<!-- CSS Links -->
	
<link rel="stylesheet" href="/css/jquery.dataTables.css" type="text/css" media="screen" />
<link rel="stylesheet" href="/css/jquery.dataTables_themeroller.css" type="text/css" media="screen" />
<link rel="stylesheet" href="/css/jquery-ui-1.8.4.custom.css" type="text/css" media="screen" />

    
<script type="text/javascript">
		
	   	function SearchCtrl($scope,$http){
			    
	   		// The function that will be executed on button click (ng-click="search()")
			    $scope.search = function() {
				$scope.url = '/rest/search'; // The url of our search
				  // Create the http post request
				  // the data holds the keywords
				  // The request is a JSON request.
				   $http.post($scope.url).
						success(function(data) {
							  alert('--------'+data.userName);
						      $scope.result = data.userName; 
						 })
				        .error(function(data) {
				            $scope.result = data.status || "Request failed";
				                
				        });
				    };
			}
   	

	   	$(document).ready(function() {
	   		var oTable = $('#dataList').dataTable({
	   			"aoColumns":[
				             {"mDataProp":"userName"},
				             {"mDataProp":"address"},
				             {"mDataProp":"city"},
				             {"mDataProp":"state"},
				             {"mDataProp":"country"},
				             {"mDataProp":"merchantName"},
				             {"mDataProp":"date"},
				             {"mDataProp":"status"},
				             {"mDataProp":"billingAddress"},
				             {"mDataProp":"shippingAddress"}
			             ],
		  
			     "bRetrieve": true,
				 "bJQueryUI":true
	   		});
	   		$('#search').click(function(){
	   		
				$.post("/rest/search", $("#searchForm").serialize()).done(function(data) {
					/* var response = jQuery.parseJSON('result":[{"merchantName":"merchant1","userName":"jawad"},{"merchantName":"merchant1","userName":"jawad"}]}'); */
					
					/* if(typeof data =='object')
					{
					  //alert('it is json');
					} */
					
					/* $.each(data.result, function(key, val) {
						
						alert("---------"+val.userName);
							  
						}); */
						
						//var testdata = jQuery.parseJSON('{"result":[{"userName":"jawad","address":"rewrew","city":"rewrew","state":"rewrew","country":"rewrew","merchantName":"merchant1","date":"rewrew","status":"rewrew","billingAddress":"rewrew","shippingAddress":"rewrew"}]}');
						if(data.result == 'Invalid'){
							alert("Please login first");
							return;
						}
						 oTable.fnClearTable();
				   		 //if (aaData.length)
				   		 oTable.fnAddData(data.result);
				   		 oTable.fnDraw();
					 /* 	oTable.dataTable( {
							"aaData": data.result,
							              
						}); */
						$("#container").show();
				}).fail(function(){
					alert("Error");
				});
	   		});
	   		
	   		   		
	   		$("#searchOption").change(function(){
	   			
	   			var value = $("#searchOption").val();
	   			
	   			if(value == 0){
	   				$("#userSearchDiv").show();
	   				$("#addressSearchDiv").hide();
	   				$("#merchantSearchDiv").hide();
	   				$("#orderSearchDiv").hide();
	   			}else if(value == 1){
	   				$("#addressSearchDiv").show();
	   				$("#userSearchDiv").hide();
	   				$("#merchantSearchDiv").hide();
	   				$("#orderSearchDiv").hide();
	   			}else if(value == 2){
	   				$("#merchantSearchDiv").show();
	   				$("#userSearchDiv").hide();
	   				$("#addressSearchDiv").hide();
	   				$("#orderSearchDiv").hide();
	   			}else if(value == 3){
	   				$("#orderSearchDiv").show();
	   				$("#merchantSearchDiv").hide();
	   				$("#userSearchDiv").hide();
	   				$("#addressSearchDiv").hide();
	   			}
	   			
	   		});
	   		
	   		$('#fromDate').datepicker({		
				  //presetRanges: [{ text: '', dateStart: 'Today', dateEnd: 'Today'}],
				  dateFormat: 'yy-mm-dd 00:00:00',
				  rangeSplitter: '-',
				  changeMonth: true,
				  changeYear: true,		
				  time: true
			});

			$('#toDate').datepicker({
				  //presetRanges: [{ text: '', dateStart: 'Today', dateEnd: 'Today'}],
				  dateFormat: 'yy-mm-dd 23:59:59',
				  rangeSplitter: '-',
				  changeMonth: true,
				  changeYear: true,				 
				  time: true
			});
				
			
		});
	   	
		/*   $(document).ready(function() {
			var testdata = jQuery.parseJSON('{"result":[{"merchantName":"merchant1","userName":"jawad"},{"merchantName":"merchant1","userName":"jawad"}]}');
			$('#dataList').dataTable( {
				"aaData": testdata.result,
				"aoColumns":[{"mDataProp":"merchantName"},
				             {"mDataProp":"userName"}],
				"bJQueryUI":true
				            
				});
			});  */
		   		
	</script>
</head>
<body>
<form action="/rest/search" method="post" name="searchForm" id="searchForm">
<h2>Search Option</h2>
<select name="searchOption" id="searchOption">
	<option selected="selected">Select Option</option>
	<option value="0">User</option>
	<option value="1">Address</option>
	<option value="2">Merchant</option>
	<option value="3">Order Detail</option>
</select>
	<div align="center"><a href="/logout">Logout</a></div>
	
	<div id="userSearchDiv" style="display: none">
		<h2>User</h2>
			User<input type="text" name="user" ng-model="user">
			Email<input type="text" name="email" ng-model="email">
		<br/>
	</div>
	<div id="addressSearchDiv" style="display: none">
		<h2>Address</h2>
		Address<input type="text" name="address" ng-model="address"><span></span>
		City<input type="text" name="city" ng-model="city">
		State<input type="text" name="state" ng-model="state">
		Country<input type="text" name="country" ng-model="country"><br/>
	</div>
	<div id="merchantSearchDiv" style="display: none">
		<h2>Merchant</h2>
		Merchant<input type="text" name="merchant" ng-model="merchant"><br/>
	</div>
	
	<div id="orderSearchDiv" style="display: none">
		<h2>Order Detail</h2>
		From Date<input type="datetime" name="fromDate" ng-model="fromDate" id="fromDate">
		To Date<input type="datetime" name="toDate" ng-model="toDate" id="toDate"/><span></span>
		<!-- Order Status<input type="text" name="status" ng-model="status"/> -->
		<select name="status">
			<option selected="selected" value="0">Order Status</option>
			<option value="Accepted">ACCEPTED</option> 
			<option value="Decline">DECLINE</option>
			<option value="Review">REVIEW</option>
			<option value="Pending">PENDING</option>
		</select>
		
		<!-- Billing Address<input type="text" name="billAddress" ng-model="bAddress"/><br/><br/>
		Shipping Address<input type="text" name="shipAddress" ng-model="shipAddress"/> -->
	</div>
		<br/><br/>
	<input type="button" value="Search" id="search">
</form>
   
    <div id="container" style="display: none;margin-top: 30px;">
			<table id="dataList" >
				<thead>
					<tr>
						<th>User Name</th>
						<th>Address</th>
						<th>City</th>
						<th>State</th>
						<th>Country</th>
						<th>Merchant Name</th>
						<th>Date</th>
						<th>Status</th>
						<th>Billing Address</th>
						<th>Shipping Address</th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>
	</div>
    
</body>
</html>