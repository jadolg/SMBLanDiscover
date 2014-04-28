/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smblandiscover;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author akiel
 */
public class updt extends Thread {

    private JLabel label;

    public updt(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
        while (true) {
            label.setText("threads: " + String.valueOf(MainForm.counter));
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(updt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
