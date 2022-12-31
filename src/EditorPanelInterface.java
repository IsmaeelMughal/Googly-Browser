
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
public class EditorPanelInterface {
    BrowserGUI refg;
    JEditorPane edPan;
    HyperLinkHandler hlHnd;

    public EditorPanelInterface(BrowserGUI g) {
        refg = g;
        initEditorPanelInterface();
    }
    
    public void initEditorPanelInterface() 
    {
        edPan = new JEditorPane();
        hlHnd = new HyperLinkHandler(refg);
        
        try {
            edPan.setPage(refg.url.readHomePageURL());
            refg.btnHnd.addInHistory(refg.url.readHomePageURL());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Home Page Not found", "HomePage", JOptionPane.ERROR_MESSAGE);
        }
        
        edPan.addHyperlinkListener(hlHnd);
        edPan.setPreferredSize(new Dimension(700,500));
        edPan.setEditable(false);
    }
}
