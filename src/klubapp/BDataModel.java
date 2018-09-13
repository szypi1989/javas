package klubapp;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class BDataModel<T> extends AbstractTableModel {

    private List<T> rows;
    private String[] colNames;
    private String[] propNames;

    private static class Accessors {

        Method read;
        Method write;
    }
    private Map<String, Accessors> accMap = new HashMap<String, Accessors>();

    public BDataModel(List<T> objList, String[] propNames, String[] colNames) {
        if (objList == null || objList.isEmpty()) {
            throw new IllegalArgumentException("BeanDataModel: empty source");
        }
        this.rows = objList;
        this.colNames = new String[colNames.length];
        System.arraycopy(colNames, 0, this.colNames, 0, colNames.length);
        this.propNames = new String[propNames.length];
        System.arraycopy(propNames, 0, this.propNames, 0, propNames.length);
        for (int i = 0; i < propNames.length; i++) {
            accMap.put(propNames[i], new Accessors());
        }
        Class klas = objList.get(0).getClass();
        try {
            PropertyDescriptor[] pd = Introspector.getBeanInfo(klas, klas.getSuperclass()).getPropertyDescriptors();
            for (PropertyDescriptor desc : pd) {
                String name = desc.getName();
                Accessors acc = accMap.get(name);
                if (acc != null) {
                    acc.read = desc.getReadMethod();
                    acc.write = desc.getWriteMethod();
                }
            }
        } catch (IntrospectionException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int r, int c) {
        T obj = rows.get(r);
        Object retVal = null;
        try {
            Method getter = accMap.get(propNames[c]).read;
            retVal = getter.invoke(obj);
        } catch (Exception exe) {
            exe.printStackTrace();
        }
        return retVal;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public void setValueAt(Object val, int r, int c) {
        Object target = rows.get(c);
        try {
            Method setter = accMap.get(propNames[c]).write;
            if (setter == null) {
                return;
            }
            setter.invoke(target, val);
        } catch (Exception exc) {
            exc.printStackTrace();
            return;
        }
        fireTableCellUpdated(r, c);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return accMap.get(propNames[c]).write == null ? false : true;
    }

    public T getRow(int r) {
        return rows.get(r);
    }

    public void setRow(int r, T obj) {
        rows.set(r, obj);
        fireTableRowsUpdated(r, r);
    }

    public void addRow(T row) {
        rows.add(row);
        fireTableRowsInserted(rows.size() - 1, rows.size() - 1);
    }
    
    public void removeRow(int r){
        rows.remove(r);
        fireTableDataChanged();
    }
}
