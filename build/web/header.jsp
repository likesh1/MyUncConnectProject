

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css" type="text/css"/>
        <title>My Twitter</title>
    </head>
    <body>
        <h1>Welcome ${user.getFullName()}</h1>


                <ul>
                    <li><a class="active" href="home.jsp" class="styleHeader" >Home</a></li>
            <li><a href="loginServlet?action=notification" class="styleHeader">Notifications</a></li>
            <li><a href="update.jsp" class="styleHeader">Profile</a></li>
            <li><a href="loginServlet?action=logout" class="styleHeader">Log Out</a></li>

        </ul>
    </body>
</html>
