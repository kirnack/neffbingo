
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
   Socket mSocket;

   BufferedReader mReadFromClient;
   DataOutputStream mOut;


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
    * Starting place for this Runnable
    */
   public void run()
   {
      //register this handler with the notifier
      mNotifier.registerConnection(this);
      //TODO: Implement
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

  

   public void listenForBingo()
      throws Exception
   {
      //Get Bingo message from client
      String clientMessage = mReadFromClient.readLine();

      System.out.println("Client: " + clientMessage);

      if ("Bingo!".equals(clientMessage))
      {
         tellNotifier();
      }
   }
   /**
    * Tell the notifier that a bingo occurred
    */
   public void tellNotifier()
   {
      mNotifier.notifyConnections();
   }

   /**
    * Tell the client that someone got a bingo
    */
   public void tellClient()
   {
      try
      {
         mOut.writeBytes("Win\n");
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }
}
