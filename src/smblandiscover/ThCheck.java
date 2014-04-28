/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smblandiscover;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import jcifs.smb.SmbFile;
import sun.net.NetworkClient;

/**
 *
 * @author jorge
 */
public class ThCheck extends Thread {

    private DefaultListModel lm;
    private String ip;

    public ThCheck(DefaultListModel lm, String ip) {
        this.lm = lm;
        this.ip = ip;
    }

    @Override
    public synchronized void run() {
        try {
            if ((avaliable(ip) || active(ip)) && !lm.contains(ip)) {
                lm.addElement(ip);
            }
            MainForm.counter--;
        } catch (UnknownHostException ex) {
            Logger.getLogger(ThCheck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThCheck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean avaliable(String ip) throws UnknownHostException, IOException {
        InetAddress mip = InetAddress.getByName(ip);
//        if (mip.isReachable(1000)) {
//            System.out.println(mip);
//        }
        return mip.isReachable(1000);
    }

    public boolean active(String ip) {
        //  System.out.println(ip);
        try {
            SmbFile f = new SmbFile("smb://" + ip);
            f.connect();
//            if (f.isDirectory()) {
//                for (String s : f.list()) {
//                    System.out.println(s);
//                }
//            }
            if (f.exists() && (f.isDirectory() || f.isFile())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
}
