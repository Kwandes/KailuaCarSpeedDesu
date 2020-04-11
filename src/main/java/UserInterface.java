/*
    Main interface and flow management class, handling interaction between different input, data generation and Database interaction classes
    Contains minimal amount of printouts and focuses on calling methods of other classes and using their results
    Static methods, class never is instanced
 */

public class UserInterface
{
    //region Attributes
    private static int screenNumber = 98;
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
        //screenNumber=98;
        while (programRunning)
        {
            // check for what screen to open next

            switch (screenNumber)
            {
                case 0: //quit
                    quit();
                    break;
                case 1: //Manage Costumers
                    //mainMenu();
                    break;
                case 2: //Mange Contracts
                    Queries.manageContracts();
                    quitOrReturnToMainMenu();
                    break;
                case 3: //Manage Employees
                    displayContractInfo();
                    break;
                case 4: //Manage Cars
                    Queries.manageCar();
                    quitOrReturnToMainMenu();
                    break;
                case 98:
                    mainMenu();
                default:
                    //mainMenu();
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
        Queries.formattedPrint(1, "Manage Costumers");
        Queries.formattedPrint(2, "Manage Contracts");
        Queries.formattedPrint(3, "Manage Employees");
        Queries.formattedPrint(4, "Manage cars");
        Queries.formattedPrint(5, "Quit");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        // get input from user. The method contains input checking
        int input = ScannerReader.scannerInt(1, 5);

        // assign next screen depending on the user input
        switch (input)
        {
            case 1: //manage
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
                screenNumber = 0;
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
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
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

    // screen number 5 (manage cars)




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
    public static void quitOrReturnToMainMenu()
    {
        System.out.println("");
        System.out.println("what would you like to do next?");
        System.out.println("[1] Return to main menu");
        System.out.println("[2] Quit the program");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int choice = ScannerReader.scannerInt(1, 2);
        switch (choice)
        {
            case 1:
                screenNumber = 98;
                break;
            case 2:
                screenNumber = 0;
                break;
        }
    }

    //endregion
}
