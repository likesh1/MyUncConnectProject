<%-- 
    Document   : forgot
    Created on : Jul 23, 2016, 8:40:55 PM
    Author     : SUNIL VUPPALA
--%>

<!DOCTYPE html>
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link href='http://fonts.googleapis.com/css?family=Varela+Round' rel='stylesheet' type='text/css'>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="styles/forgot.css" />
    </head>
    <body>
        <section class="text-center">
	<section class="logo">forgot password</section>
        <c:if test="${message != null} ">
            <p class="errorMessage">${message}</p>
          </c:if> 
	<!-- Main Form -->
	<section class="login-form-1">
		<form id="forgot-password-form" class="text-left" method="post" action="loginServlet?action=forgot">
			<section class="etc-login-form">
				<p>When you fill in your registered email address, you will be sent instructions on how to reset your password.</p>
			</section>
			<section class="login-form-main-message"></section>
			<section class="main-login-form">
				<section class="login-group">
					<section class="form-group">
						<label for="emailAddress" class="sr-only">Email address</label>
						<input type="text" class="form-control" id="emailAddress" name="emailAddress" placeholder="email address" required>
					</section>
				</section>
				<button type="submit" class="login-button"><i class="fa fa-chevron-right"></i></button>
			</section>
			<section class="etc-login-form">
				<p>already have an account? <a href="login.jsp">login here</a></p>
				<p>new user? <a href="signup.jsp">create new account</a></p>
			</section>
		</form>
	</section>
	<!-- end:Main Form -->
</section>
    </body>
    </html>