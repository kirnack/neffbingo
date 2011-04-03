import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Creates a Bingo board containing
 * quotes often spoken by Brother Neff.
 *
 * @author tblyons, har3000, Devin Doman
 */
public class NeffBingo
   implements ActionListener
{
   /**
    * Used to get the quotes for the board
    */
   private QuoteGen mQuoteGen;
   /**
    * The frame for the board
    */
   private JFrame mBoard;
   /**
    * The panel containing the board
    */
   private JPanel mPanel;
   /**
    * The buttons containing each quote
    */
   private JToggleButton[] tButton = new JToggleButton[25];
   /**
    * Listens for messages from the server
    */
   private BingoListener mClient;
   /**
    * The socket connection between the client and server
    */
   private Socket mConnection;
   /**
    * Handle on the output stream to the server
    */
   private DataOutputStream mOutToServer;
   /**
    * The name of the player
    */
   private String mPlayerName;
   
   /**
    * Starting point for the bingo client
    *
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      NeffBingo game = new NeffBingo();
      game.play();
   }

   /**
    * Launch the NeffBingo game
    */
   public void play()
   {
      try
      {
         String address = getServerAddress();
         mConnection = new Socket(address, 1352);
         mOutToServer = new DataOutputStream(mConnection.getOutputStream());
      }
      catch (Exception e)
      {
      }

      getPlayerName();

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

      //Extract random quotes and place them in the board
      for (int i = 0; i < 25; i++)
      {
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

   /**
    * Determine whether there is a bingo condition
    *
    * @return True if there is a bingo
    */
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

   /**
    * The action that occurs when the button is pressed.
    * Checks for a bingo for each selection
    *
    * @param event The action event
    */
   public void actionPerformed(ActionEvent event)
   {
      JToggleButton button = (JToggleButton) event.getSource();

      //If there is a bingo
      if (isWin())
      {
         //Indicate a bingo on the board and tell the server
         tButton[12].setBackground(Color.RED);
         tButton[12].setText("BINGO!");
         tellBingoServer();
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
      try
      {
         mOutToServer.writeBytes("Bingo! " + mPlayerName + "\n");
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }

   /**
    * Used when the server needs to indicate who the winner is
    *
    * @param pPlayer The player who won the bingo round
    */
   public void announceWin(String pPlayer)
   {
      displayBingo(pPlayer);
      resetBoard();
   }

   /**
    * Reset the bingo board after someone has won
    * 
    */
   public void resetBoard()
   {
      mQuoteGen = new QuoteGen();

      //Pull out random quotes and place them in the board
      for (int i = 0; i < 25; i++)
      {
         if (i != 12)
         {
            tButton[i].setText("<html>" + mQuoteGen.getQuote() +
                                           "</html>");

            tButton[i].setSelected(false);
         }
         else
         {
            // this is the center square, and is generally a free space
            tButton[i].setText("<html>*Free Space*</html>");
         }
      }
   }

   /**
    * Display the winner to the user
    *
    * @param pPlayer The winner of the bingo round
    */
   public void displayBingo(String pPlayer)
   {
      JOptionPane.showMessageDialog(null, "Bingo! " + pPlayer + " wins!");
   }

   /**
    * Gets from the user the IP address of the bingo server
    *
    * @return The string entered by the user
    */
   public String getServerAddress()
   {
      String address = "";
      address = JOptionPane.showInputDialog(null, "Enter IP address of server: ");
      return address;
   }

   /**
    * Gets from the user their player name for this game
    * 
    */
   public void getPlayerName()
   {
      mPlayerName =  JOptionPane.showInputDialog(null, "Player name: ");
   }
}
