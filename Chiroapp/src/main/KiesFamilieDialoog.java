package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
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
        int id;
        try {
            rs.beforeFirst();
            final JTable t = new JTable();
            final ChiroTableModel ctm = new ChiroTableModel();
            ctm.setInfo(rs);
            t.setModel(ctm);
            JPanel p = new JPanel(new BorderLayout());
            p.add(new JLabel("Kies familie"), BorderLayout.NORTH);
            p.add(new JButton(new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent ae) {
//                    id = (int) ctm.getValueAt(t.getSelectedRow(), 1);
                }
            }), BorderLayout.SOUTH);
            add(t, BorderLayout.CENTER);
            pack();
            setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(KiesFamilieDialoog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
