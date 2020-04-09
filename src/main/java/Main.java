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
            // Run the actual program
        String programClosingStatus = UserInterface.display();
        System.out.println("Program closed with '" + programClosingStatus + "' status");

    }

    }
}