<%-- 
    Document   : hashTags
    Created on : Aug 12, 2016, 2:58:45 AM
    Author     : SUNIL VUPPALA
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css" type="text/css"/>
        <script type="text/javascript" src="twitterJavaScript.js"></script>
        <title>Hash Tags</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <c:forEach items="${tweetsByTag}" var="item">
            <section class="section31">
                <c:out value="${item.getFullName()}"/>
                @<c:out value="${item.getNickName()}"/><br>

                <script>
                    <c:set var="tweetFromUser" value="${item.getTweet()}"/>

                        hashTags('<c:out value="${tweetFromUser}"/>');
                </script>
            </section>
        </c:forEach>



        <%@ include file="footer.jsp" %>
    </body>
</html>
