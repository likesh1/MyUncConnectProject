/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataaccess.UserDB;

import business.User;
import business.Validations;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author xl
 */
@WebServlet(name = "membershipServlet", urlPatterns = {"/membershipServlet"})
public class membershipServlet extends HttpServlet {

    String fullName, emailAddress, nickName, password, confirmPassword,salt;
    Date birthDate;
    String url, message = "",picUrl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        fullName = request.getParameter("fullName");
        emailAddress = request.getParameter("emailAddress");
        nickName = request.getParameter("nickName");
        password = request.getParameter("password");
        confirmPassword = request.getParameter("confirmPassword");
         salt = UserDB.getSalts();
        String dob = request.getParameter("year") + "-" + request.getParameter("month") + "-" + request.getParameter("day");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthDate = sdf.parse(dob);
        } catch (ParseException ex) {
            Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("fullName", fullName);
        request.setAttribute("nickName", nickName);
                           Date currentDate = new Date();
        request.setAttribute("currentDate", currentDate);
        User user = new User(fullName, emailAddress, nickName, password, dob,picUrl,salt);
        Validations validations = new Validations();

        url = "/signup.jsp";
        if (validations.isEmailValid(emailAddress)) {
            if (validations.isPasswordEqual(password, confirmPassword)) {
                if (validations.isPasswordValid(password)) {

                    try {
                        if (UserDB.select(emailAddress) == null) {
                            url = "/login.jsp";
                            System.out.println("inside buddy !!!!!!!!!!!!!!!!!!!!!!");
                            message = "Sign up successful!";
                            UserDB.insert(user);
                        } else {
                            message = "User Id already taken. Please try with another email Id.";
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(membershipServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    message = "Please enter a password of length atleast 7 characters";
                }
            } else {
                message = "Password and Confirm Password should be the same";
            }
        } else {
            message = "Please enter a valid email address.";
        }

        request.setAttribute("message", message);

        System.out.println(user.toString());

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
