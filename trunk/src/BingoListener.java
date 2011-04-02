
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
   Socket mSocket;

   BingoListener(Socket pSocket, NeffBingo pBoard)
   {
      mBoard = pBoard;
      mSocket = pSocket;
   }
   String mAddress;
   String mMessage;
   String mResponse;

   public void run()
   {
      String address = "10.37.155.163";
      String sentence;
      String modifiedSentence;
      BufferedReader inFromUser;

      while (true)
      {
         try
         {
            System.out.println("Enter a string: ");
            inFromUser =
               new BufferedReader(new InputStreamReader(System.in));
            sentence = inFromUser.readLine();


            Socket clientSocket = new Socket(address, 1352);

            DataOutputStream outToServer =
               new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader inFromServer =
               new BufferedReader(
               new InputStreamReader(clientSocket.getInputStream()));

            outToServer.writeBytes(sentence + '\n');

            modifiedSentence = inFromServer.readLine();

            System.out.println("FROM SERVER: " + modifiedSentence);


    
         }
         catch (Exception e)
         {
            System.out.println(e);
         }
      }
   }
}
