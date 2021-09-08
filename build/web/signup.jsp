<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>
<!DOCTYPE html>
<head>
	<title>Staff System</title>
	<meta charset="UTF-8">
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit"async defer>
        </script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main2.css">
</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<form class="login100-form validate-form" action="confirmSignup" method="post">
					<div class="wrap-input100 validate-input m-t-85 m-b-35" data-validate = "Enter Username">
						<input class="input100" id="usr" type="text" name="usrname">
						<span class="focus-input100" data-placeholder="Username"></span>
					</div>
                                        <!-- Ajax Username Check  -->
                                        <span id="available"></span>
                                        <script type="text/javascript">
                                            $(document).ready(function(){
                                                $("#usr").blur(function(){
                                                    var username = $("#usr").val();
                                                    
                                                    if(username.length >= 3){
                                                        $.ajax({
                                                           url:"checkUsername.jsp",
                                                           type:"post",
                                                           data:"uname="+username,
                                                           dataType:"text",
                                                           success:function(data){
                                                               $("#available").html(data);
                                                           }
                                                        });
                                                    }
                                                    else{
                                                        $("#available").html(" ");
                                                    }
                                                });
                                            });
                                        </script>
                                        
                                        <div class="wrap-input100 validate-input m-b-50" data-validate="Enter Firstname">
						<input class="input100" type="text" name="firstname">
						<span class="focus-input100" data-placeholder="Firstname"></span>
					</div>
                                    
					<div class="wrap-input100 validate-input m-b-50" data-validate="Enter Lastname">
						<input class="input100" type="text" name="lastname">
						<span class="focus-input100" data-placeholder="Lastname"></span>
					</div>
                                    
                                        <div class="wrap-input100 validate-input m-b-50" data-validate="Enter Contact">
						<input class="input100" type="number" name="contact">
						<span class="focus-input100" data-placeholder="Contact"></span>
					</div>

                                        <div class="wrap-input100 validate-input m-b-50" data-validate="Enter Email">
                                            <input class="input100" id="email" type="email" name="email">
                                            <span class="focus-input100" data-placeholder="Email"></span>
					</div>
                                        <!-- Ajax Email Check -->
                                        <span id="available2"></span>
                                        <script type="text/javascript">
                                            $(document).ready(function(){
                                                $("#email").blur(function(){
                                                    var email = $("#email").val();
                                                    
                                                    if(email.length >= 3){
                                                        $.ajax({
                                                           url:"checkEmail.jsp",
                                                           type:"post",
                                                           data:"email="+email,
                                                           dataType:"text",
                                                           success:function(data){
                                                               $("#available2").html(data);
                                                           }
                                                        });
                                                    }
                                                    else{
                                                        $("#available2").html(" ");
                                                    }
                                                });
                                            });
                                        </script>
                                        
                                        <div class="wrap-input100 validate-input m-b-50" data-validate="Enter Type">
                                            <select class="input100" style="border:0px; outline:0px;" name="types" id="types">
                                                <option value="Doctor">Doctor</option>
                                                <option value="Teacher">Teacher</option>
                                                <option value="Student">Student</option>
                                            </select>
					</div>
                                    
                                        <div class="g-recaptcha" style="margin: 0px 50px;" data-sitekey="6LdtjSQaAAAAAAM39IWGUgL-GA0qbTxY9clOotlP"></div>
                                        <br/>
                                        <div class="container-login100-form-btn">
                                            <button class="login100-form-btn" type="submit">Signup</button>
                                        </div>
				</form>
                                <div style="width: 100%; flex-wrap: wrap; justify-content: center;
                                     align-items: center; display: flex; padding: 15px;">
                                    <span style="padding-right: 10px">Are you already a member? </span><a href='index.html'>Login</a>
                                </div>
			</div>
		</div>
	</div>
        <div id="dropDownSelect1"></div>
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="vendor/animsition/js/animsition.min.js"></script>
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="vendor/select2/select2.min.js"></script>
	<script src="vendor/daterangepicker/moment.min.js"></script>
	<script src="vendor/daterangepicker/daterangepicker.js"></script>
	<script src="vendor/countdowntime/countdowntime.js"></script>
	<script src="js/main.js"></script>
</body>
</html>
