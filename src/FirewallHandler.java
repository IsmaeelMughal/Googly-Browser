
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
public class FirewallHandler implements ActionListener{
    BrowserGUI refg;
    JFrame delFirewall;
    JLabel dL;
    DefaultListModel fireModel;
    JList fireList;

    public FirewallHandler(BrowserGUI g) {
        refg = g;
        initDelFrame();
    }
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(refg.mb.addFire))
        {
            String f = JOptionPane.showInputDialog("Enter Firewall:");
            if(f!=null)
            {
                try {
                        PrintWriter pw = new PrintWriter(new FileWriter(new File("Firewalls"),true));

                        pw.println(f);

                        pw.close();
                        JOptionPane.showMessageDialog(null, "Added To Firewall", "Firewall", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Oops! Firewall File Not Found.", "Firewall", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(ae.getSource().equals(refg.mb.delFire))
        {
            if(delFirewall.isVisible())
            {
                delFirewall.setVisible(false);
            }
            else
            {
                fireModel.clear();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("Firewalls")));
                    String line = br.readLine();
                    while(line!=null)
                    {
                        fireModel.add(0, line);
                        line = br.readLine();
                    }
                    
                    br.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Oops! Firewalls File Not Found.", "Firewall", JOptionPane.ERROR_MESSAGE);
                }
                delFirewall.setVisible(true);
            }
        }
    }
    
    public void initDelFrame()
    {
        delFirewall = new JFrame("Firewalls");
        dL = new JLabel("Double Click To delete a Firewall:");
        
        fireModel = new DefaultListModel();
        fireList = new JList(fireModel);
        
        delFirewall.setLayout(new BorderLayout());
        
        delFirewall.add(dL,BorderLayout.NORTH);
        delFirewall.add(fireList,BorderLayout.CENTER);
        delFirewall.add(new JScrollPane(fireList));
        
        fireList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if(me.getClickCount() == 2)
                {
                    fireModel.remove(fireList.getSelectedIndex());
                    try {
                        PrintWriter fw = new PrintWriter(new FileWriter(new File("Firewalls")));
                        fw.println("");
                        fw.close();
                    } catch (Exception e) {
                    }
                    try {
                        PrintWriter fw = new PrintWriter(new FileWriter(new File("Firewalls"),true));
                        for(int i = 0; i < fireModel.size();i++)
                        {
                            fw.println((String)fireModel.get(i));
                        }
                        fw.close();
                    } catch (Exception e) {
                    }
                }
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
        
        delFirewall.setSize(new Dimension(400,300));
        delFirewall.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width-delFirewall.getWidth(), 0);
        
        delFirewall.setVisible(false);
    }
    
}
