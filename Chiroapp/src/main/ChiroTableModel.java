/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Simon
 */
public class ChiroTableModel extends AbstractTableModel {

    public List<List<Object>> data;
    private int numberOfRows;
    private int numberOfCols;

    public ChiroTableModel() {
        numberOfRows = 0;
        numberOfCols = 0;
    }

    @Override
    public int getRowCount() {
        return numberOfRows;
    }

    @Override
    public int getColumnCount() {
        return numberOfCols;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return data.get(i).get(i1);
    }

    public void setInfo(ResultSet set) {
        try {
            numberOfRows = 0;
            data = new ArrayList<>();
            int i = 0;
            while (set.next()) {
                numberOfCols = set.getMetaData().getColumnCount();
                numberOfRows++;
                ArrayList<Object> list = new ArrayList<>();
                while (i < numberOfCols) {
                    list.add(set.getObject(++i));
                }
                i = 0;
                data.add(list);
            }
            fireTableDataChanged();
        } catch (SQLException ex) {
            System.out.println("error");
            Logger.getLogger(ChiroTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
