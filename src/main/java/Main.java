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
            DBInteraction.startConnection();

            // Run the actual program
            String programClosingStatus = UserInterface.display();
            System.out.println("Program closed with '" + programClosingStatus + "' status");
            Log.trace("Program closed with " + programClosingStatus + " status");
        }

        // close the connection to the database
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        DBInteraction.closeConnection();
    }
}