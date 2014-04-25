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
					<p>Hint: Meta data included content will be uploaded to CloudSearch and can be searched based on index. 
					 </p>
				</div>
			</div>
		</div>
		<form name="upload" action="uploadDoc" method="post" enctype="multipart/form-data">
			<div id="searchBox">
				<div class="searchwrapper">
			       Upload Document &nbsp;<input type="file" name="fileNames"><br><br>	
			       <input type="submit" id="sumitbtn" class="bluebtn" value="Upload" /><br><br>	  	
				</div>
			</div>
		</form>
		
	</div>
</body>
</html>