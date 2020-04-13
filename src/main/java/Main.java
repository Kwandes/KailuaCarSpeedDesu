import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        // Load the config and set all the necessary attributes in different classes
        if( !Setup.setupProgram())
        {
            System.out.println("Program has failed to configure properly. Closing");
        }
        else
        {
            // Start the connection to the database
            DBInteraction.openConnection();
            Neo4JInteraction.openSession();
            try
            {
                Thread.sleep(500);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            // Run the actual program
            String programClosingStatus = UserInterface.display();
            System.out.println("Program closed with '" + programClosingStatus + "' status");
            Log.trace("Program closed with \"" + programClosingStatus + "\" status");
        }

        // close the connection to the database but first give 5 seconds for existing queries to finish
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        DBInteraction.closeConnection();
        Neo4JInteraction.closeSession();
    }
}