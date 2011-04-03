
import java.util.ArrayList;
import java.util.Collection;

/**
 * A singleton that keeps track of the connections to all bingo boards.
 * When a bingo occurs all other boards can be notified. Uses the
 * Observer design pattern.
 * 
 * @author Devin Doman
 */
public class BingoNotifier
{
   /**
    * The collection of handles on the clients playing
    */
   private Collection<BingoHandler> mConnections;
   /**
    * Variable to hold a singleton of BingoNotifier
    */
   private static final BingoNotifier cInstance;
   /**
    * Initialize a BingoNotifier instance
    */
   static
   {
      cInstance = new BingoNotifier();
   }

   /**
    * Constructor is private to implement a singleton of BingoNotifier
    * 
    */
   private BingoNotifier()
   {
      mConnections = new ArrayList<BingoHandler>();
   }

   /**
    * Returns the BingoNotifier instance
    *
    * @return The singleton instance of BingoNotifier
    */
   public static BingoNotifier getInstance()
   {
      return cInstance;
   }

   /**
    * Allows a client to register itself with the notifier so that
    * they can be notified of a bingo.
    *
    * @param mConnection
    */
   public void registerConnection(BingoHandler mConnection)
   {
      mConnections.add(mConnection);
   }

   /**
    * Notifies all listeners that a bingo has occurred and who the winner is.
    *
    * @param pWinner The winner of the bingo round
    */
   public void notifyConnections(String pWinner)
   {
      for (BingoHandler client : mConnections)
      {
         client.tellClient(pWinner);
      }
   }
}
