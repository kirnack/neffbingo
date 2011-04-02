import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Creates a Bingo board containing
 * quotes often spoken by Brother Neff.
 *
 * @author tblyons, domand, har3000
 */
public class NeffBingo
   implements ActionListener
{
   QuoteGen mQuoteGen;
   JFrame mBoard;
   JPanel mPanel;
   JToggleButton[] tButton = new JToggleButton[25];
   BingoListener mClient;

   Socket mConnection;
   DataOutputStream mOutToServer;
   
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      NeffBingo game = new NeffBingo();
      game.play();
   }

   public void play()
   {

      //TODO: Establish a connection
      try
      {
         String address = "10.37.155.163";
         mConnection = new Socket(address, 1352);
         mOutToServer = new DataOutputStream(mConnection.getOutputStream());
      }
      catch (Exception e)
      {
      }
      //Give a handle on the connection to the thread

      mClient = new BingoListener(mConnection, this);
      // Create a new thread to establish a connection
      Thread thread = new Thread(mClient);
      // Start the thread.
      thread.start();
  
      

      mQuoteGen = new QuoteGen();
      mBoard = new JFrame();
      mPanel = new JPanel();
      mPanel.setLayout(new GridLayout(5,5));

      for (int i = 0; i < 25; i++)
      {
         String tempStr;
         if (i != 12)
         {
            tButton[i] = new JToggleButton("<html>" + mQuoteGen.getQuote() +
                                           "</html>");

            tButton[i].setForeground(Color.BLACK);
         }
         else
         {
            // this is the center square, and is generally a free space
            tButton[i] = new JToggleButton("<html>*Free Space*</html>", true);
            tButton[i].setForeground(Color.BLACK);
            tButton[i].setEnabled(false);
         }
         tButton[i].addActionListener(this);
         mPanel.add(tButton[i]);
      }

      mBoard.getContentPane().add(mPanel);
      mBoard.setSize(500,500);
      mBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mBoard.setVisible(true);
   }

   public boolean isWin()
   {
      if (tButton[0].isSelected() && tButton[6].isSelected() && tButton[12].isSelected() &&
          tButton[18].isSelected() && tButton[24].isSelected() ||
          (tButton[20].isSelected() && tButton[16].isSelected() && tButton[12].isSelected() &&
           tButton[8].isSelected() && tButton[4].isSelected()))
      {
         //finished diagonal
         return true;
      }

      for (int i = 0; i < 5; i++)
      {
         if (tButton[5*i].isSelected() && tButton[5*i + 1].isSelected() && tButton[5*i + 2].isSelected() &&
             tButton[5*i + 3].isSelected() && tButton[5*i + 4].isSelected())
         {
            //finished row
            return true;
         }
         else if (tButton[i].isSelected() && tButton[i+5].isSelected() && tButton[i+10].isSelected() &&
                  tButton[i+15].isSelected() && tButton[i+20].isSelected())
         {
            //finsihed column
            return true;
         }
      }

      return false;
   }

   public void actionPerformed(ActionEvent event)
   {
      JToggleButton button = (JToggleButton) event.getSource();

      if (isWin())
      {
         tButton[12].setBackground(Color.RED);
         tButton[12].setText("BINGO!");
         try
         {
            mOutToServer.writeBytes("Bingo!\n");
         }
         catch(Exception e)
         {
            System.out.println(e);
         }
      }
      else
      {
         tButton[12].setForeground(Color.BLACK);
         tButton[12].setText("<html>*Free Space*</html>");
      }
   }

   /**
    * Tell the bingo server that a bingo occurred
    */
   public void tellBingoServer()
   {

   }

   /**
    * Reset the bingo board after someone has won
    */
   public void resetBoard()
   {

   }
}
