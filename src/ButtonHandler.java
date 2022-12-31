
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/*
 This class is used to handle all the actions performed by Features of my Browser
 */
public class ButtonHandler implements ActionListener{
    BrowserGUI refg;
    String currentPage;                 // to hold the address of current loaded page
    
    public ButtonHandler(BrowserGUI g) {
        refg = g;
        currentPage = readHomePageURL();            // at first it will be at home
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == refg.url.urlTxt || ae.getSource() == refg.url.go)    // GO and ADDRESS BAR
        {
            if(refg.mb.enabl.isSelected() && !isFirewallPassed())           // check for firewall
            {
                JOptionPane.showMessageDialog(null, "Firewall Encountered", "FireWall", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
                try {
                    refg.prevStack.push(currentPage);       
                    refg.sitePnl.edPan.setPage(refg.url.urlTxt.getText());
                    refg.url.urlTxt.setText(refg.url.urlTxt.getText());
                    // update current page after loading page and storing previous on stack
                    currentPage = refg.url.urlTxt.getText();
                    if(!refg.btns.featureBtns[0].isEnabled())
                    {
                        refg.btns.featureBtns[0].setEnabled(true);
                    }
                    addInHistory(currentPage);          // update history
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Oops! Page Not Found", "Bad URL", JOptionPane.ERROR_MESSAGE);
                    //e.printStackTrace();
                }
           
        }
        else if(ae.getSource().equals(refg.btns.featureBtns[0]))     // PREVIOUS
        {
            refg.nextStack.push(currentPage);
            String stackUrl = refg.prevStack.pop(); 
            try {
                refg.sitePnl.edPan.setPage(stackUrl);
                refg.url.urlTxt.setText(stackUrl);
                currentPage = stackUrl;
                addInHistory(currentPage);      
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Oops! Page Not Found", "Bad URL", JOptionPane.ERROR_MESSAGE);
            }
            // After Moving to previous enabling next button 
            if(refg.prevStack.isEmpty())        // check for last page to go back
            {
                refg.btns.featureBtns[0].setEnabled(false);
            }
            if(!refg.btns.featureBtns[1].isEnabled())
            {
                refg.btns.featureBtns[1].setEnabled(true);
            }
        }
        else if(ae.getSource().equals(refg.btns.featureBtns[1]))   //NEXT
        {
            refg.prevStack.push(currentPage);
            String stackUrl = refg.nextStack.pop(); 
            
            try {
                refg.sitePnl.edPan.setPage(stackUrl);
                refg.url.urlTxt.setText(stackUrl);
                currentPage = stackUrl;
                addInHistory(currentPage);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Oops! Page Not Found", "Bad URL", JOptionPane.ERROR_MESSAGE);
            }
            
            if(refg.nextStack.isEmpty())
            {
                refg.btns.featureBtns[1].setEnabled(false);
            }
            if(!refg.btns.featureBtns[0].isEnabled())
            {
                refg.btns.featureBtns[0].setEnabled(true);
            }
        }
        else if(ae.getSource().equals(refg.btns.featureBtns[2]))     // HOME
        {
            if(!refg.prevStack.isEmpty())
            {
                refg.prevStack.push(currentPage);
                try {
                    refg.sitePnl.edPan.setPage(readHomePageURL());
                    refg.url.urlTxt.setText(readHomePageURL());
                    currentPage = readHomePageURL();
                    addInHistory(currentPage);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Oops! Page Not Found", "Bad URL", JOptionPane.ERROR_MESSAGE);
                }
                while(!refg.nextStack.isEmpty())
                {
                    refg.nextStack.pop();
                }
                refg.btns.featureBtns[1].setEnabled(false);
            }
            else
            { 
                try {
                    refg.sitePnl.edPan.setPage(readHomePageURL());
                    refg.url.urlTxt.setText(readHomePageURL());
                    currentPage = readHomePageURL();
                    addInHistory(currentPage);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Oops! Page Not Found", "Bad URL", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if(ae.getSource().equals(refg.btns.featureBtns[3]))     // REFRESH
        {
            try {
                refg.sitePnl.edPan.setPage(currentPage);
                refg.url.urlTxt.setText(currentPage);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Oops! Page Not Found", "Bad URL", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(ae.getSource().equals(refg.btns.featureBtns[4]))     // HISTORY
        {
            if(refg.hFr.historyFr.isVisible())
            {
                refg.hFr.historyFr.setVisible(false);
                refg.hFr.dlm.clear();
            }
            else
            {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("History")));
                    
                    String dateTime;
                    int index;
                    Date d1;
                    SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
                    Instant now = Instant.now();
                    Instant before = now.minus(Duration.ofDays(3));
                    Date dateBefore = Date.from(before);
                
                    String line = br.readLine();
                    while (line!=null) {   
                        if(line.length()!=0)
                        {
                            index = line.indexOf("-")+1;
                            dateTime = line.substring(index).trim();
                            d1 = dtf.parse(dateTime);   
                            if(d1.compareTo(dateBefore) > 0)
                            {
                                refg.hFr.dlm.add(0, line);
                            }
                            line = br.readLine();
                        }
                        else
                        {
                            line = br.readLine();
                        }
                    }
                    
                    br.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Oops! History File Not Found", "History", JOptionPane.ERROR_MESSAGE);
                }
                refg.hFr.historyFr.setVisible(true);
            }
        }
        else if(ae.getSource().equals(refg.btns.featureBtns[5]))     // FAVORITE
        {
            if(refg.favFr.fFr.isVisible())
            {
                refg.favFr.fFr.setVisible(false);
                refg.favFr.favPnl.setVisible(false);
            }
            else
            {
                refg.favFr.fFr.setVisible(true);
            }
        }
        else if(ae.getSource().equals(refg.btns.featureBtns[6]))     // SEARCH
        {
            String s = JOptionPane.showInputDialog("Search:");
            String pageTxt = refg.sitePnl.edPan.getText();
                
            if(s!=null)
            {
                int count = countOccurrences(pageTxt.toLowerCase(),s.toLowerCase());
                JOptionPane.showMessageDialog(null, "Total count = " + count, "Search", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else if(ae.getSource().equals(refg.mb.shp))                // SET HOMEPAGE
        {
            String newHomePage = JOptionPane.showInputDialog("Enter URL:");
            if(newHomePage != null)
            {
                if(isValidURL(newHomePage))
                {
                    try {
                        PrintWriter pw = new PrintWriter(new FileWriter(new File("HomePage")));

                        pw.println(newHomePage);

                        pw.close();
                        JOptionPane.showMessageDialog(null, "HomePage Changed Successfully", "Home Page", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Oops! Homepage File Not Fo", "Home Page", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "HomePage Not Changed", "Home Page", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    static int countOccurrences(String str, String word)
    {
        // split the string by spaces in a
        String a[] = str.split(" ");

        // search for pattern in a
        int count = 0;
        for (int i = 0; i < a.length; i++)
        {
        // if match found increase count
        if (word.equals(a[i]))
            count++;
        }

        return count;
    }
    
    // Function to validate URL
    // using regular expression
    public static boolean isValidURL(String url)
    {
        // Regex to check valid URL
        String regex = "((http|https)://)(www.)?"
              + "[a-zA-Z0-9@:%._\\+~#?&//=]"
              + "{2,256}\\.[a-z]"
              + "{2,6}\\b([-a-zA-Z0-9@:%"
              + "._\\+~#?&//=]*)";
 
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
 
        // If the string is empty
        // return false
        if (url == null) {
            return false;
        }
 
        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(url);
 
        // Return if the string
        // matched the ReGex
        return m.matches();
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
            JOptionPane.showMessageDialog(null, "Oops! Homepage File Not Found", "HomePage", JOptionPane.ERROR_MESSAGE);
        }
        return line;
    }
    
    public void addInHistory(String u)
    {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(Calendar.getInstance().getTime());
                
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File("History"),true));
            
            pw.println(u + " - " + timeStamp);
            
            pw.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Oops! History File Not Found", "History", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isFirewallPassed()
    {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("Firewalls")));
            line = br.readLine();
            while(line!=null)
            {
                if(!line.isEmpty() && refg.url.urlTxt.getText().indexOf(line) >= 0)
                {
                     return false;
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Oops! Firewall File Not Found", "Firewalls", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }
}
