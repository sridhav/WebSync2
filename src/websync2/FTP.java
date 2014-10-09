/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package websync2;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Sridhar
 */
public class FTP{
    
    String server = "vemula.co.uk";
    int port = 21;
    String user = "sync@vemula.co.uk";
    String pass = "sekhar4995";
    FTPClient  ftpclient=null;
    public FTP(){
        
    }
    public FTP(String server, int port, String user, String pass){
        this.server=server;
        this.port=port;
        this.user=user;
        this.pass=pass;
        connect();
    }
    
    private void connect() {
        if(ftpclient==null || (!ftpclient.isConnected()) ){
            try {
                ftpclient=new FTPClient();
                ftpclient.connect(server, port);
                ftpclient.login(user, pass);
                ftpclient.enterLocalPassiveMode();
                ftpclient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
            } catch (IOException ex) {
                Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public FTPClient getFtpClient(){
        connect();
        return ftpclient;
    }
    
}
