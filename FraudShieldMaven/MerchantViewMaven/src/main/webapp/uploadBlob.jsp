<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html ng-app>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Hello App Engine</title>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.6/angular.min.js"></script>
   	<script type="text/javascript">
		
   	function SearchBlobCtrl($scope,$http){
		    
   		// The function that will be executed on button click (ng-click="search()")
		    $scope.search = function() {
			$scope.url = '/blobs/get/'+$scope.keywords; // The url of our search
			  // Create the http post request
			  // the data holds the keywords
			  // The request is a JSON request.
			   $http.get($scope.url).
					success(function(data) {
					      $scope.result = data; 
					 })
			        .error(function(data) {
			            $scope.result = data.status || "Request failed";
			                
			        });
			    };
		}
	</script>
     
  </head>

  <body ng-controller="SearchBlobCtrl">
   	
   <!--  <table>
      <tr>
        <td colspan="2" style="font-weight:bold;">Available Servlets:</td>        
      </tr>
      <tr>
        <td><a href="gaeexample">GAEExample</a></td>
      </tr>
    </table> -->
  
   	<h2>Upload a blob to blob store</h2>
    	 <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
            <input type="file" name="myFile">
            <input type="submit" value="Submit">
        </form>
    
    <h2>Search a blob</h2>    
        <form>
        	<input type="text" ng-model="keywords">
       		<button type="submit" ng-click="search()">Search</button>
        </form>
        <div ng-model="result">
        	<h2>Result</h2>
        	{{result}}
        </div>
    </body>
  
</html>
