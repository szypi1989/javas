package klubapp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class SortPanel extends JFrame implements ActionListener{
    
    private JButton bSort, bUnsort, bDodawanie, bUsuwanie, bCzysc;
    private JLabel lbOpis;
    private JList lsDodane, lsDostepne;
    private JCheckBox cbOrder;
    private JScrollPane spDodane, spDostepne;
    private JPanel pSouth, pNorth, pCenter, pWest, pEast;
    private DefaultListModel modelList1, modelList2;

    public SortPanel() {
        super("Sortowanie");
        modelList2 = new DefaultListModel();
        spDodane = new JScrollPane();
        lsDodane = new JList(modelList2);
        lsDodane.setBorder(BorderFactory.createTitledBorder("Kolejność"));
        lsDodane.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spDodane.setPreferredSize(new Dimension(200, 200));
        spDodane.setViewportView(lsDodane);
        
        modelList1 = new DefaultListModel();
        spDostepne = new JScrollPane();
        lsDostepne = new JList(modelList1);
        lsDostepne.setBorder(BorderFactory.createTitledBorder("Dostępne"));
        lsDostepne.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spDostepne.setPreferredSize(new Dimension(200, 200));
        spDostepne.setViewportView(lsDostepne);
        
        bSort = new JButton();
        bSort.setText("Sort");
        
        bUnsort = new JButton();
        bUnsort.setText("Unsort");
        
        bDodawanie = new JButton();
        bDodawanie.setText(">>");
        bDodawanie.setAlignmentX(CENTER_ALIGNMENT);
        bDodawanie.addActionListener(this);
        
        bUsuwanie = new JButton();
        bUsuwanie.setText("<<");
        bUsuwanie.setAlignmentX(CENTER_ALIGNMENT);
        
        bCzysc = new JButton();
        bCzysc.setText("Czyść");
        bCzysc.setAlignmentX(CENTER_ALIGNMENT);
        bCzysc.addActionListener(this);
        
        lbOpis = new JLabel();
        lbOpis.setText("Klucze sortowania");
        lbOpis.setAlignmentX(LEFT_ALIGNMENT);
        
        cbOrder = new JCheckBox("Malejąco");
        
        Dimension filtr = new Dimension(5, 5); 
        
        pEast = new JPanel();
        pWest = new JPanel();
        pCenter = new JPanel();
        pNorth = new JPanel();
        pSouth = new JPanel();
        
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.add(bDodawanie);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(bUsuwanie);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(bCzysc);
        
        pWest.add(spDostepne);
        pEast.add(spDodane);
        
        pSouth.setLayout(new BoxLayout(pSouth, BoxLayout.X_AXIS));
        pSouth.add(Box.createGlue());
        pSouth.add(cbOrder);
        pSouth.add(Box.createRigidArea(filtr));
        pSouth.add(bSort);
        pSouth.add(Box.createRigidArea(filtr));
        pSouth.add(bUnsort);
        
        pNorth.setLayout(new BoxLayout(pNorth, BoxLayout.LINE_AXIS));
        pNorth.add(Box.createRigidArea(filtr));
        pNorth.add(lbOpis);
        
        setLayout(new BorderLayout());
        add(pEast, BorderLayout.EAST);
        add(pWest, BorderLayout.WEST);
        add(pCenter, BorderLayout.CENTER);
        add(pNorth, BorderLayout.NORTH);
        add(pSouth, BorderLayout.SOUTH);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 4, (screenSize.height - getHeight()) / 4);
        pack();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == bDodawanie) {
            modelList2.addElement("1");
        }
        else if (source == bCzysc) {
            modelList2.clear();
        }
    }
}
