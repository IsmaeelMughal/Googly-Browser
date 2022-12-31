
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
public class HistoryFrame {
    BrowserGUI refg;
    JFrame historyFr;
    DefaultListModel dlm ;
    JList list;  
   
    public HistoryFrame(BrowserGUI g) {
        refg = g;
        initHistoryFrame();
    }
    
    public void initHistoryFrame()
    {
        historyFr = new JFrame();
        dlm = new DefaultListModel();
        list = new JList(dlm);
        
        historyFr.setLayout(new BorderLayout());
        
        historyFr.add(list);
        historyFr.add(new JScrollPane(list));
        
        list.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int index = list.getSelectedIndex();
                String u = (String)dlm.get(index);
                u = u.substring(0, u.indexOf("-")).trim();
                refg.sitePnl.hlHnd.loadPage(u);
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        
        historyFr.setSize(500, 300);
        historyFr.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-historyFr.getWidth(), 0);
        
        historyFr.setVisible(false);
    }
}
