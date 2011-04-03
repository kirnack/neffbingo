
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Handles communication with clients,
 * so the Server can listen for more requests
 * 
 * @author Devin Doman
 */
public class BingoHandler
   implements Runnable
{
   /**
    * Keep a handle on the notifier, so other clients can be notified
    * of a bingo
    */
   private BingoNotifier mNotifier;
   /**
    * Handle on the socket connection
    */
   private Socket mSocket;
   /**
    * Handle on the input stream from the client
    */
   private BufferedReader mReadFromClient;
   /**
    * Handle on the output stream to the client
    */
   private DataOutputStream mOut;
   
   /**
    * Constructor
    *
    * @param pSocket
    * @throws Exception
    */
   public BingoHandler(Socket pSocket)
      throws Exception
   {
      mSocket = pSocket;
      mNotifier = BingoNotifier.getInstance();

      //Get a reference to the socket's input stream
      InputStream in = mSocket.getInputStream();
      mOut = new DataOutputStream(mSocket.getOutputStream());
      
      mReadFromClient = new BufferedReader(new InputStreamReader(in));
      
   }

   /**
    * Listens for the client to say they have a bingo
    */
   public void run()
   {
      //Register this handler with the notifier
      mNotifier.registerConnection(this);

      //Listen for a bingo from the client
      try
      {
         while (true)
         {
            listenForBingo();
         }
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }

   /**
    * Listens for a bingo from the client. Tells the notifier to tell
    * the other clients if there is.
    *
    * @throws Exception Error conditions are thrown
    */
   public void listenForBingo()
      throws Exception
   {
      //Get Bingo message from client
      String clientMessage = mReadFromClient.readLine();

      System.out.println("Client: " + clientMessage);

      //If the client has a bingo
      if (clientMessage.contains("Bingo!"))
      {
         //Pull out and send the player name
         String[] contents = clientMessage.split(" ");
         mNotifier.notifyConnections(contents[1]);
      }
   }

   /**
    * Tell the client that someone got a bingo. Used by the notifier.
    */
   public void tellClient(String pWinner)
   {
      try
      {
         mOut.writeBytes("Win " + pWinner + "\n");
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }
}
