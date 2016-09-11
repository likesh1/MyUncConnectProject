/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import business.User;
import business.UserTweet;
import business.Userfollow;
import business.hashTagsTrends;
import java.io.*;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author xl
 */
public class UserDB {

    public static Connection connect() {
        Connection conn = null;
        
        try {
            System.out.println("inside connect!!!!!!!!");

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/twitter", "root", "");
            System.out.println("connected successfully");

        } catch (SQLException ex) {
            System.out.println("not conected");
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static long insert(User user) throws SQLException, NoSuchAlgorithmException {
        //implement insert into database
        Statement stmt = null;
        Connection conn = connect();
        String fullName, emailAddress, nickName, password, birthDate,salt;
        fullName = user.getFullName();
        emailAddress = user.getEmailAddress();
        nickName = user.getNickName();
        password = user.getPassword();
        birthDate = user.getBirthDate();

  salt = user.getSalt();
        String pass = UserDB.hashPassword(password);
        password = UserDB.hashAndSaltPassword(pass, salt);
        String sql = "INSERT INTO users " + "VALUES ('" + fullName + "','" + emailAddress + "','" + password + "','" + nickName + "','" + birthDate + "','images/default.png','" + salt + "')";

        System.out.println(sql);
        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        return 0;
    }

    public static long insertTweet(UserTweet userTweet) throws SQLException {
        //implement insert into database
        Statement stmt = null;
        Connection conn = connect();
        String emailAddress, tweet;

        emailAddress = userTweet.getEmailAddress();
        tweet = userTweet.getTweet();

        String sql = "INSERT INTO tweets " + "VALUES ('" + emailAddress + "','" + tweet + "','" + userTweet.getFullName() + "','" + userTweet.getNickName() + "',NOW())";

        System.out.println(sql);
        stmt = conn.createStatement();
        int count = stmt.executeUpdate(sql);
        return count;
    }
    
    
        public static long insertFollow(User user, String userFollow) throws SQLException {
        //implement insert into database
        Statement stmt = null;
        Connection conn = connect();
        String emailAddress, tweet;

        emailAddress = user.getEmailAddress();
        

        String sql = "INSERT INTO userFollow " + "VALUES ('"+user.getEmailAddress()+"','"+userFollow+"',NOW(),'unfollow')";

        System.out.println(sql);
        stmt = conn.createStatement();
        int count = stmt.executeUpdate(sql);
        return count;
    }
                public static long deleteFollow(String userFollow) throws SQLException {
        //implement insert into database
        Statement stmt = null;
        Connection conn = connect();
        String emailAddress, tweet;

           

        String sql = "DELETE FROM userFollow WHERE emailAddress = '"+userFollow+"'";

        System.out.println(sql);
        stmt = conn.createStatement();
        int count = stmt.executeUpdate(sql);
        return count;
    }
                
                       public static long insertHashTag(String hashTag) throws SQLException {
        //implement insert into database
        Statement stmt = null;
        Connection conn = connect();
  

        String sql = "INSERT INTO HashTag VALUES ('"+hashTag+"',1)";

        System.out.println(sql);
        stmt = conn.createStatement();
        int count = stmt.executeUpdate(sql);
        return count;
    }
          public static long upDateHashTag(String hashTag) throws SQLException {
        //implement insert into database
        Statement stmt = null;
        Connection conn = connect();
  

        String sql = "UPDATE HashTag SET hashTagCount = hashTagCount + 1  WHERE hashTagText = '"+hashTag+"'";

        System.out.println(sql);
        stmt = conn.createStatement();
        int count = stmt.executeUpdate(sql);
        return count;
    }
          
          public static ArrayList<hashTagsTrends> trendsCount() throws SQLException{
                      Statement stmt = null;
        Connection conn = connect();
        ArrayList<hashTagsTrends> arrayList = new ArrayList<>();

        String sql = "SELECT * FROM HashTag ORDER BY hashTagCount DESC";
         stmt = conn.createStatement();
         
         ResultSet rs = stmt.executeQuery(sql);
         while(rs != null && rs.next()){
             hashTagsTrends tagsTrends = new hashTagsTrends();
             tagsTrends.setHashTag(rs.getString("hashTagText"));
             tagsTrends.setCount(rs.getInt("hashTagCount"));
                
             arrayList.add(tagsTrends);
         }
         return arrayList;
          }
    
    
       public static boolean selectFollow(String emailAddress) throws SQLException {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
        Statement stmt = null;
        Connection conn = connect();
        String sql = "SELECT * FROM userFollow WHERE emailAddress = '" + emailAddress + "' ;";
        System.out.println(sql);
        stmt = conn.createStatement();
        System.out.println("create statement no problem");
        stmt.executeQuery(sql);
        System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);

        User user = new User();
        if (rs.next()) {

            return true;
        } else {
            return false;
        }

    }
       
       public static boolean hashTagIntoTable(String hashTag)throws SQLException{
                   Statement stmt = null;
        Connection conn = connect();
        String sql = "SELECT * FROM HashTag WHERE hashTagText = '" + hashTag + "' ;";
        System.out.println(sql);
        stmt = conn.createStatement();
        System.out.println("create statement no problem");
        stmt.executeQuery(sql);
        System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);

      
        if (rs.next()) {

            return true;
        } else {
            return false;
        }
       }
       
       
       
       
       
       
              public static ArrayList<User> selectFollowList(String emailAddress) throws SQLException {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<User> list =null;
        String sql = "SELECT * FROM USERS WHERE emailAddress IN (SELECT emailAddress FROM userFollow WHERE userId = '" + emailAddress + "')";
        System.out.println(sql);
        stmt = conn.createStatement();
        System.out.println("create statement no problem");
        stmt.executeQuery(sql);
        System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        
        list = new ArrayList<>();
        while(rs.next() && rs != null){
            User user = new User();
            user.setFullName(rs.getString("fullName"));
            user.setNickName(rs.getString("nickName"));
            user.setEmailAddress(rs.getString("emailAddress"));
            list.add(user);
        }

  return list;

    } 
              
                            public static ArrayList<User> selectFollowerOfUserList(String emailAddress) throws SQLException {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<User> list =null;
        String sql = "SELECT * FROM USERS WHERE emailAddress IN (SELECT userId FROM userFollow WHERE emailAddress = '" + emailAddress + "')";
        System.out.println(sql);
        stmt = conn.createStatement();
        System.out.println("create statement no problem");
        stmt.executeQuery(sql);
        System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        
        list = new ArrayList<>();
        while(rs.next() && rs != null){
            User user = new User();
            user.setFullName(rs.getString("fullName"));
            user.setNickName(rs.getString("nickName"));
            user.setEmailAddress(rs.getString("emailAddress"));
            list.add(user);
        }

  return list;

    } 
    

    public static User select(String emailAddress) throws SQLException {
        //search in the database and find the User, if does not exist return null; if exist make a User object and return it.
        Statement stmt = null;
        Connection conn = connect();
        String sql = "SELECT * FROM users WHERE emailAddress = '" + emailAddress + "' ;";
        System.out.println(sql);
        stmt = conn.createStatement();
        System.out.println("create statement no problem");
        stmt.executeQuery(sql);
        System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);

        User user = new User();
        if (rs.next()) {
            System.out.println("getting string from Rs" + rs.getString("fullName"));

            user.setFullName(rs.getString("fullName"));
            user.setEmailAddress(rs.getString("emailAddress"));
            user.setPassword(rs.getString("userPassword"));
            user.setNickName(rs.getString("nickName"));
            user.setBirthDate(rs.getDate("birthDate") + "");
            user.setUrl(rs.getString("picURL"));
            System.out.println("checking inside userDb" + user.toString());
            return user;
        } else {
            return null;
        }

    }

    public static ArrayList<User> selectUsers() throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        stmt = conn.createStatement();
        stmt.executeQuery(sql);

        ResultSet rs = stmt.executeQuery(sql);

        while (rs != null && rs.next()) {

            User user = new User();
            user.setFullName(rs.getString("fullName"));
            user.setEmailAddress(rs.getString("emailAddress"));
            user.setPassword(rs.getString("userPassword"));
            user.setNickName(rs.getString("nickName"));
            user.setBirthDate(rs.getDate("birthDate") + "");
            user.setUrl(rs.getString("picURL"));

            users.add(user);

        }

        return users;
    }
    
        public static ArrayList<User> selectUsersNotFollowing(String emailAddress) throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE emailAddress NOT IN (SELECT emailAddress FROM userfollow WHERE userId='"+emailAddress+"') AND emailAddress != '"+emailAddress+"';";
        
            System.out.println(sql);
        stmt = conn.createStatement();
        stmt.executeQuery(sql);

        ResultSet rs = stmt.executeQuery(sql);

        while (rs != null && rs.next()) {

            User user = new User();
            user.setFullName(rs.getString("fullName"));
            user.setEmailAddress(rs.getString("emailAddress"));
            user.setPassword(rs.getString("userPassword"));
            user.setNickName(rs.getString("nickName"));
            user.setBirthDate(rs.getDate("birthDate") + "");
            user.setUrl(rs.getString("picURL"));

            users.add(user);

        }

        return users;
    }

    public static ArrayList<UserTweet> selectTweets() throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<UserTweet> tweets = new ArrayList<>();
        String sql = "SELECT * FROM tweets ORDER BY timeTweet DESC;";
        stmt = conn.createStatement();
        //System.out.println("create statement no problem");
        stmt.executeQuery(sql);
        // System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {

            UserTweet tweet = new UserTweet(rs.getString("emailAddress"), rs.getString("tweet"), rs.getString("fullName"), rs.getString("nickName"));

            System.out.println(tweet.toString());
            tweets.add(tweet);

        }
        System.out.println(tweets.toString());
        return tweets;
    }

    public static ArrayList<UserTweet> selectTweetsUser(User user) throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<UserTweet> tweets = new ArrayList<>();
        String sql = "SELECT * FROM tweets WHERE emailAddress IN (SELECT userFollow.emailAddress FROM userFollow WHERE userId = '"+user.getEmailAddress()+"') OR emailAddress='"+user.getEmailAddress()+"' OR tweet LIKE '%@" + user.getNickName() + "%' ORDER BY timeTweet DESC;";
        stmt = conn.createStatement();
        System.out.println(sql);
        stmt.executeQuery(sql);
        // System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {

            UserTweet tweet = new UserTweet(rs.getString("emailAddress"), rs.getString("tweet"), rs.getString("fullName"), rs.getString("nickName"));

            System.out.println(tweet.toString());
            tweets.add(tweet);

        }
        System.out.println(tweets.toString());
        return tweets;
    }
    
       public static ArrayList<User> selectFollowingUser(String user) throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<User> followering = new ArrayList<>();
        String sql = "SELECT * FROM userFollow WHERE userId = '"+user+"'";
        stmt = conn.createStatement();
        System.out.println(sql);
        stmt.executeQuery(sql);
        // System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {

         User userFollower = new User();
         
         userFollower.setEmailAddress(rs.getString("emailAddress"));

          followering.add(userFollower);
        }
     
        return followering;
    }
        public static ArrayList<UserTweet> selectTweetsUserCount(User user) throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<UserTweet> tweets = new ArrayList<>();
        String sql = "SELECT * FROM tweets WHERE emailAddress = '"+user.getEmailAddress()+"' OR tweet LIKE '%@" + user.getNickName() + "%' ORDER BY timeTweet DESC;";
        stmt = conn.createStatement();
        System.out.println(sql);
        stmt.executeQuery(sql);
        // System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {

            UserTweet tweet = new UserTweet(rs.getString("emailAddress"), rs.getString("tweet"), rs.getString("fullName"), rs.getString("nickName"));

            System.out.println(tweet.toString());
            tweets.add(tweet);

        }
        System.out.println(tweets.toString());
        return tweets;
    }
    
        public static ArrayList<UserTweet> selectTweetsUserLogin(User user) throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<UserTweet> tweets = new ArrayList<>();
        String sql = "SELECT * FROM tweets WHERE timeTweet > (SELECT userTimeStamp FROM userLogin WHERE emailAddress='"+user.getEmailAddress()+"') AND (emailAddress !='" + user.getEmailAddress() + "' OR tweet LIKE '%@" + user.getNickName() + "%') ORDER BY timeTweet DESC;";
        stmt = conn.createStatement();
        System.out.println(sql);
        stmt.executeQuery(sql);
        // System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {

            UserTweet tweet = new UserTweet(rs.getString("emailAddress"), rs.getString("tweet"), rs.getString("fullName"), rs.getString("nickName"));

            System.out.println(tweet.toString());
            tweets.add(tweet);

        }
        System.out.println(tweets.toString());
            System.out.println("Size of tweets inside UserDb class is "+tweets.size());
        return tweets;
    }
        
                public static ArrayList<UserTweet> tweetsByHahTag(String hashTag) throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<UserTweet> tweets = new ArrayList<>();
        String sql = "SELECT * FROM tweets WHERE tweet LIKE '%#" + hashTag + "%' ORDER BY timeTweet DESC;";
        stmt = conn.createStatement();
        System.out.println(sql);
        stmt.executeQuery(sql);
        // System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {

            UserTweet tweet = new UserTweet(rs.getString("emailAddress"), rs.getString("tweet"), rs.getString("fullName"), rs.getString("nickName"));

            System.out.println(tweet.toString());
            tweets.add(tweet);

        }
        System.out.println(tweets.toString());
            System.out.println("Size of tweets inside UserDb class is "+tweets.size());
        return tweets;
    }
        
    
    
            public static ArrayList<User> selectFollowersLogin(User user) throws SQLException {
        Statement stmt = null;
        Connection conn = connect();
        ArrayList<User> followers = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE emailAddress IN (SELECT userId FROM userfollow WHERE emailAddress='"+user.getEmailAddress()+"' AND timeStampFollow > (SELECT userTimeStamp FROM userLogin WHERE emailAddress='"+user.getEmailAddress()+"'))";
        stmt = conn.createStatement();
        System.out.println(sql);
        stmt.executeQuery(sql);
        // System.out.println("execute query no problem");
        ResultSet rs = stmt.executeQuery(sql);
        while (rs != null && rs.next()) {

         User user1 = new User();
         user1.setEmailAddress(rs.getString("emailAddress"));
         user1.setFullName(rs.getString("fullName"));
         user1.setNickName(rs.getString("nickName"));

           //System.out.println(tweet.toString());
            followers.add(user1);
            

        }
        System.out.println(followers.toString());
        return followers;
    }
    
    private static final Random RANDOM = new SecureRandom();
    /**
     * Length of password. @see #generateRandomPassword()
     */
    public static final int PASSWORD_LENGTH = 8;

    public static String generateRandomPassword() {
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

        String pw = "";
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        System.out.println("new password is " + pw);
        return pw;
    }

    public static void UpdatePassword(String emailAddress, String password) throws SQLException {
        try {
            Statement stmt = null;
            Connection conn = connect();
            System.out.println(password);
            String sql = "UPDATE users SET userPassword = '" + password + "'WHERE emailAddress = '" + emailAddress + "';";
            System.out.println(sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("mistake in db connection");
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public static void SendEmail(String emailAddress, String password) throws MessagingException {
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        // String username="ikesh.pammina";
        final String password1 = "Vuppala01";
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");
        Authenticator authenticator = null;
        boolean auth = true;
        if (auth) {
            mailServerProperties.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private final PasswordAuthentication pa = new PasswordAuthentication("sunilkumar.vuppala01@gmail.com", password1.toCharArray());

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        }
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("sunilkumar.vuppala01@gmail.com"));
        generateMailMessage.setSubject("Changed Password ");
        String emailBody = "Your changed Password is " + password + "<br><br> Regards, <br>Twitter";
        System.out.println(emailBody + "" + emailAddress);
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        System.out.println(generateMailMessage + " " + generateMailMessage.getAllRecipients().toString());
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "sunilkumar.vuppala01", "Vuppala01");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    public static User UpdateUser(User user, User u) {
        System.out.println("Inside db class to check user: " + u.toString());
        try {
            Statement stmt = null;
            Connection conn = connect();
            String fullName, nickName, dob, password, fullName1, nickName1, dob1, password1, pic;
            fullName = u.getFullName();
            nickName = u.getNickName();
            password = u.getPassword();
            dob = u.getBirthDate();
            fullName1 = user.getFullName();
            nickName1 = user.getNickName();
            password1 = user.getPassword();
            dob1 = user.getBirthDate();
            pic = user.getUrl();
            if (fullName1.equals("")) {
                fullName1 = fullName;
            }

            if (dob1.equals("--")) {
                dob1 = dob;
            }
            if (password1.equals("")) {
                password1 = password;
            }
            if (pic.equals("images/")) {
                pic = u.getUrl();
            }
            System.out.println("pic value inside user: " + pic);
            System.out.println("checking the session user inside userDB: " + u.toString());
            String sql = "UPDATE users SET fullName = '" + fullName1 + "',userPassword = '" + password1 + "',birthDate = '" + dob1 + "',picURL = '" + pic + "' WHERE emailAddress = '" + u.getEmailAddress() + "';";
            System.out.println(sql);
            String sql2 = "UPDATE tweets SET fullName = '" + fullName1 + "' WHERE emailAddress = '" + u.getEmailAddress() + "';";

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            user.setFullName(fullName1);
            user.setNickName(u.getNickName());
            user.setPassword(password1);
            user.setEmailAddress(u.getEmailAddress());
            user.setBirthDate(dob1);
            user.setUrl(pic);
            return user;

        } catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void delete(String mail, String userTweet) throws SQLException {
        try {
            Statement stmt = null;
            Connection conn = connect();
            String sql = "DELETE FROM tweets WHERE emailAddress='" + mail + "' AND tweet='" + userTweet + "';";
            System.out.println(sql);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("mistake in db connection");
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void userLogin(User user) {
        try {
            Statement stmt = null;
            Connection conn = connect();
            String sqlInsert = "SELECT * FROM userLogin WHERE emailAddress='"+user.getEmailAddress()+"'";
            
            String sql =null;
            
            stmt = conn.createStatement();
            ResultSet rsInsert = stmt.executeQuery(sqlInsert);
            if(rsInsert.next()){
                sql = "UPDATE userLogin SET userTimeStamp=NOW() WHERE emailAddress='"+user.getEmailAddress()+"'";
                stmt.executeUpdate(sql);
                System.out.println(sql);
            }       else{
                sql = "INSERT INTO userLogin values ('"+user.getEmailAddress()+"',NOW())";
                stmt.executeUpdate(sql);
                System.out.println(sql);
                        }
            
        } catch (SQLException ex) {
            System.out.println("mistake in db connection");
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static String getSalts() {
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    public static String hashAndSaltPassword(String password, String salt)
            throws NoSuchAlgorithmException {

        return hashPassword(password + salt);
    }


}
