/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smblandiscover;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author jorge
 */
public final class Core {

    private DefaultListModel lm;

    public Core(DefaultListModel lm) {
        this.lm = lm;
    }

    private String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "0.0.0.0";
    }

    private ArrayList<String> getIps() {
        ArrayList<String> result = new ArrayList<String>();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {

                NetworkInterface a = interfaces.nextElement();

                for (int i = 0; i < a.getInterfaceAddresses().size(); i++) {
                    try {
                        Inet4Address ina = (Inet4Address) Inet4Address.getByAddress(a.getInterfaceAddresses().get(i).getAddress().getAddress());
                        if (!ina.isLoopbackAddress()) {
                           // System.out.println(a.getDisplayName());
                           // System.out.println("  address:\t" + ina.getHostAddress());
                            result.add(ina.getHostAddress());
                        }
                    } catch (Exception e) {
                    }

                }

            }
        } catch (SocketException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private String getAddrPrefix(String address) throws Exception {
        if (address.equals("0.0.0.0") || address.equals("127.0.0.1")) {
            throw new Exception("Not connected");
        } else {
            String res = (address.substring(0, address.lastIndexOf(".") + 1));
            return res;
        }
    }

    public ArrayList<String> prefixes() throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        for (String i : getIps()) {
            if (!result.contains(getAddrPrefix(i))) {
                result.add(getAddrPrefix(i));
            }
        }
        return result;
    }

    public void check() throws Exception {
        // if (MainForm.counter == 0) {
        lm.clear();
        for (String prefix : prefixes()) {
            for (int i = 1; i < 255; i++) {
                String ip = prefix + String.valueOf(i);
                MainForm.counter++;
                new ThCheck(lm, ip).start();
            }
        }
        // }
    }
}
