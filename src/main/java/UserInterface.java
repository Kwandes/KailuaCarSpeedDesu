/*
    Main interface and flow management class, handling interaction between different input, data generation and Database interaction classes
    Contains minimal amount of printouts and focuses on calling methods of other classes and using their results
    Static methods, class never is instanced
 */

public class UserInterface
{
    //region Attributes
    private static int screenNumber = 1;
    private static boolean programRunning = true;
    private static String quitStatus = "default";

    // formatting variables
    private static String decorationLines = "_________________________________________________________________";
    private static String decorationSymbol = " ";

    //endregion

    //region Screen changing and management

    /*
        handles switching between different screens(menus etc) and quiting the program
        works based on the 'screenNumber' variable. Each 'screen' assigns a value of the next thing to display to the 'screenNumber' var.
        mainMenu screen has a number 1 so if a given method is supposed to return to mainMenu, it will set the variable to 1
        quitting the program is done by setting the variable to 0
    */
    public static String display()
    {
        screenNumber = 5;
        while (programRunning)
        {
            // check for what screen to open next

            switch (screenNumber)
            {
                case 0:
                    quit();
                    break;
                case 1:

                    break;
                case 2:
                    displayCustomerInfo();
                    break;
                case 3:
                    displayContractInfo();
                    break;
                case 4:
                    displayCarInfo();
                    break;
                case 5:
                    Queries.registerCar();
                    //registerCustomer();
                    break;
                case 6:
                    registerContract();
                    break;
                case 7:
                    Queries.registerCar();
                    break;
                case 98:
                    mainMenu();
                default:
                    mainMenu();
                    break;
            }
        }
        return quitStatus;
    }


    // screen number 0
    // stops the main display loop and defines the status of the shutdown
    private static void quit()
    {
        programRunning = false;
        quitStatus = "normal shutdown";
    }

    // screen number 1
    // main screen users see after running the program
    // contains basic interactions with the system
    private static void mainMenu()
    {
        Queries.formattedHeader("Welcome to Kailua Car Rental");
        Queries.formattedPrint(1, "Display information about customers");
        Queries.formattedPrint(2, "Display information about contracts");
        Queries.formattedPrint(3, "Register a new Customer");
        Queries.formattedPrint(4, "Create a new Contract");
        Queries.formattedPrint(5, "Manage cars");
        Queries.formattedPrint(6, "Quit");

        // get input from user. The method contains input checking
        int input = ScannerReader.scannerInt(1, 7);

        // assign next screen depending on the user input
        switch (input)
        {
            case 1:
                screenNumber = 1;
                break;
            case 2:
                screenNumber = 2;
                break;
            case 3:
                screenNumber = 3;
                break;
            case 4:
                screenNumber = 4;
                break;
            case 5:
                screenNumber = 5;
                break;
            case 6:
                screenNumber = 6;
                break;
            case 7:
                quit();
                break;
        }
    }

    // screen number 2
    // displays various information about Customer(s)
    private static void displayCustomerInfo()
    {
        Queries.formattedHeader("Customer Information");
        Queries.formattedPrint(1, "List all customers");
        Queries.formattedPrint(2, "Search for a specific customer");
        int input = ScannerReader.scannerInt(1,2);
        switch (input)
        {
            case 1:
                System.out.println("Here would be the list of customers etc");
                break;
            case 2:
                System.out.println("Here would be search function for a specific customer");
                break;
        }
        quitOrReturnToMainMenu();
    }

    // screen number 3
    private static void displayContractInfo()
    {
        Queries.formattedHeader("Contract info");
        System.out.println("to be implemented!");
        quitOrReturnToMainMenu();
    }

    // screen number 4
    private static void displayCarInfo()
    {
        Queries.formattedHeader("Car info");
        System.out.println("to be implemented!");
        quitOrReturnToMainMenu();
    }

    // screen number 5
    private static void registerCustomer()
    {
        Queries.formattedHeader("Register Customer");
        System.out.println("to be implemented!");
        quitOrReturnToMainMenu();
    }


    // screen number 6
    private static void registerContract()
    {
        Queries.formattedHeader("Create a new Contract");
        System.out.println("to be implemented!");
        quitOrReturnToMainMenu();
    }

    // screen number 7
    private static void registerCar()
    {
        Queries.formattedHeader("Register a Car");
        System.out.println("to be implemented!");
        quitOrReturnToMainMenu();
    }

    //endregion

    //region Non-screen methods




    // ask user for input regarding next actions
    private static void quitOrReturnToMainMenu()
    {
        System.out.println("");
        System.out.println("what would you like to do next?");
        System.out.println("[1] Return to main menu");
        System.out.println("[2] Quit the program");
        int choice = ScannerReader.scannerInt(1, 2);
        switch (choice)
        {
            case 1:
                screenNumber = 1;
                break;
            case 2:
                screenNumber = 0;
                break;
        }
    }

    //endregion
}
