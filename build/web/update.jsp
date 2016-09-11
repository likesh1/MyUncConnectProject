<%-- 
    Document   : update
    Created on : Jul 25, 2016, 1:15:02 AM
    Author     : SUNIL VUPPALA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/style.css" type="text/css"/>
        
        <title>JSP Page</title>
         <script type="text/javascript" src="twitterJavaScript.js"></script>

    </head>
    <body class="background">
            <%@ include file="header.jsp" %>
         <section class="container">
     <section class="login">
      <h1>Update Profile</h1>
   
      <form method="post" action="homeServlet?action=update">
          <c:if test="${message != null} ">
              <p class="errorMessage">${message}</p>
          </c:if> 
          
          
          <p><label>Full Name :</label><input type="text" name="fullName" value="" placeholder="Full Name" /></label></p>
        
         <section class="register-form-row">
      <section class="register-form-row-col">Date Of Birth :</section>
      <section >Month:<select name="month" onchange="call()" >
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
        Day :<select name="day" >
           <option value="">select</option>
          </select>
        Year:<select name="year" onchange="call()" >
           <option value="">select</option>
          </select>
               </section>
       </section>
        <p><input type="password" name="password" value="" placeholder="Password" ></p>
        <p><input type="password" name="confirmPassword" value="" placeholder="Confirm Password" ></p>
        <p><label>Nick Name: ${user.getNickName()}</label></p>
        <p><input type="text" name="profilePic" placeholder="Profile pic Name"/></p>
        <p class="submit"><input type="submit" name="update" value="Update" >
    
      </form>
    </section>
            <%@ include file="footer.jsp" %>
    </body>
</html>
