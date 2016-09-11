/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 *
 * @author SUNIL VUPPALA
 */
public class Validations {
    
    
    public boolean isEmailValid(String email){
      String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
      String email1 = email;
      Boolean b = email1.matches(EMAIL_REGEX);
      System.out.println("is e-mail: "+email1+" :Valid = " + b);

      return b;
}
    
    public boolean isPasswordValid(String password){
        if(password.length()>=7){
            return true;
        }
        else{
            return false;
        }
    }
     public boolean isPasswordEqual(String password, String confirmPassword){
         
         if(password.equals(confirmPassword)){
             return true;
         }
         else{
             return false;
         }
     }
}
