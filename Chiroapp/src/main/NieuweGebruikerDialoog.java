package main;

import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class NieuweGebruikerDialoog extends JDialog {

    private final JTextField voorNaamInput;
    private final JTextField familieNaamInput;
    private final JComboBox<String> afdelingInput;
    private final JComboBox<Integer> dagInput;
    private final JComboBox<String> maandInput;
    private final JComboBox<Integer> jaarInput;
    private final JCheckBox lidGeldBetaaldInput;
    private final JCheckBox gaatMeeOpKampInput;
    private final JCheckBox kampBetaaldInput;
    private final JCheckBox okPasInput;
    private final JTextField familieInput;
    private final JComboBox jaarAfdelingInput;

    public NieuweGebruikerDialoog(JFrame fr) {
        super(fr);
        JLabel voorNaamLabel = new JLabel("Voornaam");
        JLabel familieNaamLabel = new JLabel("familienaam");
        JLabel afdelingLabel = new JLabel("Afdeling");
        JLabel jaarAfdelingLabel = new JLabel("Jaar (1e of 2e)");
        JLabel geboorteDatumLabel = new JLabel("Geboortedatum");
        JLabel lidGeldBetaaldLabel = new JLabel("Lidgeldbetaald");
        JLabel gaatMeeOpKampLabel = new JLabel("Gaat mee op kamp");
        JLabel kampBetaaldLabel = new JLabel("Kamp betaald");
        JLabel okPasLabel = new JLabel("OK Pas");
        JLabel familieLabel = new JLabel("Familie al in systeem");
        voorNaamInput = new JTextField();
        familieNaamInput = new JTextField();
        afdelingInput = new JComboBox(Application.getInstance().getClasses());
        jaarAfdelingInput = new JComboBox(new Integer[]{1, 2});
        Integer[] dagen = new Integer[31];
        for (int i = 0; i < 31; i++) {
            dagen[i] = Integer.valueOf(i + 1);
        }
        dagInput = new JComboBox(dagen);
        maandInput = new JComboBox(new String[]{"Januari", "Februari", "Maart", "April", "Mei", "Juni", "Juli", "Augustus", "September", "Oktober", "November", "December"});
        int year = Calendar.getInstance().get(1);
        Integer[] years = new Integer[100];
        for (int i = 0; i < 100; i++) {
            years[i] = Integer.valueOf(year - (100 - i));
        }
        jaarInput = new JComboBox(years);
        lidGeldBetaaldInput = new JCheckBox();
        gaatMeeOpKampInput = new JCheckBox();
        kampBetaaldInput = new JCheckBox();
        okPasInput = new JCheckBox();
        familieInput = new JTextField();
        JButton maakGebruiker = new JButton(new AbstractAction("Voeg gebruiker toe") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("test");
//                try {
//                    PreparedStatement p = Application.getInstance().getConnection().prepareStatement("INSERT INTO CH_Lid(Voornaam, Familienaam, Afdeling, Jaar, Geboortedatum, FamilieID, LidgeldBetaald, Kamp, KampBetaald, OKPas) VALUES (?,?,?,?,?,?,?,?,?,?)");
//
//                    p.setString(1, NieuweGebruikerDialoog.this.voorNaamInput.getText());
//                    p.setString(2, NieuweGebruikerDialoog.this.familieNaamInput.getText());
//                    p.setString(3, (String) NieuweGebruikerDialoog.this.afdelingInput.getSelectedItem());
//                    p.setInt(4, (Integer) NieuweGebruikerDialoog.this.jaarAfdelingInput.getSelectedItem());
//                    p.setDate(5, NieuweGebruikerDialoog.this.convertDate(((Integer) NieuweGebruikerDialoog.this.dagInput.getSelectedItem()).intValue(), NieuweGebruikerDialoog.this.maandInput.getSelectedIndex() + 1, ((Integer) NieuweGebruikerDialoog.this.jaarInput.getSelectedItem()).intValue()));
//                    p.setInt(6, 1);
//                    p.setBoolean(7, NieuweGebruikerDialoog.this.lidGeldBetaaldInput.isSelected());
//                    p.setBoolean(8, NieuweGebruikerDialoog.this.gaatMeeOpKampInput.isSelected());
//                    p.setBoolean(9, NieuweGebruikerDialoog.this.kampBetaaldInput.isSelected());
//                    p.setBoolean(10, NieuweGebruikerDialoog.this.okPasInput.isSelected());
//                    p.execute();
//                    Application.getInstance().renewInfo();
//                    NieuweGebruikerDialoog.this.setVisible(false);
//                } catch (SQLException ex) {
//                    Logger.getLogger(NieuweGebruikerDialoog.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
        });
        JPanel p = new JPanel();
        GroupLayout layout = new GroupLayout(p);
        p.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(voorNaamLabel).addComponent(familieNaamLabel).addComponent(geboorteDatumLabel).addComponent(afdelingLabel).addComponent(jaarAfdelingLabel).addComponent(lidGeldBetaaldLabel).addComponent(gaatMeeOpKampLabel).addComponent(kampBetaaldLabel).addComponent(okPasLabel).addComponent(familieLabel)).addGroup(layout.createParallelGroup().addComponent(voorNaamInput).addComponent(familieNaamInput).addComponent(afdelingInput).addComponent(dagInput).addComponent(maandInput).addComponent(jaarInput).addComponent(jaarAfdelingInput).addComponent(lidGeldBetaaldInput).addComponent(gaatMeeOpKampInput).addComponent(kampBetaaldInput).addComponent(okPasInput).addComponent(familieInput).addComponent(maakGebruiker)));

        layout.setVerticalGroup(
                layout.createSequentialGroup().addGroup(layout.createParallelGroup().addComponent(voorNaamLabel).addComponent(voorNaamInput)).addGroup(layout.createParallelGroup().addComponent(familieNaamLabel).addComponent(familieNaamInput)).addGroup(layout.createParallelGroup().addComponent(afdelingLabel).addComponent(afdelingInput)).addGroup(layout.createParallelGroup().addComponent(jaarAfdelingLabel).addComponent(jaarAfdelingInput)).addGroup(layout.createParallelGroup().addComponent(geboorteDatumLabel).addGroup(layout.createSequentialGroup().addComponent(dagInput).addComponent(maandInput).addComponent(jaarInput))).addGroup(layout.createParallelGroup().addComponent(lidGeldBetaaldLabel).addComponent(lidGeldBetaaldInput)).addGroup(layout.createParallelGroup().addComponent(gaatMeeOpKampLabel).addComponent(gaatMeeOpKampInput)).addGroup(layout.createParallelGroup().addComponent(kampBetaaldLabel).addComponent(kampBetaaldInput)).addGroup(layout.createParallelGroup().addComponent(okPasLabel).addComponent(okPasInput)).addGroup(layout.createParallelGroup().addComponent(familieLabel).addComponent(familieInput)).addComponent(maakGebruiker));
        add(p);
        pack();
    }

    private java.sql.Date convertDate(int dag, int maand, int jaar) {
        java.util.Date utilDate = new java.util.Date(jaar - 1900, maand, dag);
        return new java.sql.Date(utilDate.getTime());
    }
}