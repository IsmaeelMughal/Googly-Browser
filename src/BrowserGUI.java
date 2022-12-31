import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
public class BrowserGUI {
    JFrame fr;
    
    
    JPanel upper;
    BtnPanelInterface btns;
    URLPanelInterface url;
    EditorPanelInterface sitePnl;
    Container c;
    ButtonHandler btnHnd;
    HistoryFrame hFr;
    FavoriteFrame favFr;
    BrowserMenuBar mb;
       
    Stack<String> prevStack = new Stack<String>();          // for storing url of previous
    Stack<String> nextStack = new Stack<String>();          // for storing url of next

    public BrowserGUI() {
        initBrowserGUI();
    }
    
    public void initBrowserGUI()
    {
        fr = new JFrame("BROWSER");
        
        
        upper = new JPanel();
        
        btnHnd = new ButtonHandler(this);
        hFr = new HistoryFrame(this);
        favFr = new FavoriteFrame(this);
        mb = new BrowserMenuBar(this, btnHnd);
        c = fr.getContentPane();
        c.setLayout(new FlowLayout());
        
        upper.setLayout(new GridLayout(2, 1));
        
        btns = new BtnPanelInterface(this);
        url = new URLPanelInterface(this);
        sitePnl = new EditorPanelInterface(this);
       
        
        fr.setLayout(new BorderLayout());
         
        
        
        upper.add(btns.btnPnl);
        upper.add(url.urlPnl);
        
        
        
        fr.add(upper,BorderLayout.NORTH);
        fr.add(sitePnl.edPan,BorderLayout.CENTER);
        
        fr.add(new JScrollPane(sitePnl.edPan),BorderLayout.CENTER);
        
        fr.setMinimumSize(new Dimension(800, 800));
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
        
        fr.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent we) {
            }

            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(new File("FirewallStatus")));
                    if(mb.enabl.isSelected())
                    {
                        pw.println("1");
                    }
                    else
                    {
                        pw.println("0");
                    }
                    pw.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Firewall Status not Updated", "Firewall Status", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void windowClosed(WindowEvent we) {
            }

            @Override
            public void windowIconified(WindowEvent we) {
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
            }

            @Override
            public void windowActivated(WindowEvent we) {
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
    }
}
