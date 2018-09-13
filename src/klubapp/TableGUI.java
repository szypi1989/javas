package klubapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableRowSorter;

public class TableGUI<T> extends JFrame implements ActionListener {
    
    private BDataModel<Klub> model;
    private JTable table;
    private TableRowSorter<BDataModel<Klub>> sorter;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu, pomocMenu;
    private JMenuItem openItem, saveItem, exitItem, aboutItem;
    private JButton bAdd, bDelete, bSort;
    private ModifyPanel modifypanel;
    private AddPanel addPanel;
    private SortPanel sortPanel;
    Dimension screenSize;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    
    public TableGUI(String title, final BDataModel<Klub> model) {
        super(title);
        
        pl_jfc();
        
        this.model = model;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(400,400);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(600, 400));
        
        fileMenu = new JMenu("Plik");
        pomocMenu = new JMenu("Pomoc");
        
        openItem = new JMenuItem("Otwórz", 'O');
        saveItem = new JMenuItem("Zapisz", 'Z');
        JSeparator separator = new JSeparator();
        exitItem = new JMenuItem("Wyjście", 'W');
        
        aboutItem = new JMenuItem("O programie");
        
        openItem.addActionListener(this);
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        saveItem.addActionListener(this);
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        exitItem.addActionListener(this);
        exitItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        aboutItem.addActionListener(this);
        
        menuBar.add(fileMenu);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(separator);
        fileMenu.add(exitItem);
        
        menuBar.add(pomocMenu);
        pomocMenu.add(aboutItem);
        
        setJMenuBar(menuBar);
        
        sorter = new TableRowSorter<BDataModel<Klub>>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        this.add(new JScrollPane(table));
        
        Box controls = Box.createHorizontalBox();
        controls.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        Dimension filter = new Dimension(5, 5);
        bAdd = new JButton("Dodaj rekord...");
        bAdd.addActionListener(this);
        
        bDelete = new JButton("Usuń rekord...");
        bDelete.addActionListener(this);
        
        bSort = new JButton("Sortuj...");
        bSort.addActionListener(this);
        
        controls.add(bAdd);
        controls.add(Box.createRigidArea(filter));
        controls.add(bDelete);
        controls.add(Box.createGlue());
        controls.add(bSort);
        
        add(controls, "South");
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()));
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 3) {
                    int r = table.rowAtPoint(e.getPoint());
                    r = table.convertColumnIndexToModel(r);
                    int c = table.columnAtPoint(e.getPoint());
                    c = table.convertColumnIndexToModel(c);
                    modifypanel = new ModifyPanel(model, TableGUI.this, r);
                }
            }
        });
    }
    
    public JTable getTable() {
        return table;
    }
    
    public BDataModel<Klub> getModel() {
        return model;
    }
    
    public TableRowSorter<BDataModel<Klub>> getSorter() {
        return sorter;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.endsWith("confirm")) {
            System.out.println(model.getRowCount());
            System.out.println(model.getColumnCount());
            System.out.println(model.getValueAt(1, 0));
            for (int i = 1; i < model.getColumnCount(); i++) {
                model.setValueAt(modifypanel.tf[0].getText(), modifypanel.c, 0);
            }            
            model.fireTableDataChanged();
            modifypanel.setVisible(false);
        } else if (cmd.endsWith("clear")) {
            for (int i = 0; i < model.getColumnCount(); i++) {
                addPanel.tf[i].setText("");
            }
        } else if (cmd.endsWith("add")) {
            try {
                String newKlub = addPanel.tf[0].getText();
                String[] newAdres = rozdziel(addPanel.tf[1].getText(), 3, ";");
                Date newData = dateFormat.parse(addPanel.tf[2].getText());
                String[] newPrezes = rozdziel(addPanel.tf[3].getText(), 2, ";");
                String[] newTrener = rozdziel(addPanel.tf[4].getText(), 2, ";");
                Integer newPoj = Integer.parseInt(addPanel.tf[5].getText());
                String newTelefon = addPanel.tf[6].getText();
                model.addRow(new Klub(newKlub, new Adres(newAdres[0], newAdres[1], Integer.parseInt(newAdres[2])),
                        newData, new Osoba(newPrezes[0], newPrezes[1]), new Osoba(newTrener[0], newTrener[1]),
                        newPoj, newTelefon));
                model.fireTableDataChanged();
                addPanel.setVisible(false);
            } catch (ParseException ex) {
                Logger.getLogger(TableGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Object source = e.getSource();
        if (source == bAdd) {
            addPanel = new AddPanel(model, TableGUI.this);
            addPanel.setVisible(true);
        } else if (source == bSort) {
            sortPanel = new SortPanel();
            sortPanel.setVisible(true);
        }
        else if (source == exitItem) {
            dispose();
        } else if (source == saveItem) {
            JFileChooser fc = new JFileChooser(".");
            fc.setDialogTitle("Zapisz w...");
            fc.setFileFilter(new FileFilter() {
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
                }
                
                public String getDescription() {
                    return "Pliki tekstowe";
                }
            });
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File plik = fc.getSelectedFile();
                
                try {
                    PrintWriter pw = new PrintWriter(plik);
                    for (int i = 0; i < model.getRowCount(); i++) {
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            if (j == 2) {
                                Date data = (Date) model.getValueAt(i, j);
                                pw.print(dateFormat.format(data) + ";");
                            } else {
                                pw.print(model.getValueAt(i, j));
                                if (j < model.getColumnCount() - 1) {
                                    pw.print(";");
                                }
                            }
                        }
                        pw.println();
                    }
                    pw.close();
                } catch (IOException ex) {
                    Logger.getLogger(TableGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } else if (source == openItem) {
            JFileChooser fc = new JFileChooser(".");
            fc.setDialogTitle("Wybierz plik");
            fc.setFileFilter(new FileFilter() {
                public boolean accept(File f) {
                    return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
                }
                
                public String getDescription() {
                    return "Pliki tekstowe";
                }
            });
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File plik = fc.getSelectedFile();
                try {
                    BufferedReader in = new BufferedReader(new FileReader(plik));
                    String s;
                    try {
                        while ((s = in.readLine()) != null) {
                            Scanner sc = new Scanner(s);
                            sc.useDelimiter(";");
                            Object[] row = new Object[11];
                            int i = 0;
                            while (sc.hasNext()) {
                                row[i] = sc.next();
                                i++;
                            }
                            try {
                                model.addRow(new Klub(row[0].toString(), new Adres(row[1].toString(), row[2].toString(),
                                        Integer.parseInt(row[3].toString())), dateFormat.parse(row[4].toString()), new Osoba(row[5].toString(),
                                        row[6].toString()), new Osoba(row[7].toString(), row[8].toString()),
                                        Integer.parseInt(row[9].toString()), row[10].toString()));
                            } catch (ParseException ex) {
                                Logger.getLogger(TableGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            model.fireTableDataChanged();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(TableGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Nie znaleziono pliku " + plik.getName(), "Ostrzeżenie",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } else if (source == bDelete) {
            int wiersz = table.getSelectedRow();
            if (wiersz > -1) {
                int sel = JOptionPane.showConfirmDialog(null, "Czy na pewno usunąć wybrany wiersz?", "Potwierdzenie",
                        JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
                if (sel == JOptionPane.OK_OPTION) {
                    model.removeRow(wiersz);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nie wybrano wiersza !!", "Ostrzeżenie",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else if (source == aboutItem) {
            JOptionPane optionpane = new JOptionPane();
            optionpane.setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()));
            optionpane.setMessage("Kluby Sportowe 1.0\n Autor: Piotr Robak");
            optionpane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionpane.createDialog(null, "O programie");
            dialog.setVisible(true);
        }
    }
    
    private Component button(String string, String string0, TableGUI beanTableGUI) {
        JButton jb = new JButton(string);
        jb.setActionCommand(string0);
        jb.addActionListener(this);
        return jb;
    }
    
    private Component check(String string, String string0, TableGUI beanTableGUI) {
        JCheckBox jb = new JCheckBox(string);
        jb.setActionCommand(string0);
        return jb;
    }
    
    private Component label(ImageIcon imageIcon) {
        return null;
    }
    
    private String[] rozdziel(String linia, int size, String znak) {
        String[] dane = new String[size];
        Scanner sc = new Scanner(linia);
        sc.useDelimiter(znak);
        int i = 0;
        while (sc.hasNext()) {
            dane[i] = sc.next();
            i++;
        }
        return dane;
    }
    
    private void pl_jfc() {
        UIManager.put("FileChooser.saveButtonText", "Zapisz");
        UIManager.put("FileChooser.openButtonText", "Otwórz");
        UIManager.put("FileChooser.cancelButtonText", "Anuluj");
        UIManager.put("FileChooser.updateButtonText", "Modyfikuj");
        UIManager.put("FileChooser.helpButtonText", "Pomoc");
        UIManager.put("FileChooser.fileNameLabelText", "Nazwa pliku:");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Pliki typu:");
        UIManager.put("FileChooser.upFolderToolTipText", "do góry o jeden poziom");
        UIManager.put("FileChooser.homeFolderToolTipText", "Folder domowy");
        UIManager.put("FileChooser.newFolderToolTipText", "Utwórz nowy folder");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Szczegóły");
        UIManager.put("FileChooser.lookInLabelText", "Szukaj w");
        UIManager.put("FileChooser.directoryOpenButtonText", "Otwórz");
    }
}