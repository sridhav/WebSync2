/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websync2;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sridhar
 */

public class TimeNoter {
    
    File file;
    String MD5;
    long ModTime;
    
    public TimeNoter(File file){
        this.file=file;
        MD5=generateMD5(file.getAbsolutePath());
        ModTime=file.lastModified();
    }
    
    public void updateTime(long time){
        ModTime=time;
    }
      
    public String getMD5(){
        return MD5;
    }
    static String generateMD5(String MD5att){
        String x=null;
        try {
            byte[] b=MD5att.getBytes("UTF-8");
            MessageDigest md=MessageDigest.getInstance("MD5");
            byte[] dig=md.digest(b);
            x=new String(dig, "UTF-8");

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TimeNoter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TimeNoter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x;
    }
    
    
     public static String stripAndAdd(String winPath, String servPath, File file){
        String name=file.getAbsolutePath();
        name=name.replace(winPath, servPath);
        return name;
    }
}
