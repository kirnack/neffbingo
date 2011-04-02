
import java.io.BufferedReader;
import java.io.DataOutputStream;
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
   NeffBingo mBoard;
   Socket mSocket;

   BingoListener(Socket pSocket, NeffBingo pBoard)
   {
      mBoard = pBoard;
      mSocket = pSocket;
   }

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
         }

      }
      catch (Exception e)
      {
      }
   }
}
