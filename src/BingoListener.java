
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Handles the connection from this client to the server
 * 
 * @author Devin
 */
public class BingoListener
   implements Runnable
{
   /**
    * Handle on the bingo board
    */
   private NeffBingo mBoard;
   /**
    * Handle on the connection between the client and server
    */
   private Socket mSocket;

   /**
    * Default Constructor
    *
    * @param pSocket The connection between the client and server
    * @param pBoard The handle on the bingo board
    */
   public BingoListener(Socket pSocket, NeffBingo pBoard)
   {
      mBoard = pBoard;
      mSocket = pSocket;
   }

   /**
    * Listens for messages from the server. When someone has won it announces
    * the winner to the user.
    * 
    */
   public void run()
   {
      String serverMessage;
      try
      {
         BufferedReader inFromServer =
            new BufferedReader(
            new InputStreamReader(mSocket.getInputStream()));

         while(true)
         {
            serverMessage = inFromServer.readLine();
            System.out.println("Server: " + serverMessage);

            //If the server indicates a win has occured
            if (serverMessage.contains("Win"))
            {
               //Pull out and send the player name
               String[] contents = serverMessage.split(" ");
               mBoard.announceWin(contents[1]);
            }
         }
      }
      catch (Exception e)
      {
      }
   }
}
