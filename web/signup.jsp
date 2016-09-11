

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/style.css" type="text/css"/>
        <title>My Twitter</title>
         <link rel="icon" href="images/Twitter_logo_blue_1.png" type="image/png" sizes="16x16">
         <script type="text/javascript" src="twitterJavaScript.js"></script>

    </head>
    <body class="background">
         <section class="container">
     <section class="login">
      <h1>Sign up with Twitter</h1>
      <form method="post" action="membershipServlet">
          <c:if test="${message != null} ">
              <p class="errorMessage">${message}</p>
          </c:if> 
          
          
          <p><label>Full Name :</label><input type="text" name="fullName" value="" placeholder="Full Name" required/></label></p>
        
        <p><label>Email :</label><input type="text" name="emailAddress" value="" placeholder="Username or Email" required></p><br>
         <section class="register-form-row">
      <section class="register-form-row-col">Date Of Birth :</section>
      <section >Month:<select name="month" onchange="call()" required>
         <option value="">select</option>
         <option value="01">Jan</option>
         <option value="02">Feb</option>
         <option value="03">Mar</option>
         <option value="04">Apr</option>
         <option value="05">May</option>
         <option value="06">Jun</option>
         <option value="07">Jul</option>
         <option value="08">Aug</option>
         <option value="09">Sep</option>
         <option value="10">Oct</option>
         <option value="11">Nov</option>
         <option value="12">Dec</option>
        </select>
        Day :<select name="day" required>
           <option value="">select</option>
          </select>
        Year:<select name="year" onchange="call()" required>
           <option value="">select</option>
          </select>
     
        <p><input type="password" name="password" value="" placeholder="Password" required></p>
        <p><input type="password" name="confirmPassword" value="" placeholder="Confirm Password" required></p>
        <p><input type="text" name="nickName" value="" placeholder="Nick Name " ></p>
 
        <p class="submit"><input type="submit" name="signUp" value="Sign Up" >
            <input type="reset" name="reset" value="Reset"/></p>
      </form>
    </section>
    </body>
</html>
