
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

   BingoListener(NeffBingo pBoard)
   {
      mBoard = pBoard;
   }

   public void run()
   {
      
   }
}
