
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PC
 */
public class FavoriteFrameHandler implements ActionListener{
    BrowserGUI refg;

    public FavoriteFrameHandler(BrowserGUI g) {
        refg = g;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Add Favorite"))
        {
            String title = JOptionPane.showInputDialog("Enter The Title:");
            if(title!=null)
            {
                try {
                        PrintWriter pw = new PrintWriter(new FileWriter(new File("Favorites"),true));

                        pw.println(title + " - " + refg.btnHnd.currentPage);

                        pw.close();
                        JOptionPane.showMessageDialog(null, "Added To Favorites", "Favorite", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Oops! Favorite File Not Found.", "Favorites", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(ae.getActionCommand().equals("View Favorite"))
        {
            if(refg.favFr.favPnl.isVisible())
            {
                refg.favFr.favPnl.setVisible(false);
                refg.favFr.fvmodel.clear();
                refg.favFr.fFr.pack();
            }
            else
            {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("Favorites")));
                    String line = br.readLine();
                    while(line!=null)
                    {
                        //line = line.substring(line.indexOf("-")+1, line.length()).trim();
                        refg.favFr.fvmodel.add(0, line);
                        line = br.readLine();
                    }
                    
                    br.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Oops! Favorite File Not Found.", "Favorites", JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                }
                refg.favFr.favPnl.setVisible(true);
                refg.favFr.fFr.pack();
            }
        }
    }
    
    
}
