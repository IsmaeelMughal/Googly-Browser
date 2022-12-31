
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

/*
This class add menu Bar in Browser
*/
public class BrowserMenuBar {
    BrowserGUI refg;
    JMenuBar mBar;                          // menu bar
    JMenu setHomePage;                      // to show menu for set HomePage
    JMenu setFireWall;                      // To set firewall
    JMenu addDelKeyword;                    // Sun menu of firewall
    JMenuItem addFire;                      // to add firewall
    JMenuItem delFire;                      // to delete firewall
    JMenu enableDisableKeyword;             // to enable or disable firewall
    JMenuItem shp;                          // to set HomePage
    ButtonHandler btnHnd;
    
    FirewallHandler fireHnd;
    
    ButtonGroup grp;                        // to group enable and disable
    JRadioButtonMenuItem enabl;                 // to enable firewall
    JRadioButtonMenuItem dabl;                  // to disable firewall
    
    public BrowserMenuBar(BrowserGUI g, ButtonHandler b) {
        refg = g;
        btnHnd = b;
        initBrowserMenuBar();
    }
    
    public void initBrowserMenuBar()
    {
        mBar = new JMenuBar();
        setHomePage = new JMenu("Set HomePage");
        setFireWall = new JMenu("Set FireWall");
        addDelKeyword = new JMenu("ADD/Delete Keyword");
        enableDisableKeyword = new JMenu("Enable/Disable");
        shp = new JMenuItem("Set HomePage");
        addFire = new JMenuItem("Add Firewall");
        delFire = new JMenuItem("Del Firewall");
        grp = new ButtonGroup();
        enabl = new JRadioButtonMenuItem("Enable");
        dabl = new JRadioButtonMenuItem("Disable");
        fireHnd = new FirewallHandler(refg);
        
        // adding menus
        mBar.add(setHomePage);
        mBar.add(setFireWall);
        
        setHomePage.setFont(new Font("SansSerif",Font.BOLD,20));
        setHomePage.add(shp);
        shp.addActionListener(btnHnd);
       
        setFireWall.setFont(new Font("SansSerif",Font.BOLD,20));
        setFireWall.add(addDelKeyword);     // adding sub menu
        addDelKeyword.add(addFire);
        addDelKeyword.add(delFire);
        
        // setting action listener on add and delete firewall
        addFire.addActionListener(fireHnd);
        delFire.addActionListener(fireHnd);
        
        setFireWall.add(enableDisableKeyword);
        
        // setting previous state of firewall
        if(isFirewallEnable())
        {
            enabl.setSelected(true);
        }
        else
        {
            dabl.setSelected(true);
        }
        grp.add(enabl);
        grp.add(dabl);
        
        enableDisableKeyword.add(enabl);
        enableDisableKeyword.add(dabl);
        
        
        refg.fr.setJMenuBar(mBar);
    }
    
    // To check for last firewall status
    public boolean isFirewallEnable()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("FirewallStatus")));
            String s = br.readLine();
            br.close();
            if(s.equals("1"))       // if enabled
            {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Firewall status was'nt read", "Firewall Status", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
