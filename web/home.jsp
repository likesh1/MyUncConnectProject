
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="business.UserTweet"%>
<%@page import="business.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/login.css" type="text/css"/>
        <script type="text/javascript" src="twitterJavaScript.js"></script>
        <title>My Twitter</title>
    </head><link rel="icon" href="images/Twitter_logo_blue_1.png" type="image/png" sizes="16x16">
    <body>
    <%@ include file="header.jsp" %>
    
    
    <section class="section11" >
        <section class="section21">
            <img src=${user.getUrl()} alt="image not found" width="100" height="100"/><br>
            <span class="name">${user.getFullName()}</span><br>
            <span class="name">@${user.getNickName()}</span><br><br>
            <span class="name">Tweets</span>
            <span class="name">${tweetsUserCount.size()}</span><br>
                        <span class="name">Followers</span>
            <span class="name">${followersCount}</span><br>
                        <span class="name">Following</span>
            <span class="name">${selectFollowingUser.size()}</span><br>
        </section>

        <section class="section22" >
            <label class="label">Trends</label><br>
            <c:forEach items="${trends}" var="item">
                <c:out value="${item.getHashTag()}" />
                <br><br>
            </c:forEach>
        </section>
    </section>
    <section class="section12" >
        <section>
            <form method="POST" action="homeServlet?action=home">
                <textarea name="tweetPostArea" id="tweetText" maxlength="200" placeholder="What's happening?"></textarea>
                <input type="submit" name="tweetPost" value="tweet" class="myButton"/>

            </form>
        </section><br><br>
        <section>


            <c:forEach items="${tweetsUser}" var="item">
                <form method="POST" action="homeServlet?action=delete">
                <section class="section3" id="${item.getTweet()}">
                    
                    <c:out value="${item.getFullName()}"/>
                    @<c:out value="${item.getNickName()}"/><br>

                    <script>
                        <c:set var="tweetFromUser" value="${item.getTweet()}"/>
                        
                        hashTags('<c:out value="${tweetFromUser}"/>');
                        
                        
                        
                    </script>
                    <c:if test="${item.getEmailAddress()==user.getEmailAddress()}">
                            <input type='hidden' value='${item.getEmailAddress()}' name="userID">
                            <input type='hidden' value='${item.getTweet()}' name="userTweet">
                            <input type="submit" value="Delete" class="delete" />                       
                    </c:if>
                            <input type="hidden" id="hiddenField" name="hiddenField"/>
                </section>
                </form>
            </c:forEach>



        </section>
    </section>

    <section class="section23">
        <label>Who to follow:</label>
        <section>
        <c:forEach items="${userFollowList}" var="item">
            <form method="POST" action="homeServlet?action=follow">
                <c:out value="${item.getFullName()}" />
                @<c:out value="${item.getNickName()}"/><br>
                <input type='hidden' value='${item.getEmailAddress()}' name="followId">
                <input type="submit" value="unfollow" class="follow"/>  <br><br>
            </form>
        </c:forEach>
        <c:forEach items="${users}" var="item">
            
                <form method="POST" action="homeServlet?action=follow">
                <c:out value="${item.getFullName()}"/>
                @<c:out value="${item.getNickName()}"/><br>
                <input type='hidden' value='${item.getEmailAddress()}' name="followId">
                <input type="submit" value="follow" class="follow"/>  <br><br>
                </form>
          
        </c:forEach>
                  </section>
    </section>
    <br>  


    <%@ include file="footer.jsp" %>
</body>
</html>
