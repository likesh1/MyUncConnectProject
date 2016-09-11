
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link rel="stylesheet" href="styles/style.css" type="text/css"/>

        <title>My Twitter</title>
        <link rel="icon" href="images/Twitter_Logo.png" type="image/png" sizes="16x16">
 
    </head>
    <body class="background">
  <section class="container">
    <section class="login">
      <h1>Login to Twitter</h1>
                <c:if test="${message != null} ">
              <p class="errorMessage">${message}</p>
          </c:if> 
      <form method="post" action="loginServlet?action=login">
        <p><input type="text" name="userName" value="" placeholder="Username or Email"></p>
        <p><input type="password" name="password" value="" placeholder="Password"></p>
        <p class="remember_me">
          <label>
            <input type="checkbox" name="remember_me" id="remember_me">
            Remember me
          </label>
        </p>
        <p class="submit"><input type="submit" name="commit" value="Login"></p>
        <p>New User? <a href="signup.jsp">Sign up here</a>.</p>
         <p>Forgot Password? <a href="forgot.jsp">Forgot Password</a>.</p>
      </form>

      
    </section>
    </body>
</html>
