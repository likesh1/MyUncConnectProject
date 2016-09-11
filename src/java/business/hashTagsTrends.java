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
public class hashTagsTrends implements Serializable{
 
    String hashTag;
    int count;

    public hashTagsTrends() {
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "hashTagsTrends{" + "hashTag=" + hashTag + ", count=" + count + '}';
    }
    
}
