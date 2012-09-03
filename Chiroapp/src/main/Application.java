package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Simon
 */
public class Application {

    public static Application appInstance;
    private Connection conn;
    public JTable t;
    public ChiroTableModel tm;
    private JFrame fr;
    private JPanel jp;
    private PreparedStatement currentQuery;
    private String[] classes;
    private Thread swt;
    private String pw;

    //Singleton
    public static Application getInstance() {
        if (appInstance == null) {
            appInstance = new Application();
        }
        return appInstance;
    }

    private Application() {
        try {

            System.out.println("Wachtwoord:");
            Scanner sc = new Scanner(System.in);
            pw = sc.nextLine();
            swt = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    }
                    fr = new JFrame("Chiro Bevere Administratie");
                    tm = new ChiroTableModel();
                    t = new JTable();
                    jp = new JPanel(new BorderLayout());
                    jp.add(t, BorderLayout.CENTER);

                    //JCombobox opbouwen voor het selecteren van de afdelingen
                    classes = new String[]{"Ribbel", "Speelclub", "Rakwi", "Tito", "Keti", "Aspi", "Leiding", "Oud-Leiding"};
                    final JComboBox b = new JComboBox(classes);
                    //Zorgen dat de combobox filtert
                    b.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            try {
                                PreparedStatement p = conn.prepareStatement("SELECT * FROM ((SELECT * FROM CH_Lid WHERE Afdeling=?) AS lid LEFT JOIN CH_Familie ON lid.FamilieID = CH_Familie.FamilieID)");
                                p.setString(1, classes[b.getSelectedIndex()]);
                                showNewInfo(p);
                            } catch (SQLException ex) {
                                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    jp.add(b, BorderLayout.NORTH);
                    fr.setContentPane(jp);

                    //De connectie moet eerst afgesloten worden
                    fr.setDefaultCloseOperation(0);

                    //Menu aanmaken
                    JMenuBar jmb = new JMenuBar();
                    JMenu jm = new JMenu("Een menu!");
                    JMenuItem jmi = new JMenuItem(new AbstractAction("Voeg nieuwe gebruiker toe") {

                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            JDialog jd = new NieuweGebruikerDialoog(fr);
                            jd.setVisible(true);
                        }
                    });
                    JMenuItem jmi2 = new JMenuItem(new AbstractAction("Verwijder gebruiker") {

                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            try {
                                PreparedStatement p = conn.prepareStatement("DELETE FROM CH_Lid WHERE LidID = ?");
                                p.setInt(1, ((Integer) tm.getValueAt(t.getSelectedRow(), 0)));
                                p.execute();
                                renewInfo();
                            } catch (SQLException ex) {
                                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    });

                    JMenuItem jmi3 = new JMenuItem(new AbstractAction("Ga naar het volgend werkjaar") {

                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            try {
                                PreparedStatement p = conn.prepareStatement("UPDATE CH_Lid SET Jaar = Jaar+1");
                                p.execute();
                                for (int i = 0; i < 6; i++) {
                                    PreparedStatement ps = conn.prepareStatement("UPDATE CH_Lid SET Afdeling = ?, Jaar = ? WHERE Jaar = ? AND Afdeling = ?");
                                    ps.setString(1, classes[i + 1]);
                                    ps.setInt(2, 1);
                                    ps.setInt(3, 3);
                                    ps.setString(4, classes[i]);
                                    ps.execute();
                                }
                                renewInfo();
                            } catch (SQLException ex) {
                                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                    jm.add(jmi);
                    jm.add(jmi2);
                    jm.add(jmi3);
                    jmb.add(jm);
                    fr.setJMenuBar(jmb);

                    //Connectie afsluiten wanneer het venster gesloten wordt
                    fr.addWindowListener(new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent we) {
                            closeApplication();
                        }
                    });
                }
            });
            SwingUtilities.invokeLater(swt);
            //Verbinding make
            conn = dbConnect("jdbc:mysql://chirobevere.be/jnet1231_ledenlijst", "donothack", pw);
            //Gegevens afhalen
            PreparedStatement p = conn.prepareStatement("SELECT * FROM (CH_Lid LEFT JOIN CH_Familie ON CH_Lid.FamilieID = CH_Familie.FamilieID)");
            currentQuery = conn.prepareStatement("SELECT * FROM CH_Lid");
            showNewInfo(p);
//            Timer t = new Timer(1000, new ActionListener() {
//
//                @Override
//                public void actionPerformed(ActionEvent ae) {
//                    renewInfo();
//                }
//            });
//            t.start();
            fr.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    //Na een update de informatie vernieuwen (de laatste query opnieuw doen)
    public void renewInfo() {
        showNewInfo(currentQuery);
    }

    public void showNewInfo(PreparedStatement p) {
        currentQuery = p;
        try {
            ResultSet set = p.executeQuery();
            swt.join();
            tm.setInfo(set);
            t.setModel(tm);
            fr.pack();
        } catch (InterruptedException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("error in showNewInfo");
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        getInstance();
    }

    public void closeApplication() {
        try {
            //Er kan ook op de afsluitknop geklikt worden voor het conn-veld geinitialiseerd is, vandaar deze check.
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
        }
        System.exit(0);
    }

    public Connection dbConnect(String db_connect_string, String db_userid, String db_password) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(
                    db_connect_string, db_userid, db_password);
            return conn;
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null, "Geen verbinding met server mogelijk. Probeer het opnieuw wanneer er internetverbinding beschikbaar is");
            closeApplication();
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getClasses() {
        return classes;
    }
}
