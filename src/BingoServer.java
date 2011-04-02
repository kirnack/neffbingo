
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author Devin Doman
 */
public class BingoServer
{
   /**
    * Starting place for the WebServer
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


      //Process HTTP service requests in an infinite loop
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
