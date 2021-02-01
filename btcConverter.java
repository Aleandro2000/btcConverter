import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpResponse.*;

public class btcConverter extends JFrame
{   
    //window's components
    static JLabel text_btc;
    static JTextField btc;
    static JLabel text_usd;
    static JTextField usd;
    static JButton convert;
    static JPanel panel;
    static double value;

    public static String getContent(String uri) throws Exception
    {
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request=HttpRequest.newBuilder()
              .uri(URI.create(uri))
              .build();
    
        HttpResponse<String> response=client.send(request, BodyHandlers.ofString());
    
        return response.body();
    }

    public static void getValue()
    {
        //url connection to Cryptocurrency's API
        try
        {
            value=Double.parseDouble(getContent("https://blockchain.info/tobtc?currency=USD&value=1"));
        }
        catch(Exception e)
        {
            showMessageDialog(null, "ERROR! TNO API AVAILABLE!");
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable(){
            public void run()
            {
                //window's attribute
                JFrame frame=new JFrame("BTC to USD converter");
                frame.setSize(300,400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                //window's components settings
                text_btc=new JLabel("BTC amout:");
                btc=new JTextField(25);
                text_usd=new JLabel("USD amout:");
                usd=new JTextField(25);
                convert=new JButton("CONVERT");
                panel=new JPanel();
                usd.setEditable(false);

                //adding pannels's components
                panel.add(text_btc);
                panel.add(btc);
                panel.add(text_usd);
                panel.add(usd);
                panel.add(convert);

                //define pannels's component's events
                convert.setActionCommand("CONVERT");
                convert.addActionListener(new al());

                //adding window's pannel
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static boolean isNumeric(String strNum)
    {
        if (strNum==null)
            return false;
        try
        {
            double d=Double.parseDouble(strNum);
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    private static class al implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            switch(e.getActionCommand())
            {
                case "CONVERT":
                    getValue();
                    if(isNumeric(btc.getText()))
                        usd.setText(String.valueOf(Double.parseDouble(btc.getText())/value));
                    else
                        showMessageDialog(null, "ERROR! THIS IS NOT NUMERICAL VALUE!");
                    break;
                default:
                    break;
            }
        }
    }
}