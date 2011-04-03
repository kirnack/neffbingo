
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The BingoServer listens for players to connect and spawns
 * a thread to handle each connection made.
 *
 * @author Devin Doman
 */
public class BingoServer
   extends JFrame
{
   /**
    * Used to show the IP address of the server
    */
   private JLabel mMessage;

   /**
    * Default constructor
    */
   public BingoServer()
   {
      super("Bingo Server");

      mMessage = new JLabel("   empty");
      add(mMessage);
      setSize(240, 100);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }

   /**
    * Starting place for the BingoServer
    *
    * @param argv Command line arguments
    * @throws Exception
    */
   public static void main(String argv[])
   {
      new BingoServer().run();
   }

   /**
    * Establishes socket connections with requesting clients
    */
   public void run()
   {
      try
      {
         //Set the port number
         int port = 1352;

         //Establish the listen socket.
         ServerSocket welcomeSocket = new ServerSocket(port);


         InetAddress addr = InetAddress.getLocalHost();
         String ip = addr.getHostAddress();

         mMessage.setText("   Server IP Address: " + ip);

         //Process service requests in an infinite loop
         while (true)
         {
            //Listen for a TCP connection request
            Socket connectionSocket = welcomeSocket.accept();
            // Construct an object to process the request.
            BingoHandler request = new BingoHandler(connectionSocket);
            // Create a new thread to process the request.
            Thread thread = new Thread(request);
            // Start the thread.
            thread.start();
         }
      }
      catch (Exception e)
      {
      }
   }
}
