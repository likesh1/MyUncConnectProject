<%-- 
    Document   : notificationsJsp
    Created on : Aug 5, 2016, 4:44:40 PM
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
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <section>


            <c:forEach items="${userLoginTweets}" var="item">

                <section class="section31" >

                    <c:out value="${item.getFullName()}"/>
                    @<c:out value="${item.getNickName()}"/><br>

                    <script>
                        <c:set var="tweetFromUser" value="${item.getTweet()}"/>

                        hashTags('<c:out value="${tweetFromUser}"/>');
                    </script>

                </section>

            </c:forEach>

            <c:forEach items="${followersUserNotify}" var="item">
                <section class="section31">
                    <c:out value="${item.getFullName()}" />
                    @<script>
                        <c:set var="followerForUser" value="${item.getNickName()}"/>
                        hashTags('<c:out value="${followerForUser}"/>');
                    </script>
                    started following you
                </section>
            </c:forEach>



        </section>
        <%@ include file="footer.jsp" %>
    </body>
</html>
