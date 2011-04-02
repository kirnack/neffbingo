
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Devin Doman
 */
public class BingoServer
{
   /**
    * Starting place for the BingoServer
    *
    * @param argv Command line arguments
    * @throws Exception
    */
   public static void main(String argv[])
      throws Exception
   {
      //Set the port number
      int port = 1352;

      //Establish the listen socket.
      ServerSocket welcomeSocket = new ServerSocket(port);

      try
      {
         InetAddress addr = InetAddress.getLocalHost();
         String ip = addr.getHostAddress();

         System.out.println("Address: " + ip);
      }
      catch(Exception e)
      {

      }

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
}
