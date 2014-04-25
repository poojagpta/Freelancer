<!Doctype html>
<html>
<head>
 <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
 <script src="js/base64.js"></script>
 <script type="text/javascript">
	$(document).ready(function(){
		base64.settings.ascii = true;
		$('#login').on('click',function(e){
		e.preventDefault();
		$('#UserAgent').val(navigator.userAgent);
		var userAgent = navigator.userAgent;
		var userName = $('#userName').val();
		var password = $('#password').val();
		var ip = null;
		$.getJSON( "http://smart-ip.net/geoip-json?callback=?",
			        function(data){
			            ip = data.host;
			            console.log(ip);
			            var encodedValue = base64.encode(ip+'####'+userName+'####'+userAgent);
			            console.log(encodedValue);
			            //readCookie(encodedValue);
						$.ajax({
					        type: 'POST',
					        url: '/login',
					        data: {'userName':userName,'password':password},
					        dataType: 'json',
					        success: function(msg){
					        	if(msg['success'] != undefined || msg['success']!= null){
					        		document.location.href = msg['success'];
								    }
					        	if(msg['error'] != undefined || msg['error']!= null){
										$('#error').html(msg['error']);		
						        	}
					        }
				        });
			            
			        }
		   );
		}); 
		});

	function readCookie(n){n+='=';for(var a=document.cookie.split(/;\s*/),i=a.length-1;i>=0;i--)if(!a[i].indexOf(n))return a[i].replace(n,'');}
	
 </script>
</head>
<body>
		<div id="logindiv" style="margin:10%;">
		<h3>Login Form</h3>
		<form method="post" action="login" name="Login">
			<label>User Name:</label><input id="userName" type="text" name="userName"><br/>
			<label>Password:</label><input id="password" type="password" name="password">	<br/>
			<lable>Keep me logged in for: </lable>
			<select name="days">
				<option value="1" selected>1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				<option value="13">13</option>
				<option value="14">14</option>
				<option value="15">15</option>
				<option value="16">16</option>
				<option value="17">17</option>
				<option value="18">18</option>
				<option value="19">19</option>
				<option value="20">20</option>
				<option value="21">21</option>
				<option value="22">22</option>
				<option value="23">23</option>
				<option value="24">24</option>
				<option value="25">25</option>
			</select><br>
			<input id="loginx" type="submit" value="login">
		</form>
		<label style="color:red;" id="error"></label>
		</div>
		<a href="register.jsp"><button>Register</button></a>
		<input type="hidden" id="UserAgent" value="">
	</body>
</html>