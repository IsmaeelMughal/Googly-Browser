
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
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
public class FavoriteFrame {
    BrowserGUI refg;
    JFrame fFr;
    JButton addBtn;
    JButton viewBtn;
    JPanel btnsPnl;
    JPanel favPnl;
    DefaultListModel fvmodel;
    JList fvls;
    FavoriteFrameHandler favFrHnd;

    public FavoriteFrame(BrowserGUI g) {
        refg = g;
        initFavoriteFrame();
    }
    
    
    public void initFavoriteFrame()
    {
        fFr = new JFrame("Favorite");
        addBtn = new JButton("Add Favorite");
        viewBtn = new JButton("View Favorite");
        btnsPnl = new JPanel();
        favPnl = new JPanel();
        fvmodel = new DefaultListModel();
        fvls = new JList(fvmodel);
        favFrHnd = new FavoriteFrameHandler(refg);
        
        fFr.setLayout(new BorderLayout());
        
        fFr.add(btnsPnl,BorderLayout.NORTH);
        fFr.add(favPnl,BorderLayout.CENTER);
        
        btnsPnl.setLayout(new FlowLayout());
        btnsPnl.add(addBtn);
        btnsPnl.add(viewBtn);
        btnsPnl.setPreferredSize(new Dimension(500,50));
        
        favPnl.setLayout(new BorderLayout());
        favPnl.add(fvls);
        favPnl.add(new JScrollPane(fvls));
        favPnl.setPreferredSize(new Dimension(500, 300));
        favPnl.setVisible(false);
        
        addBtn.addActionListener(favFrHnd);
        addBtn.setFocusable(false);
        viewBtn.setFocusable(false);
        viewBtn.addActionListener(favFrHnd);
        
        fvls.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                int index = fvls.getSelectedIndex();
                String u = (String)fvmodel.get(index);
                u = u.substring(u.indexOf("-")+1,u.length()).trim();
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
        
        fFr.pack();
        //fFr.setSize(500, 500);
        fFr.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-fFr.getWidth(), 0);
        fFr.setVisible(false);
    }
}
