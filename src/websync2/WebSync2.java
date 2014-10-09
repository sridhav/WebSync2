/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package websync2;

/**
 *
 * @author Sridhar
 */
public class WebSync2 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        SyncDesign s1=new SyncDesign("C:\\Users\\Sridhar\\Desktop\\","F:\\Prateek\\Desktop\\");
        SyncDesign s2=new SyncDesign("C:\\Users\\Sridhar\\My Documents\\","F:\\Prateek\\Documents\\");
        SyncDesign s3=new SyncDesign("C:\\Users\\Sridhar\\Downloads\\","F:\\Prateek\\Downloads\\");
        SyncDesign s4=new SyncDesign("C:\\Users\\Sridhar\\My Pictures\\","F:\\Prateek\\My Pictures\\");
        SyncDesign s5=new SyncDesign("C:\\Users\\Sridhar\\My Videos\\","F:\\Prateek\\My Videos\\");
        SyncDesign s6=new SyncDesign("C:\\Users\\Sridhar\\My Music\\","F:\\Prateek\\My Music\\");
        
        Thread t1=new Thread(s1);
        Thread t2=new Thread(s2);
        Thread t3=new Thread(s3);
        Thread t4=new Thread(s4);
        Thread t5=new Thread(s5);
        Thread t6=new Thread(s6);
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        */
        
        SyncDesign s1=new SyncDesign("C:/Users/Sridhar/Desktop/","/Desktop/");
        SyncDesign s2=new SyncDesign("C:/Users/Sridhar/My Documents/","/Documents/");
        SyncDesign s3=new SyncDesign("C:/Users/Sridhar/Downloads/","/Downloads/");
        SyncDesign s4=new SyncDesign("C:/Users/Sridhar/My Pictures/","/My Pictures/");
        SyncDesign s5=new SyncDesign("C:/Users/Sridhar/My Videos/","/My Videos/");
        SyncDesign s6=new SyncDesign("C:/Users/Sridhar/My Music/","/My Music/");
        
        Thread t1=new Thread(s1);
        Thread t2=new Thread(s2);
        Thread t3=new Thread(s3);
        Thread t4=new Thread(s4);
        Thread t5=new Thread(s5);
        Thread t6=new Thread(s6);
        
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        
        
        
    }
    
}
