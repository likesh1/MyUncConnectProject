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
public class Userfollow implements Serializable{
    
    String userId, emailAddress, followOption;

    public Userfollow() {
    }
    
    
    public Userfollow(String userId, String emailAddress, String followOption) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.followOption = followOption;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFollowOption() {
        return followOption;
    }

    public void setFollowOption(String followOption) {
        this.followOption = followOption;
    }

    @Override
    public String toString() {
        return "Userfollow{" + "userId=" + userId + ", emailAddress=" + emailAddress + ", followOption=" + followOption + '}';
    }
    
    
    
}
