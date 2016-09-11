/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import business.User;
import business.UserTweet;
import business.Validations;
import dataaccess.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author SUNIL VUPPALA
 */
@WebServlet(name = "homeServlet", urlPatterns = {"/homeServlet"})
public class homeServlet extends HttpServlet {

    String url = null;
    String followName = null;
    User user, userToDb = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
        ArrayList<UserTweet> tweets = null;
        ArrayList<UserTweet> tweetsUser = null;
        ArrayList<UserTweet> tweetsUserCount = null;

        ArrayList<User> users = null;
        ArrayList<User> list = null;

        url = "/home.jsp";

        HttpSession session = request.getSession();
        user = (User) request.getSession().getAttribute("user");
        userToDb = new User(user.getFullName(), user.getFullName(), user.getNickName(), user.getPassword(), user.getBirthDate(), user.getUrl(),user.getSalt());
        System.out.println("for checking session : " + user.toString());
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        request.setAttribute("fullName", user.getFullName());
        request.setAttribute("nickName", user.getNickName());
        if (action.equals("home")) {
            user = (User) request.getSession().getAttribute("user");

            UserTweet userTweet = new UserTweet(user.getEmailAddress(), request.getParameter("tweetPostArea"), user.getFullName(), user.getNickName());

            //     System.out.println(userTweet.toString());
            if (!userTweet.getTweet().isEmpty()) {
                try {
                    UserDB.insertTweet(userTweet);
                } catch (SQLException ex) {
                    Logger.getLogger(homeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

            }
            try {

                //  tweets = UserDB.selectTweets();
                // tweetsUser = UserDB.selectTweetsUser(user);
                //users = UserDB.selectUsers();
                tweets = UserDB.selectTweets();
                users = UserDB.selectUsers();
                tweetsUser = UserDB.selectTweetsUser(user);
                tweetsUserCount = UserDB.selectTweetsUserCount(user);
                
                session.setAttribute("trends", UserDB.trendsCount());
                session.setAttribute("tweetsUserCount", tweetsUserCount);

                session.setAttribute("tweetsUser", tweetsUser);
                session.setAttribute("tweets", tweets);
                session.setAttribute("users", users);
                //session.setAttribute("fullName", user.getFullName());
                //session.setAttribute("nickName", user.getNickName());
                session.setAttribute("user", user);
            } catch (SQLException ex) {
                Logger.getLogger(homeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(tweets.toString());

            System.out.println("Before update" + user.toString());
        } else if (action.equals("update")) {
            Validations validations = new Validations();
            if (request.getParameter("password").equals(request.getParameter("confirmPassword"))) {
                if (validations.isPasswordValid(request.getParameter("password")) || request.getParameter("password").length() == 0) {

                    User user1 = new User();
                    user1.setFullName(request.getParameter("fullName"));
                    user1.setNickName(request.getParameter("nickName"));
                    user1.setPassword(request.getParameter("password"));
                    user1.setUrl("images/" + request.getParameter("profilePic"));
                    String dob = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");

                    user1.setBirthDate(dob);

                    user = UserDB.UpdateUser(user1, user);
                    try {
                        //users = UserDB.selectUsers();
                        users = UserDB.selectUsersNotFollowing(user.getEmailAddress());
                        tweets = UserDB.selectTweets();
                        session.setAttribute("users", users);
                        session.setAttribute("tweets", tweets);
                    } catch (SQLException ex) {
                        Logger.getLogger(homeServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("trying to check updated user: " + user.toString());
                    session.setAttribute("user", user);
                    //  session.setAttribute("fullName", user.getFullName());
                    // session.setAttribute("nickName", user.getNickName());
                    url = "/home.jsp";
                } else {
                    String message = "Password length should be more than 6 characters";
                    session.setAttribute("message", message);
                    url = "/update.jsp";
                }
            } else {
                String message = "Password and confirm password should be the same";
                session.setAttribute("message", message);
                url = "/update.jsp";
            }
        } else if (action.equals("delete")) {
            String mail = request.getParameter("userID");
            String userTweet = request.getParameter("userTweet");
            try {
                UserDB.delete(mail, userTweet);
                tweets = UserDB.selectTweets();
                tweetsUser = UserDB.selectTweetsUser(user);
                users = UserDB.selectUsersNotFollowing(user.getEmailAddress());
                //users = UserDB.selectUsers();
                
                  tweetsUserCount = UserDB.selectTweetsUserCount(user);
                session.setAttribute("tweetsUserCount", tweetsUserCount);
                session.setAttribute("presentUser", user);
                session.setAttribute("tweets", tweets);
                session.setAttribute("users", users);
                session.setAttribute("tweetsUser", tweetsUser);

            } catch (SQLException ex) {
                Logger.getLogger(homeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  else if (action.equals("follow")) {
            User userNow = user;
            list = new ArrayList<>();
            String followId = request.getParameter("followId");
            try {
                boolean follower = UserDB.selectFollow(followId);
                
                if(!follower){
                    UserDB.insertFollow(userNow, followId);

                }
                else{
                    UserDB.deleteFollow(followId);

                }
                
                list = UserDB.selectFollowList(userNow.getEmailAddress());
                
                tweets = UserDB.selectTweets();
                tweetsUser = UserDB.selectTweetsUser(user);
                users = UserDB.selectUsersNotFollowing(user.getEmailAddress());
                  tweetsUserCount = UserDB.selectTweetsUserCount(user);
                session.setAttribute("tweetsUserCount", tweetsUserCount);
                
                
                                session.setAttribute("selectFollowingUser", UserDB.selectFollowingUser(user.getEmailAddress()));
                session.setAttribute("followersCount",UserDB.selectFollowerOfUserList(user.getEmailAddress()).size() );
                
                session.setAttribute("userFollowList", list);
                session.setAttribute("presentUser", user);
                session.setAttribute("tweets", tweets);
                session.setAttribute("users", users);
                session.setAttribute("tweetsUser", tweetsUser);
            } catch (SQLException ex) {
                Logger.getLogger(homeServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
        //getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
