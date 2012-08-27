/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Simon
 */
public class KiesFamilieDialoog extends JDialog {

    public KiesFamilieDialoog(ResultSet rs) {
        try {
            rs.beforeFirst();
            JTable t = new JTable();
            ChiroTableModel ctm = new ChiroTableModel();
            ctm.setInfo(rs);
            t.setModel(ctm);
            JPanel p = new JPanel();
            p.add(new JLabel("Kies familie"));
            add(t);
            pack();
            setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(KiesFamilieDialoog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
