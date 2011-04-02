
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

      //register this handler with the notifier
      mNotifier.registerConnection(this);
   }

   /**
    * Starting place for this Runnable
    */
   public void run()
   {
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
      //Get a reference to the socket's input stream
      InputStream in = mSocket.getInputStream();
      DataOutputStream out = new DataOutputStream(mSocket.getOutputStream());

      BufferedReader reader = new BufferedReader(new InputStreamReader(in));


      //Get messages
      String clientMessage = reader.readLine();

      System.out.println("Client: " + clientMessage);

      out.writeBytes(clientMessage + ", test from server\n");

   }
   /**
    * Tell the notifier that a bingo occurred
    */
   public void tellNotifier()
   {

   }

   /**
    * Tell the client that someone got a bingo
    */
   public void tellClient()
   {

   }
}
