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
//            String programClosingStatus = UserInterface.display();
//            System.out.println("Program closed with '" + programClosingStatus + "' status");
//            Log.trace("Program closed with '" + programClosingStatus + "' status");
            List<String> locations = new ArrayList<String>();
            locations.add("Romania"); locations.add("Brazil"); locations.add("Namibia"); locations.add("Mauritania"); locations.add("Italy");
            List<String> names1 = new ArrayList<String>();
            names1.add("Cristi; Purcea"); names1.add("Alex; McDonalds"); names1.add("Jan; Bogorsomething"); names1.add("Teo; Viking"); names1.add("Brobert; Strongest of them all");
            List<String> names2 = new ArrayList<String>();
            names2.add("Cristi Customer; Purcea"); names2.add("Alex Duh; da"); names2.add("Jan YEET; dar"); names2.add("Teo; noob"); names2.add("Brobert Bro; Strongest of them all");
            List<String> cars = new ArrayList<String>();
            cars.add("model name; brand; 2000"); cars.add("dacia; logan; 2012");

            Neo4JInteraction.sendQuery(Neo4jQueries.createDealership(locations));

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