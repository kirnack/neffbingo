
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
         
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }

  

   public void listenForBingo()
   {
      
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
