
import java.util.ArrayList;
import java.util.Collection;

/**
 * A singleton that keeps track of the connections to all bingo boards.
 * When a bingo occurs all other boards can be notified.
 * 
 * @author Devin Doman
 */
public class BingoNotifier
{
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

   public void registerConnection(BingoHandler mConnection)
   {
      
   }

   public void notifyConnections()
   {
   
   }
}
