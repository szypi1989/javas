package klubapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddPanel<T> extends JFrame {

    private JFrame jf;
    private JTable jt;
    public JTextField[] tf;
    public JLabel[] lab;
    private JButton ok;
    private BDataModel<T> model;
    private String[] data;
    private JTextField ob;
    private JPanel jp;
    public int n;
    public Object ot[];
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public AddPanel(BDataModel<T> model, ActionListener al) {
        super("Nowy rekord");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GridLayout gl = new GridLayout(0, 2, 3, 3);
        setLayout(gl);
        setPreferredSize(new Dimension(400, 200));
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
        n = model.getColumnCount();
        tf = new JTextField[n];
        lab = new JLabel[n];
        for (int i = 0; i < n; i++) {
            String name = model.getColumnName(i);
            lab[i] = new JLabel(name);
            lab[i].setHorizontalAlignment(SwingConstants.RIGHT);
            getContentPane().add(lab[i]);
            tf[i] = new JTextField();
            getContentPane().add(tf[i]);
        }
        getContentPane().add(button("Zatwierdź", "cmd:add", this, al));
        getContentPane().add(button("Wyczyść", "cmd:clear", this, al));
//        add(new Panel());
        pack();
//        setVisible(true);
    }

    private Component button(String string, String string0, AddPanel beanTableGUI, ActionListener al) {
        JButton jb = new JButton(string);
        jb.setActionCommand(string0);
        jb.addActionListener(al);
        return jb;
    }
}
