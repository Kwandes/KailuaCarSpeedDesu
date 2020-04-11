import java.text.SimpleDateFormat;
import java.util.Date;

public class Queries {

    private static final String art = "☭";
    private static final int headerLinesCount = 100;
    private static final String headerLines = "*";

    //region System.out.prints

    //region menu methods

    //region manage contracts
    public static void manageContracts()
    {


        formattedHeader("Manage Contracts");

        formattedPrint("What would you like to do?");
        formattedPrint(1,"See all open contracts");
        formattedPrint(2,"See all closed contracts");
        formattedPrint(3,"Create a new contract");
        formattedPrint(4,"Generate contract(s)");
        formattedPrint(5,"Return to main menu");
        int selection = ScannerReader.scannerInt(1,5);
        switch(selection)
        {
            case 1: //see all open contracts
                formattedHeader("See all open contracts");
                formattedPrint("Getting the list of all open contracts");
                slowScroll(1000, "... ");
                break;
            case 2: //see all closed contracts
                formattedHeader("See all closed contracts");
                formattedPrint("Getting the list of all closed contracts");
                slowScroll(1000, "... ");
                break;
            case 3: //Create new contract
                formattedHeader("Create a new contract");
                break;
            case 4: //Generate contract(s)
                formattedHeader("Generate contract(s)");
                formattedPrint("How many contracts would you like to generate?");
                int contractsToGenerate = ScannerReader.scannerInt();
                //generateContractsCode here ________________________
                formattedPrint(contractsToGenerate + " contracts generated.");
                break;
            case 5: //main menu JAN

                break;
        }

        //int selection = ScannerReader.scannerInt(1,5);
    }


    //endregion

    //region menu manageCar
    public static void registerCar()
    {
        formattedHeader("Register a new car");
        formattedPrint("Please type the name of the new car");
        String model_TEMP = ScannerReader.scannerWords();
        formattedPrint("Please type the brand of the new car");
        String brand_TEMP = ScannerReader.scannerWords();
        formattedPrint("Please type the color of the new car"); //default is white
        String color_TEMP = ScannerReader.scannerWords();
        formattedPrint("Please type the plate number of the new car");
        String plate_number_TEMP = ScannerReader.scannerAll();
        formattedPrint("Generating registry date of car based on current time");
        slowScroll(1000, "... ");//the date we acquire it
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reg_date = sdf.format(new Date());
        System.out.println("Registry date is: " + reg_date);
        formattedPrint("Please type the current kilometers on the dial of the car"); //default is 0
        int km_driven_TEMP = ScannerReader.scannerInt(0, 200000);
        formattedPrint("Please type the amount of seats the car has"); //default is 4
        int seats_TEMP = ScannerReader.scannerInt(2,9);
        formattedPrint("Please type the slogan or catchphrase of the new car, if it has one"); //an example is "I am Speed" for 'Lightning McQueen'
        String other_specifications_TEMP = ScannerReader.scannerWords();
        formattedPrint("Please type the fuel type of the new car"); //default is gas
        String fuel_type_TEMP = ScannerReader.scannerWords();
        formattedPrint("Lastly, please type the price per day of lease of the new car");
        int price_per_day_TEMP = ScannerReader.scannerInt(1, 9999); //DB type is INT. I made a string because idk how you wanna use it
        //create query
        String carInfo = "'" + model_TEMP + "', '" + brand_TEMP + "', '" +  color_TEMP + "', '" + plate_number_TEMP + "', '" + reg_date +
                         "', 'TRUE', '" + km_driven_TEMP + "', '" + seats_TEMP + "', '" + other_specifications_TEMP +  "', '" + fuel_type_TEMP +
                         "', '" + price_per_day_TEMP + "'";
        //send query?
    }
    public static void deleteCar()
    {
        formattedHeader("Delete a car");
        //print list of cars here with their IDs
        formattedPrint("Please type the car ID to delete");
        int selection = ScannerReader.scannerInt();
        formattedPrint("You have selected car ID " + selection);
        formattedPrint("Are you sure you wish to delete this car from the database?");
        slowScroll(300, "This can't be undone "); //amazing
        System.out.println();
        formattedPrint("[1] Yes.");
        formattedPrint("[2] No.");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int choice = ScannerReader.scannerInt(1,2);
        switch (choice)
        {
            case 1:
                boolean deleted = false;
                //create query to delete the car
                //send the query, if query is successful then deleted = true.
                if (deleted=true)
                {
                    formattedPrint("The car you selected has been deleted");
                } else {
                    formattedPrint("There was an error deleting the desired car");
                }
                break;
            case 2:
                //abort mission
                slowScroll("Good choice.");
                formattedPrint("Delete operation has been aborted");
                //return to main menu, somehow.
                break;
        }

    }
    public static void sortCarsInList()
    {
        formattedHeader("List of all cars");
        formattedPrint("How would you like the cars to be sorted?");
        formattedPrint(1,"By model name");
        formattedPrint(2,"By brand name");
        formattedPrint(3,"By number of seats");
        formattedPrint(4,"By availability");
        formattedPrint(5,"Return to main Menu");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int choice = ScannerReader.scannerInt(1,5);
        switch(choice)
        {
            case 1: //sort Model Name

                break;
            case 2: //sort Brand Name

                break;
            case 3: //sort Number of seats

                break;
            case 4: //sort Availability

                break;
            case 5: //main menu

                break;
        }
    }
    public static void updateCar()
    {
        //uuurgh
        formattedHeader("Change car details");
        formattedPrint("Please select a car");
        //print list of all cars
        //'choice' is the car_ID of the chose car
        int choice = ScannerReader.scannerInt();
        formattedPrint("What would you like to change?");
        formattedPrint(1,"Color");
        formattedPrint(2,"Contract ID of a car");
        formattedPrint(3,"Daily Price of a car");
        formattedPrint(4, "Cancel");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int editChoice = ScannerReader.scannerInt(1,4);
        String text = "";
        if (editChoice == 1) {
            text = "color";
        } else if (editChoice ==2){
            text = "contract ID";
        } else if (editChoice ==3){
            text = "daily price";
        }
        if (editChoice!=4)
        {
            formattedHeader("Change a car's " + text);
            formattedPrint("Please type the new " + text + " of the car");
            if (editChoice == 1)
            {
                String carEdit = ScannerReader.scannerWords();

                //int price_per_day = Integer.parseInt(carEdit);
            } else {
                int carEdit = ScannerReader.scannerInt();
            }
            boolean success = false;
            //query here to set new car colour
            //if query was successful then success = true;
            if (success)
            {
                formattedPrint("The car's " + text + " has been updated");
            } else
            {
                formattedPrint("Something went wrong updating the car's " + text);
            }
        }
    }

    public static void manageCar()
    {
        formattedHeader("Manage Cars");
        formattedPrint("What would you like to do?");
        formattedPrint(1,"See a list of all cars");
        formattedPrint(2,"Register a new car");
        formattedPrint(3,"Change some details of a car");
        formattedPrint(4,"Delete a car from the database");
        formattedPrint(5,"Return to main menu");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int selection = ScannerReader.scannerInt(1,5);
        switch(selection)
        {
            case 1: //See a sorted list of cars
                sortCarsInList();
                break;
            case 2: //Register a new car
                registerCar();
                break;
            case 3: //Change details of a car
                updateCar();
                break;
            case 4: //Delete a car
                deleteCar();
                break;
            case 5: //main menu
                //JAN
                break;
        }
    }
    //endregion
    //endregion

    //region System.out.print Methods
    public static void formattedPrint(String text)
    {

        System.out.printf("%-4s%-95s%-4s%n", art, text, art);
    }
    // prints a menu option in a '[num] option text' format
    public static void formattedPrint(int num, String text)
    {
        System.out.printf("%-4s [" + num + "] %-90s%-4s%n", art, text, art);
        //System.out.println("[" + num + "] " + text);
    }
    public static void slowScroll(int delay, String text){
        for(int i = 0; i < text.length(); i++) {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < delay) {

            }
            System.out.print(text.charAt(i));
        }
        System.out.println();
    }
    public static void slowScroll(String text){
        for(int i = 0; i < text.length(); i++) {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 100)
            {

            }
            System.out.print(text.charAt(i));
        }
        System.out.println();
    }

    public static void formattedHeader(String text)
    {
        for(int i=0; i<10;i++)
        {
            System.out.println();
        }
        printLines();
        if (text.length()%2 != 0)
        {
            text += " ";
        }
        int numbers = (headerLinesCount-text.length())/2;
        System.out.printf("%-" + numbers + "s%s%" + numbers + "s%n", art, text, art);
        printLines();
        System.out.println();
        System.out.println();
    }

    private static void printLines()
    {
        for (int i=0; i<=headerLinesCount; i++)
        {
            System.out.print(headerLines);
        }
        System.out.println();
    }
    //endregion
    //endregion

    //region queries
    public static void addPerson(String customerInfo)
    {
        String DBCustomerInfo = "INSERT INTO cunstomer( " +
                "first_name, last_name, cpr, zip, city, address, phone_number, email, drivers_licence_number, driver_since_date " +
                ") VALUES ( " +
                customerInfo + " );";
        int rows = DBInteraction.updateData( DBCustomerInfo ); //For verification that the data was updated
        System.out.println( "Rows effected : " + rows);
    }

    public static void addCar(String carInfo)
    {
        String DBCarInfo = "INSERT INTO Car( " +
                "model, brand, color, plate_number, date_registered, km_driven, available, seats, other_specifications, fuel_type, price_pr_day " +
                ") VALUES ( " +
                carInfo + " );";
        int rows = DBInteraction.updateData( DBCarInfo ); //For verification that the data was updated
        System.out.println( "Rows effected : " + rows);
    }


    //Contact info should be formattet ( Date startDate, Date endDate, int costumerId, int salesmanId, int carId, int maxKm, int startKm ) in a db query string
    public static void addContract( String contractInfo ) {
        String DBContractInfo = "INSERT INTO Contract( " +
                "date_signed, end_date, costumer_id, salesman_id, car_id, max_km, start_km, value " +
                ") VALUES ( " +
                contractInfo + " );";
        //Remember to set the choosen car's contract_id to match this contracts primary key
        int rows = DBInteraction.updateData(DBContractInfo);
        System.out.println( "Rows effected : " + rows);   //For verification that the data was updated
    }
    //endregion
}

