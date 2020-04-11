import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
        formattedPrint(2,"Create a new contract");
        formattedPrint(3,"Generate contract(s)");
        formattedPrint(4,"Return to main menu");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int selection = ScannerReader.scannerInt(1,5);

        switch(selection)
        {
            case 1: //see all open contracts
                formattedHeader("See all open contracts");
                formattedPrint("Getting the list of all open contracts");
                slowScroll(1000, "... ");
                break;
            case 2: //Create new contract
                formattedHeader("Create a new contract");
                String createContractInfo = createContract();
                //send query
                addContract( createContractInfo );
                break;
            case 3: //Generate contract(s)
                formattedHeader("Generate contract(s)");
                formattedPrint("How many contracts would you like to generate?");
                int contractsToGenerate = ScannerReader.scannerInt();
                //generateContractsCode here ________________________
                for (int i = 0; i < contractsToGenerate; i++)
                {
                    String contractInfo = genContract();
                }
                //end gen
                formattedPrint(contractsToGenerate + " contracts generated.");
                break;
            case 4: //main menu JAN
                break;
        }

        //int selection = ScannerReader.scannerInt(1,5);
    }

    private static String createContract()   //HAS NOT BEEN TESTED (Failiure to work with DB)
    {
        int salesmanId = -1;            //
        int carId = -1;                 //
        int start_km = -1;              //
        int totalPrice = -1;            //
        int pricePrDay = -1;            //
        int customerId = -1;            //
        int maxKm = -1;                 //
        String contractEndDate = "";    //
        String signedDate = "";         //
        String contractStartDate = "";  //

        Random rand = new Random();
        SimpleDateFormat sdtf = new SimpleDateFormat( "yyyy-MM-dd HH:mm");

        //PRINT and select salesman
        try
        {
            String salesmanQuery = "SELECT * FROM Salesman;";
            ResultSet salesmen = DBInteraction.getData( salesmanQuery );
            //Print info on all salesmen so user can select salesman for contract
            formattedHeader("Select a salesman");
            salesmen.absolute(0);
            while (salesmen.next())
            {
                System.out.printf("ID : %-6s | Name : %-30s | Cpr nr : %-15s ",
                        salesmen.getString("salesman_id"),
                        salesmen.getString("first_name") + " " + salesmen.getString("last_name"),
                        salesmen.getString("cpr"));
            }

            //Select salesman_id
            //moves to the first row in the salesman list so i know the smallest id in the DB
            salesmen.absolute(0);
            int firstRowId = Integer.parseInt(salesmen.getString("salesman_id"));
            //moves to the last row in the salesman list so i know the largest id in the DB
            salesmen.last();
            int lastRowId = Integer.parseInt(salesmen.getString("salesman_id"));
            System.out.println();
            Queries.printLines();
            System.out.print("\tSelect from " + firstRowId + " - " + lastRowId + " : ");
            salesmanId = ScannerReader.scannerInt( firstRowId, lastRowId);                   //selects an Salesman_id that is in the DB!
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //PRINT and select a customer or CREATE customer
        formattedPrint("Do you wish to select a customer or register a new one? ");
        System.out.println();
        formattedPrint("[1] select from existing");
        formattedPrint("[2] register a new one");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int choice = ScannerReader.scannerInt(1,2);
        if (choice==1)
        {
            try
            {
                String customerQuery = "SELECT * FROM Customers;";
                ResultSet customers = DBInteraction.getData( customerQuery );
                //Print info on all customer so user can select customer for contract
                formattedHeader("Select a customer");
                customers.absolute(0);
                while (customers.next())
                {
                    System.out.printf("ID : %-6s | Name : %-30s | Cpr nr : %-15s ",
                            customers.getString("customer_id"),
                            customers.getString("first_name") + " " + customers.getString("last_name"),
                            customers.getString("cpr"));
                }

                //Select customer_id
                //moves to the first row in the customer list so i know the smallest id in the DB
                customers.absolute(0);
                int firstRowId = Integer.parseInt(customers.getString("customer_id"));
                //moves to the last row in the customer list so i know the largest id in the DB
                customers.last();
                int lastRowId = Integer.parseInt(customers.getString("customer_id"));
                System.out.println();
                Queries.printLines();
                System.out.print("\tSelect from " + firstRowId + " - " + lastRowId + " : ");
                customerId = ScannerReader.scannerInt( firstRowId, lastRowId);                   //selects an customer_id that is in the DB!
            }
            catch (SQLException | NullPointerException e) { e.printStackTrace(); }
        }
        else
        {
            //fuck that shit
            System.out.println("Register a new one your self man!");   //DO WE WANT TO MAKE THE REGISTER CUSTOMER METHOD?!?!?!

            //Auto gens a customer for you
            GenPerson genPerson = new GenPerson();
            String personInfo = genPerson.returnCustomer();
            addPerson( personInfo );
            String personQuery = "SELECT * FROM customer;";
            //get id of the customer that was just created
            try{
                ResultSet customer = DBInteraction.getData( personQuery );
                customer.last();
                customerId = Integer.parseInt( customer.getString("customer_id") );
            }
            catch (SQLException | NullPointerException e) { e.printStackTrace(); }
        }

        //PRINT and select car
        try
        {
            String carQuery = "SELECT * FROM Car " +
                    "WHERE available = 'TRUE';";
            ResultSet cars = DBInteraction.getData( carQuery );
            //Print info on all salesmen so user can select salesman for contract
            formattedHeader("Select a car");
            cars.absolute(0);
            while (cars.next())
            {
                System.out.printf("ID : %-6s | model : %-25s | brand : %-15s | Price pr Day : %-10s ",
                        cars.getString("car_id"),
                        cars.getString("model"),
                        cars.getString("brand"),
                        cars.getString("price_per_day"));
            }

            //Select car_id
            //moves to the first row in the cars list so i know the smallest id in the DB
            cars.absolute(0);
            int firstRowId = Integer.parseInt(cars.getString("car_id"));
            //moves to the last row in the cars list so i know the largest id in the DB
            cars.last();
            int lastRowId = Integer.parseInt(cars.getString("car_id"));
            System.out.println();
            Queries.printLines();
            System.out.print("\tSelect from " + firstRowId + " - " + lastRowId + " : ");
            carId = ScannerReader.scannerInt( firstRowId, lastRowId);                   //selects an Salesman_id that is in the system!
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Date signed
        signedDate = sdtf.format(new Date());

        //get start date
        Date startDate = ScannerReader.scannerDate( new Date() );
        contractStartDate = sdtf.format( startDate );

        //get end date (after start of contract date
        Date endDate = ScannerReader.scannerDate( startDate );
        contractEndDate = sdtf.format( endDate );

        //get max_km
        formattedPrint("What should max km limit be? ");
        Queries.printLines();
        System.out.print("\tSelect from 0 - 10.000 : ");
        maxKm = ScannerReader.scannerInt(0, 10000);

        //get start_km
        try
        {
            String carQuery = "SELECT * FROM Car " +
                    "WHERE car_id = '" + carId + "';";
            ResultSet cars = DBInteraction.getData( carQuery );
            start_km = Integer.parseInt( cars.getString("km_driven") );
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //get total price of rental
        //price pr day from selected car
        pricePrDay = -1;
        try
        {
            String carQuery = "SELECT * FROM Car " +
                    "WHERE car_id = '" + carId + "';";
            ResultSet cars = DBInteraction.getData( carQuery );
            pricePrDay = Integer.parseInt( cars.getString("price_pr_day") );
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //get amount of days from the rental start and end dates
        int daysOfRental = GenDate.daysBetween( startDate, endDate );
        totalPrice = pricePrDay * daysOfRental;

        //Create string for updateQuery
        return "'" + signedDate + "', '" + contractEndDate + "', '" + customerId +
                "', '" + salesmanId + "', '" + carId + "', '" + maxKm + "', '" + start_km +  "', '" + totalPrice +
                "', '" + contractStartDate + "'";

    }

    private static String genContract()  //HAS NOT BEEN TESTED (Failiure to work with DB
    {
        int salesmanId = -1;            //
        int carId = -1;                 //
        int start_km = -1;              //
        int totalPrice = -1;            //
        int pricePrDay = -1;            //
        int customerId = -1;            //
        int maxKm = -1;                 //
        String contractEndDate = "";    //
        String signedDate = "";         //
        String contractStartDate = "";  //

        Random rand = new Random();
        SimpleDateFormat sdtf = new SimpleDateFormat( "yyyy-MM-dd HH:mm");

        //Get random salesman_id from DB
        try {
            String salesmanQuery = "SELECT * FROM Salesman;";
            ResultSet salesmen = DBInteraction.getData(salesmanQuery);
            //Print info on all salesmen so user can select salesman for contract

            //moves to the first row in the salesman list so i know the smallest id in the DB
            salesmen.absolute(0);
            int firstRowId = Integer.parseInt(salesmen.getString("salesman_id"));
            //moves to the last row in the salesman list so i know the largest id in the DB
            salesmen.last();
            int lastRowId = Integer.parseInt(salesmen.getString("salesman_id"));

            //gets a random nr from salesman id's
            salesmanId = firstRowId + rand.nextInt( lastRowId - firstRowId );
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Get random car_id from DB
        try
        {
            String carQuery = "SELECT * FROM Car " +
                    "WHERE available = 'TRUE';";
            ResultSet cars = DBInteraction.getData( carQuery );
            //Print info on all salesmen so user can select salesman for contract
            cars.absolute(0);

            //Select car_id
            //moves to the first row in the cars list so i know the smallest id in the DB
            cars.absolute(0);
            int firstRowId = Integer.parseInt(cars.getString("car_id"));
            //moves to the last row in the cars list so i know the largest id in the DB
            cars.last();
            int lastRowId = Integer.parseInt(cars.getString("car_id"));

            //gets a random nr from car id's
            carId = firstRowId + rand.nextInt( lastRowId - firstRowId );

        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Get start km from selected car
        try
        {
            String carQuery = "SELECT * FROM Car " +
                    "WHERE car_id = '" + carId + "';";
            ResultSet cars = DBInteraction.getData( carQuery );
            start_km = Integer.parseInt( cars.getString("km_driven") );
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Get price from selected car
        try
        {
            String carQuery = "SELECT * FROM Car " +
                    "WHERE car_id = '" + carId + "';";
            ResultSet cars = DBInteraction.getData( carQuery );
            pricePrDay = Integer.parseInt( cars.getString("price_pr_day") );
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Date signed
        signedDate = sdtf.format(new Date());

        //Get get random Customer id from DB
        try
        {
            String customerQuery = "SELECT * FROM Customers;";
            ResultSet customers = DBInteraction.getData( customerQuery );
            //Print info on all customer so user can select customer for contract

            //moves to the first row in the customer list so i know the smallest id in the DB
            customers.absolute(0);
            int firstRowId = Integer.parseInt(customers.getString("customer_id"));
            //moves to the last row in the customer list so i know the largest id in the DB
            customers.last();
            int lastRowId = Integer.parseInt(customers.getString("customer_id"));
            customerId = firstRowId + rand.nextInt( lastRowId - firstRowId );                   //selects a random customer_id that is in the DB!
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Get contract start date
        Date rentalStart = GenDate.genRentalDate();
        contractStartDate = sdtf.format( rentalStart );

        //Get contract end date
        Date rentalEnd = GenDate.genRentalEnd( rentalStart );
        contractEndDate = sdtf.format( rentalEnd );

        //Get total price from daysRented * pricePrDay
        totalPrice = pricePrDay * GenDate.daysBetween( rentalStart, rentalEnd);

        //Create string for updateQuery
        return "'" + signedDate + "', '" + contractEndDate + "', '" + customerId +
                "', '" + salesmanId + "', '" + carId + "', '" + maxKm + "', '" + start_km +  "', '" + totalPrice +
                "', '" + contractStartDate + "'";
    }
    //endregion Contract Management

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reg_date = sdf.format(new Date());
        slowScroll(1000, "..........");//the date we acquire it
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
        //send query
        addCar( carInfo );
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
                }
                else
                {
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
                formattedHeader("Sort by model name");
                break;
            case 2: //sort Brand Name
                formattedHeader("Sort by brand name");
                break;
            case 3: //sort Number of seats
                formattedHeader("Sort by number of seats");
                break;
            case 4: //sort Availability
                formattedHeader("Sort by Availability");
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
        if (editChoice == 1)
        {
            text = "color";
        }
        else if (editChoice ==2)
        {
            text = "contract ID";
        }
        else if (editChoice ==3)
        {
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
            }
            else
            {
                int carEdit = ScannerReader.scannerInt();
            }
            boolean success = false;
            //query here to set new car colour
            //if query was successful then success = true;
            if (success)
            {
                formattedPrint("The car's " + text + " has been updated");
            }
            else
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
    //endregion Manage Car
    //endregion Menu methods

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
    public static void slowScroll(int delay, String text)
    {
        for(int i = 0; i < text.length(); i++)
        {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < delay) { }
            System.out.print(text.charAt(i));
        }
        System.out.println();
    }
    public static void slowScroll(String text)
    {
        for(int i = 0; i < text.length(); i++)
        {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < 100) { }
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
    }

    static void printLines()
    {
        for (int i=0; i<=headerLinesCount; i++)
        {
            System.out.print(headerLines);
        }
        System.out.println();
    }
    //endregion

    //endregion system.out.prints

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
                "date_signed, end_date, costumer_id, salesman_id, car_id, max_km, start_km, value, start_date " +
                ") VALUES ( " +
                contractInfo + " );";
        //Remember to set the choosen car's contract_id to match this contracts primary key
        int rows = DBInteraction.updateData(DBContractInfo);
        System.out.println( "Rows effected : " + rows);   //For verification that the data was updated
    }
    //endregion
}

