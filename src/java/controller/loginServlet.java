/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import business.UserTweet;
import dataaccess.UserDB;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SUNIL VUPPALA
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/loginServlet"})
public class loginServlet extends HttpServlet{
    String userName, password,emailAddress, passwordForgot ;
    String url,message=null;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         User user = new User();
           //  super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
      ArrayList<UserTweet> tweets=null;
      ArrayList<UserTweet> tweetsUser=null;
      ArrayList<UserTweet> tweetsUserNotify=null;
      ArrayList<User> followersUserNotify=null;
            ArrayList<User> users=null;
            ArrayList<User> userFollowList =null;
            ArrayList<UserTweet> tweetsUserCount = null;
 
      HttpSession session = request.getSession();

            
        try {
            String action=request.getParameter("action")==null?"":request.getParameter("action");
             url="/login.jsp";
            if(action.equals("login")){
                //  super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
            userName = request.getParameter("userName");
            password = request.getParameter("password");
           
                    Date currentDate = new Date();
        session.setAttribute("currentDate", currentDate);
           
            user=UserDB.select(userName);
            String salt = user.getSalt();                 
                System.out.println(salt);
            
            System.out.println("username is :"+userName+"  password  is :"+password);
                 String pass = UserDB.hashPassword(password);
                password = UserDB.hashAndSaltPassword(pass, salt);
                System.out.println(password);
            if(user != null && password.equals(user.getPassword())){
                url="/home.jsp";
                
                
               String followName = "follow";
               
               userFollowList = UserDB.selectFollowList(userName);
               tweets = UserDB.selectTweets();
               users = UserDB.selectUsersNotFollowing(user.getEmailAddress());
             //  users = UserDB.selectUsers();
               tweetsUser = UserDB.selectTweetsUser(user);
                 tweetsUserCount = UserDB.selectTweetsUserCount(user);
                session.setAttribute("tweetsUserCount", tweetsUserCount);
               session.setAttribute("trends", UserDB.trendsCount());
                session.setAttribute("selectFollowingUser", UserDB.selectFollowingUser(userName));
                session.setAttribute("followersCount",UserDB.selectFollowerOfUserList(userName).size() );
               session.setAttribute("userFollowList", userFollowList);
                session.setAttribute("followName", followName);
               session.setAttribute("tweetsUser", tweetsUser);
               session.setAttribute("tweets", tweets);
               session.setAttribute("users", users);
              //session.setAttribute("fullName", user.getFullName());
             //session.setAttribute("nickName", user.getNickName());
              session.setAttribute("user", user);
            }else{
                message="Invalid username or password!";
            }
           //  System.out.println("sunil printing!!!!!!!! "+user.getFullName());
            }else if(action.equals("forgot")){
                  emailAddress = request.getParameter("emailAddress");
                user = UserDB.select(emailAddress);
                System.out.println("emailAddress is :" + emailAddress);
                if (user != null && emailAddress.equals(user.getEmailAddress())) {
                    passwordForgot = UserDB.generateRandomPassword();
                    System.out.println(passwordForgot);
                    UserDB.UpdatePassword(emailAddress,passwordForgot);
                    UserDB.SendEmail(emailAddress,passwordForgot);    
                    
        /*String subject = "password recovery";
        String mess =  "your password is"+password;
        String usern = "";
        String pass = request.getParameter("pass");
        
        out.println("Mail send successfully");*/

                    System.out.println("emailsent-likesh");
                } else {
                    url = "/forgot.jsp";
                    message = "Invalid Email Address";
                }
            }
            else if (action.equals("notification")) {
                System.out.println("inside notification@@@@@");
                 
                user = UserDB.select(userName);
                tweetsUserNotify = UserDB.selectTweetsUserLogin(user);
                followersUserNotify = UserDB.selectFollowersLogin(user);
                //System.out.println(tweetsUserNotify.toString());
                session.setAttribute("followersUserNotify", followersUserNotify);
                session.setAttribute("userLoginTweets",tweetsUserNotify);
                System.out.println("size of notifications "+tweetsUserNotify.size());
            url = "/notifications.jsp";
            

        }else if (action.equals("hashTags")) {
            System.out.println("inside hashTags@@@@@");
            String hashTag = request.getParameter("value");
           // session.setAttribute("s", s);
                System.out.println("hashtag clicked is "+hashTag);
                
                session.setAttribute("tweetsByTag", UserDB.tweetsByHahTag(hashTag));
                
                System.out.println("trends!!!!!!!!!"+UserDB.trendsCount().toString());
                boolean hashTagInTable = UserDB.hashTagIntoTable(hashTag);
                
                if(!hashTagInTable){
                    
                    UserDB.insertHashTag(hashTag);
                }
                else{
                    
                       UserDB.upDateHashTag(hashTag);
                }
                session.setAttribute("trends", UserDB.trendsCount());
            url = "/hashTags.jsp";

        }
            else if(action.equals("logout")){
                 user = UserDB.select(userName);
                UserDB.userLogin(user);
                session.invalidate();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

              
        request.setAttribute("message", message);       
        getServletContext().getRequestDispatcher(url).forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }
    
}
