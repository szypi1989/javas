package klubapp;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;


public class KlubApp 
{
    private TableGUI<Klub> gui;
    
    public KlubApp() throws ParseException, ParseException{
        List<Klub> elist = Klub.generate(100);
        String[] propNames = {"name", "adres", "dataZal", "prezes", "trener", "pojemnosc", "telefon"};
        String[] colNames = {"Nazwa", "Adres", "Data zał.", "Prezes", "Trener", "Poj. stadionu", "Telefon"};
        BDataModel<Klub> model = new BDataModel<Klub>(elist, propNames, colNames);
        gui = new TableGUI<Klub>("Aplikacja Kluby sportowe", model);
        TableCellRenderer cellRenderer = gui.getTable().getDefaultRenderer(Date.class);
        ((JLabel) cellRenderer).setHorizontalAlignment(JLabel.RIGHT);
        gui.pack();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
}

    public static void main(String[] args) throws ParseException 
    {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        new KlubApp();
    }

}
