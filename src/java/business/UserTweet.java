/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;

/**
 *
 * @author SUNIL VUPPALA
 */
public class UserTweet implements Serializable{
    
    String emailAddress, tweet, fullName, nickName;

    public UserTweet(String emailAddress, String tweet, String fullName, String nickName) {
        this.emailAddress = emailAddress;
        this.tweet = tweet;
        this.fullName = fullName;
        this.nickName = nickName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "UserTweet{" + "emailAddress=" + emailAddress + ", tweet=" + tweet + ", fullName=" + fullName + ", nickName=" + nickName + '}';
    }
    

    
}
