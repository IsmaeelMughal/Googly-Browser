
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
public class URLPanelInterface {
    BrowserGUI refg;
    JPanel urlPnl;
    JTextField urlTxt;
    JButton go;
    //ButtonHandler btnHnd;
    
    public URLPanelInterface(BrowserGUI g) {
        refg = g;
        initURLPanelInterface();
    }
    public void initURLPanelInterface()
    {
        urlPnl = new JPanel();
        urlTxt = new JTextField();
        go = new JButton();
        GridBagConstraints c = new GridBagConstraints();
        //btnHnd = new ButtonHandler(refg);
        
        urlPnl.setLayout(new GridBagLayout());
        
       
        
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.99;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        urlPnl.add(urlTxt,c);
        
       
        c.weightx = 0.01;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 1;
        c.gridy = 0;
        urlPnl.add(go,c);
        
        try {
            go.setIcon(new ImageIcon((new ImageIcon(this.getClass().getResource("/ImageAssets/go.png")).getImage().getScaledInstance(90, 55, java.awt.Image.SCALE_SMOOTH))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 0);
        go.setBorder(new MatteBorder(5, 0, 5, 5, Color.DARK_GRAY));
        go.setFont(new Font("SansSarif", Font.BOLD, 14));
        go.setBackground(Color.WHITE);
        go.setFocusable(false);
        go.addActionListener(refg.btnHnd);
        
        border = BorderFactory.createLineBorder(Color.DARK_GRAY, 5);
        urlTxt.setBorder(border);
        urlTxt.setFont(new Font("SansSerif",Font.PLAIN,30));
        urlTxt.setText(readHomePageURL());
            
        urlTxt.addActionListener(refg.btnHnd);
        
       
        urlPnl.setPreferredSize(new Dimension(700, 50));
    }
    
    public String readHomePageURL()
    {
        BufferedReader br;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(new File("HomePage")));
            line = br.readLine();
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return line;
    }
}
