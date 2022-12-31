
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import jdk.nashorn.internal.scripts.JO;

/*
This class is used to implement the GUI of functionality buttons 
Previous, Next, Home, Refresh, History, Favorite, Search
*/
public class BtnPanelInterface {
    JPanel btnPnl;
    BrowserGUI refg;
    //ButtonHandler btnHnd;
    JButton[] featureBtns = new JButton[7];
    String[] btnIconAddress = {"/ImageAssets/previous.png","/ImageAssets/next.png",
                                "/ImageAssets/home.png","/ImageAssets/refresh.png",
                                "/ImageAssets/history.png","/ImageAssets/favorite.png",
                                "/ImageAssets/search.png"};


    public BtnPanelInterface(BrowserGUI g) {
        refg = g;
        initBtnPanelInterface();
    }
    
    public void initBtnPanelInterface()
    {
        btnPnl = new JPanel();
        //btnHnd = new ButtonHandler(refg);
        btnPnl.setLayout(new GridLayout(1, 7));

        for (int i = 0; i<7; i++)
        {
            try {
                featureBtns[i] = new JButton();
                // setting icon for each button
                featureBtns[i].setIcon(new ImageIcon((new ImageIcon(this.getClass().getResource(btnIconAddress[i])).getImage().getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH))));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Image Icon Not set", "Image Icon", JOptionPane.ERROR_MESSAGE);
            }
            featureBtns[i].setFocusable(false);
            featureBtns[i].setBackground(Color.white);
            featureBtns[i].setBorder(null);
            btnPnl.add(featureBtns[i]);
            featureBtns[i].addActionListener(refg.btnHnd);
        }
        
        // by defalut Previous and Next are set to disabled
        featureBtns[0].setEnabled(false);
        featureBtns[1].setEnabled(false);
        
        btnPnl.setPreferredSize(new Dimension(700,100));
        
    }  
}
