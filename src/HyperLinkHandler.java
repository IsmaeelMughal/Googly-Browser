
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
public class HyperLinkHandler implements HyperlinkListener{
    BrowserGUI refg;
    
    public HyperLinkHandler(BrowserGUI g) {
        refg = g;
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent he) {
        if (he.getSource() == refg.sitePnl.edPan) {
            if(he.getEventType()==HyperlinkEvent.EventType.ACTIVATED)
                    loadPage(he.getURL().toString());
        }
    }
    
    public void loadPage(String url){
        try{
            refg.prevStack.push(refg.url.urlTxt.getText());
            refg.sitePnl.edPan.setPage(url);
            refg.url.urlTxt.setText(url);
            refg.btnHnd.currentPage = url;

            if(!refg.btns.featureBtns[0].isEnabled())
            {
                refg.btns.featureBtns[0].setEnabled(true);
            }
            addInHistory(refg.btnHnd.currentPage);
        }
        catch(IOException ioexp){
            JOptionPane.showMessageDialog(null,"Oops! Page Not Found","Bad URL",JOptionPane.ERROR_MESSAGE);    
        }
    }
    
    
    public void addInHistory(String u)
    {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(Calendar.getInstance().getTime());
                
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File("History"),true));
            
            pw.println(u + " - " + timeStamp);
            
            pw.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
