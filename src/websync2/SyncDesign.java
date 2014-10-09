/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package websync2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Sridhar
 */
public class SyncDesign implements Runnable{
    ArrayList timers;
    String _BASE;
    String _TO;
    FTP ftp=null;
    public SyncDesign(String base, String to) {
        timers=new ArrayList<TimeNoter>();
        _BASE=base;
        _TO=to;
        ftp=new FTP();
        File f=new File(_TO);
        f.mkdirs();
    }
    
    private void initiator(File base) {
        File[] f=base.listFiles();
        if(f!=null){
            for(File temp:f){
                if(temp==null){
                    continue;
                }
                if(temp.isFile()){
                    processFile(temp);
                    
                }
                if(temp.isDirectory()){
                    initiator(temp);
                }
            }
        }
    }
    
    private int checkForMD5(String mD5) {
        for(int i=0;i<timers.size();i++){
            TimeNoter x=(TimeNoter) timers.get(i);
            if(x.getMD5().equals(mD5)){
                return i;
            }
        }
        return -1;
    }
    
    private void processFile(File temp) {
        String servP=TimeNoter.stripAndAdd(_BASE, _TO,temp);
        File servF=new File(servP);
        if(!servF.exists()){
            copyFile(temp,servF);
            int check=checkForMD5(TimeNoter.generateMD5(temp.getAbsolutePath()));
            if(check==-1){
                timers.add(new TimeNoter(temp));
            }
            else{
                TimeNoter someX=(TimeNoter) timers.get(check);
                someX.updateTime(temp.lastModified());
                timers.set(check, someX);
            }
        }
        else{
            int check=checkForMD5(TimeNoter.generateMD5(temp.getAbsolutePath()));
            if(check==-1){
                timers.add(new TimeNoter(temp));
                System.out.println("FILE EXISTS");
                
            }else{
                TimeNoter y=(TimeNoter) timers.get(check);
                long pastTime=y.ModTime;
                long nowTime=y.file.lastModified();
                if(pastTime<nowTime){
                    System.out.println("Past "+pastTime);
                    System.out.println("NOW "+nowTime);
                    servF.delete();
                    copyFile(temp, servF);
                    y.updateTime(nowTime);
                    timers.set(check, y);
                    y=(TimeNoter) timers.get(check);
                    System.out.println("UP Past "+y.ModTime);
                }
            }
            
        }
        
    }
    
    public void copyFileFTP(File temp, File servF){
        
        InputStream inputStream = null;
        try {
            FTPClient ftpclient =ftp.getFtpClient();
            ftpclient.changeToParentDirectory();
            String firstRemoteFile = servF.getAbsolutePath();
            String[] pathA=firstRemoteFile.split("/");
            if(pathA!=null){
                for (int i=0;i<pathA.length-1;i++) {
                    if("".equals(pathA[i])){
                        continue;
                    }
                    InputStream is=ftpclient.retrieveFileStream(pathA[i]);
                    int retCode=ftpclient.getReplyCode();
                    if(retCode==550){
                        ftpclient.makeDirectory(pathA[i]);
                    }
                    ftpclient.changeWorkingDirectory(pathA[i]);
                }
                inputStream=new FileInputStream(temp);
                boolean done=ftpclient.storeFile(pathA[pathA.length-1], inputStream);
                if (done) {
                    System.out.println("The first file is uploaded successfully.");
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SyncDesign.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SyncDesign.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(SyncDesign.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void copyFile(File temp, File servF){
        String x=servF.getAbsolutePath();
        x=x.substring(0, x.lastIndexOf("\\"));
        File temp2=new File(x);
        temp2.mkdirs();
        try {
            Files.copy(temp.toPath(), servF.toPath());
        } catch (IOException ex) {
            Logger.getLogger(SyncDesign.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void manager(File base){
        File[] f=base.listFiles();
        if(f!=null){
            for(File temp:f){
                if(temp==null){
                    continue;
                }
                if(temp.isFile()){
                    String name=TimeNoter.stripAndAdd(_TO, _BASE, temp);
                    File mainFile=new File(name);
                    if(!mainFile.exists()){
                        temp.delete();
                    }
                }
                if(temp.isDirectory()){
                    manager(temp);
                }
            }
        }
    }
    
    @Override
    public void run() {
        while(true){
            initiator(new File(_BASE));
            //manager(new File(_TO));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SyncDesign.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}

