import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.ArrayList;

/**
 * <code>QuoteGen</code> reads the Quote file
 * and pulls out quotations to label the bingo
 * grid with.
 *
 * @author Devin Doman
 */
public class QuoteGen
{
   private Collection<String> mQuotes;

   /**
    * QuoteGen Constructor
    */
   public QuoteGen()
   {
      mQuotes = new ArrayList<String>();
      readQuotes("Quotes.txt");
      
      //TODO: implement resource folder, so that making jar will be easier
      //readQuotes("res/Quotes.txt");
   }

   /**
    * Retrieves the quotes to be generated from a file
    *
    * @param pFile The file location of the quotations
    */
   private void readQuotes(String pFile)
   {
      BufferedReader in = null;
      try
      {
         in = new BufferedReader(new FileReader(pFile));
         //in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(pFile)));

         //Read the data from the file and place in mQuotes
         while (in.ready())
         {
            String line = in.readLine().trim();

            //do not put in a blank line
            if (!line.isEmpty())
            {
               mQuotes.add(line);
            }
         }
      }
      catch (FileNotFoundException e)
      {
         System.out.println("File not found.");
      }
      catch (IOException e)
      {
         System.out.println("Error reading data.");
      }
   }

   /**
    * Pull out a quote from the collection
    *
    * @return a quote from the collection
    *         null if the collection is empty
    */
   public String getQuote()
   {
      String rQuote = null;
      int rand;
      ArrayList<String> quotes;

      if (!mQuotes.isEmpty())
      {
         quotes = (ArrayList<String>) mQuotes;
         //pick a random number to get a quote with
         rand = (int) (Math.random() * quotes.size());

         rQuote = quotes.get(rand);

         //do not use this quote again
         mQuotes.remove(rQuote);
      }
      return rQuote;
   }
}
