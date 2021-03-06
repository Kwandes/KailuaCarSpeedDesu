import com.mysql.cj.jdbc.exceptions.MySQLStatementCancelledException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Queries {

    private static final String art = "|";
    private static final int headerLinesCount = 126;
    private static final String headerLines = "_";
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
                slowScroll(500, "... ");
                ResultSet rs = DBInteraction.getData("SELECT * FROM contract;");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdtf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try
                {
                    System.out.printf("%-11s | %-20s| %-10s | %-10s | %-8s | %-11s | %-6s | %-11s |\n" ,"Contract ID","Date signed","Start Date","End Date","Start km","Km drivable","Price","Customer ID","Employee ID");
                    printLines();
                    while (rs.next()) // contract_id, date_signed, start_date, end_date, start_km, max_km | value | customer_id | salesman_id
                    {
                        System.out.printf("%-11s | %-20s| %-10s | %-10s | %-8s | %-11s | %-6s | %-11s |\n",
                                rs.getString("contract_id"),
                                rs.getString("date_signed"),
                                sdf.format(sdtf.parse(rs.getString("start_date"))),
                                sdf.format(sdtf.parse(rs.getString("end_date"))),
                                rs.getString("start_km"),
                                rs.getString("max_km"),
                                rs.getString("value"),
                                rs.getString("customer_id"),
                                rs.getString("salesman_id"));
                    }
                } catch (SQLException | NullPointerException | ParseException e)
                {
                    e.printStackTrace();
                }
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
                    //"'" + signedDate + "', '" + contractEndDate + "', '" + customerId + "', '" + salesmanId + "', '" + maxKm + "', '" + start_km +  "', '" + totalPrice +
                    // "', '" + contractStartDate + "'";
                    String contractInfo = genContract();
                    DBInteraction.updateData("INSERT INTO contract (date_signed, end_date, customer_id, salesman_id, max_km, start_km, value, start_date) " +
                            "VALUES (" + contractInfo +");");
                    //DBInteraction.updateData(contractInfo);
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
        SimpleDateFormat sdtf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

        //PRINT and select salesman
        try
        {
            String salesmanQuery = "SELECT * FROM salesman;";
            ResultSet salesmen = DBInteraction.getData( salesmanQuery );
            //Print info on all salesmen so user can select salesman for contract
            formattedPrint("Select a salesman");
            salesmen.absolute(0);
            while (salesmen.next())
            {
                System.out.printf("ID : %-6s | Name : %-30s | Cpr nr : %-15s \n",
                        salesmen.getString("salesman_id"),
                        salesmen.getString("first_name") + " " + salesmen.getString("last_name"),
                        salesmen.getString("cpr"));
            }

            //Select salesman_id
            //moves to the first row in the salesman list so i know the smallest id in the DB
            salesmen.absolute(0);
            salesmen.next();
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
                String customerQuery = "SELECT * FROM customer;";
                ResultSet customers = DBInteraction.getData( customerQuery );
                //Print info on all customer so user can select customer for contract
                formattedPrint("Select a customer");
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
                customers.next();
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
            // [removed}
            System.out.println("Register a new one yourself");

            //Auto gens a customer for you
            String personInfo = GenPerson.returnCustomer();
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
        carId = getCarId();
        //Date signed
        signedDate = sdtf.format(new Date());

        //get start date
        formattedPrint("Please type the desired start date of the contract");
        formattedPrint("Please use the following format: yyyy-MM-dd");
        Date startDate = ScannerReader.scannerDate( new Date() );
        contractStartDate = sdtf.format( startDate );

        System.out.println(contractEndDate);

        //get end date (after start of contract date
        formattedPrint("Please type the desired end date of the contract");
        formattedPrint("Please use the following format: yyyy-MM-dd");
        Date endDate = ScannerReader.scannerDate( startDate );
        contractEndDate = sdtf.format( endDate );

        //get max_km
        formattedPrint("What should max km limit be? ");
        Queries.printLines();
        System.out.print("\tSelect from 0 - 10.000 : ");
        maxKm = ScannerReader.scannerInt(0, 10000);

        //get start_km
        try //STUCK
        {
            String carQuery = "SELECT * FROM car " +
                    "WHERE car_id = '" + carId + "';";
            ResultSet cars = DBInteraction.getData( carQuery );
            cars.next();
            start_km = cars.getInt("km_driven");
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //get total price of rental
        //price pr day from selected car
        pricePrDay = -1;
        try
        {
            String carQuery = "SELECT * FROM car " +
                    "WHERE car_id = '" + carId + "';";
            ResultSet cars = DBInteraction.getData( carQuery );
            cars.next();
            pricePrDay = Integer.parseInt( cars.getString("price_per_day") );
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //get amount of days from the rental start and end dates
        int daysOfRental = GenDate.daysBetween( startDate, endDate );
        totalPrice = pricePrDay * daysOfRental;

        //Create string for updateQuery
        return "'" + signedDate + "', '" + contractEndDate + "', '" + customerId +
                "', '" + salesmanId + "', '" + maxKm + "', '" + start_km +  "', '" + totalPrice +
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
        SimpleDateFormat sdtf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

        //Get random salesman_id from DB
        try {
            String salesmanQuery = "SELECT * FROM salesman;";
            ResultSet salesmen = DBInteraction.getData(salesmanQuery);
            //Print info on all salesmen so user can select salesman for contract

            //moves to the first row in the salesman list so i know the smallest id in the DB
            salesmen.absolute(0);
            salesmen.next();
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
            String carQuery = "SELECT * FROM car " +
                    "WHERE available = TRUE;";
            ResultSet cars = DBInteraction.getData( carQuery );
            //Print info on all salesmen so user can select salesman for contract

            //Select car_id
            //moves to the first row in the cars list so i know the smallest id in the DB
            cars.absolute(0);
            cars.next();
            int firstRowId = cars.getInt("car_id");
            //moves to the last row in the cars list so i know the largest id in the DB
            cars.last();
            int lastRowId = cars.getInt("car_id");

            //gets a random nr from car id's
            if ((lastRowId - firstRowId) != 0)
            {
                carId = firstRowId + rand.nextInt(lastRowId - firstRowId);
            } else
            {
                carId = firstRowId;
            }

        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Get start km from selected car
        try
        {
            String carQuery = "SELECT * FROM car " +
                    "WHERE car_id = '" + carId + "';";
            ResultSet cars = DBInteraction.getData( carQuery );
            cars.next();
            start_km = cars.getInt("km_driven");
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Get price from selected car
        try
        {
            String carQuery = "SELECT * FROM car " +
                    "WHERE car_id = '" + carId + "';";
            ResultSet cars = DBInteraction.getData( carQuery );
            cars.next();
            pricePrDay = cars.getInt("price_per_day");
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Date signed
        signedDate = sdtf.format(new Date());

        //Get get random Customer id from DB
        try
        {
            String customerQuery = "SELECT * FROM customer;";
            ResultSet customers = DBInteraction.getData( customerQuery );
            //Print info on all customer so user can select customer for contract

            //moves to the first row in the customer list so i know the smallest id in the DB
            customers.absolute(0);
            customers.next();
            int firstRowId = customers.getInt("customer_id");
            //moves to the last row in the customer list so i know the largest id in the DB
            customers.last();
            int lastRowId = customers.getInt("customer_id");
            if ((lastRowId-firstRowId) != 0)
            {
                customerId = firstRowId + rand.nextInt(lastRowId - firstRowId); //selects a random customer_id that is in the DB!
            } else
            {
                customerId = firstRowId;
            }
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

        //generate drivable Km (100 - 10000)
        maxKm = rand.nextInt(9901)+100;

        //Create string for updateQuery
        return "'" + signedDate + "', '" + contractEndDate + "', '" + customerId +
                "', '" + salesmanId + "', '" + maxKm + "', '" + start_km +  "', '" + totalPrice +
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
        slowScroll(500, "... ");//the date we acquire it
        System.out.println("Registry date is: " + reg_date);

        formattedPrint("Please type the current kilometers on the dial of the car"); //default is 0
        int km_driven_TEMP = ScannerReader.scannerInt(0, 200000);

        formattedPrint("Please type the amount of seats the car has"); //default is 4
        int seats_TEMP = ScannerReader.scannerInt(2,9);

        formattedPrint("Please type the slogan or catchphrase of the new car, if it has one"); //an example is "I am Speed" for 'Lightning McQueen'
        String other_specifications_TEMP = ScannerReader.scannerWords();

        formattedPrint("Please type the fuel type of the new car"); //default is gas
        String fuel_type_TEMP = ScannerReader.scannerWords();

        formattedPrint("Please type the price per day of lease of the new car");
        int price_per_day_TEMP = ScannerReader.scannerInt(1, 9999); //DB type is INT. I made a string because idk how you wanna use it

        formattedPrint("Lastly, please type the type of the new car [ Family, Luxury, Sport ]");
        String type_TEMP = ScannerReader.scannerWords();

        //create query
        String carInfo = "'" + model_TEMP + "', '" + brand_TEMP + "', '" +  color_TEMP + "', '" + plate_number_TEMP + "', '" + reg_date +
                "', '"+ km_driven_TEMP + "', TRUE, '" + seats_TEMP + "', '" + other_specifications_TEMP +  "', '" + fuel_type_TEMP +
                "', '" + price_per_day_TEMP + "', '" + type_TEMP + "'";
        //send query [model, brand, color, plate_number, date_registered, km_driven, available, seats, other_specifications, fuel_type, price_per_day, type]
        addCar( carInfo );
    }

    public static int getCarId() {
        int carId = -1;
        try
        {
            String carQuery = "SELECT * FROM car " + "WHERE available = TRUE;";
            ResultSet cars = DBInteraction.getData( carQuery );
            //Print info on all salesmen so user can select salesman for contract
            formattedPrint("Plese select a car");
            cars.absolute(0);
            while (cars.next())
            {
                System.out.printf("ID : %-6s | model : %-25s | brand : %-15s | Price pr Day : %-10s \n",
                        cars.getString("car_id"),
                        cars.getString("model"),
                        cars.getString("brand"),
                        cars.getString("price_per_day"));
            }
            //Select car_id
            //moves to the first row in the cars list so i know the smallest id in the DB
            cars.absolute(0);
            cars.next(); //[REWRITE] [FIX]
            //int firstRowId = Integer.parseInt(cars.getString("car_id")); [REWRITE]
            int firstRowId = cars.getInt("car_id");
            //moves to the last row in the cars list so i know the largest id in the DB
            cars.last();
            //int lastRowId = Integer.parseInt(cars.getString("car_id"));  [REWRITE]
            int lastRowId = cars.getInt("car_id");
            System.out.println();
            Queries.printLines();
            formattedPrint("Select from " + firstRowId + " - " + lastRowId + " : ");
            carId = ScannerReader.scannerInt( firstRowId, lastRowId);                   //selects an Salesman_id that is in the system!
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }
        return carId;
    }

    public static void deleteCar()
    {
        int carIdToRemove = -1;
        formattedHeader("Delete a car");
        //print list of cars here with their IDs
        carIdToRemove = getCarId();
        formattedPrint("You have selected car ID " + carIdToRemove);
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
                String removalQuery = "DELETE FROM car WHERE car_id = '" + carIdToRemove + "';";
                int rowsAffected = DBInteraction.updateData( removalQuery );

                if (rowsAffected >= 1)
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
        formattedPrint(5,"By price pr day");
        formattedPrint(6,"Return to main Menu");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int choice = ScannerReader.scannerInt(1,6);

        switch(choice)
        {
            case 1: //sort Model Name
                formattedHeader("Sort by model name");
                String carQuery = "SELECT * FROM car ORDER BY model;";
                printCarInfo(carQuery);
                break;
            case 2: //sort Brand Name
                formattedHeader("Sort by brand name");
                carQuery = "SELECT * FROM car ORDER BY brand;";
                printCarInfo(carQuery);
                break;
            case 3: //sort Number of seats
                formattedHeader("Sort by number of seats");
                carQuery = "SELECT * FROM car ORDER BY seats;";
                printCarInfo(carQuery);
                break;
            case 4: //sort Availability
                formattedHeader("Sort by Availability");
                carQuery = "SELECT * FROM car ORDER BY available;";
                printCarInfo(carQuery);
                break;
            case 5: //sort Price Per Day
                formattedHeader("Sort by price pr day");
                carQuery = "SELECT * FROM car ORDER BY price_per_day;";
                printCarInfo(carQuery);
                break;
            case 6: //main menu

                break;
        }
    }

    public static void printCarInfo( String carQuery )
    {
        try {
            ResultSet cars = DBInteraction.getData(carQuery);
            //Print info on all salesmen so user can select salesman for contract
            formattedHeader("Select a car");
            cars.absolute(0);
            // ||||||||||
            System.out.printf("| %-7s| %-10s| %-13s| %-10s| %-10s| %-7s| %-6s| %-15s| %-15s| %-12s|\n", "Car ID", "Available", "Plate Number", "Brand","Model","Type","Seats","Specification","Price per day", "Contract ID"); //10
            printLines();
            while (cars.next()) {
                System.out.printf("| %-7s| %-10s| %-13s| %-10s| %-10s| %-7s| %-6s| %-15s| %-15s| %-12s|\n",
                        cars.getString("car_id"),
                        cars.getString("available"),
                        cars.getString("plate_number"),
                        cars.getString("brand"),
                        cars.getString("model"),
                        cars.getString("type"),
                        cars.getString("seats"),
                        cars.getString("other_specifications"),
                        cars.getString("price_per_day"),
                        cars.getString("contract_id"));
            }
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }
    }

    public static void updateCar()
    {
        //uuurgh
        int carId = -1;
        formattedHeader("Change car details");
        formattedPrint("Please select a car");
        //print list of all cars
        String carQuery = "SELECT * FROM car;";
        printCarInfo(carQuery);

        //now select a car
        ResultSet cars = DBInteraction.getData(carQuery);
        try
        {
            cars.absolute(0);
            cars.next();
            int firstRowId = cars.getInt("car_id");
            //moves to the last row in the cars list so i know the largest id in the DB
            cars.last();
            int lastRowId = cars.getInt("car_id");
             System.out.println();
             Queries.printLines();
             System.out.print("\tSelect from " + firstRowId + " - " + lastRowId + " : ");
             carId = ScannerReader.scannerInt( firstRowId, lastRowId);                   //selects an Salesman_id that is in the system!
        }
        catch (SQLException | NullPointerException e) { e.printStackTrace(); }

        //Select what you want to change
        formattedPrint("What would you like to change?");
        formattedPrint(1,"Plate Number");
        formattedPrint(2,"Specification");
        formattedPrint(3,"Seats");
        formattedPrint(4,"Contract ID of a car"/*Availability*/);
        formattedPrint(5,"Cancel");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int editChoice = ScannerReader.scannerInt(1,5);
        String text = "";
        int success;
        switch(editChoice)
        {
            case 1:
                formattedHeader("Change plate number");
                formattedPrint("What would you like to change the plate number to?");
                String plate_number_TEMP = ScannerReader.scannerWords();
                success = DBInteraction.updateData("UPDATE car SET plate_number = '"+ plate_number_TEMP + "' WHERE car_id = "+ carId +";");
                successCheck(success);
                //send query here
                break;
            case 2:
                formattedHeader("Change specification");
                formattedPrint("What would you like to change the specification to?");
                String specification_TEMP = ScannerReader.scannerWords(); //LIMITED TO ONE SINGLE WORD INPUT
                success = DBInteraction.updateData("UPDATE car SET other_specifications = '"+ specification_TEMP + "' WHERE car_id = "+ carId +";");
                successCheck(success);
                //send query here
                break;
            case 3:
                formattedHeader("Change seats");
                formattedPrint("How many seats does the car now have?");
                int seats_TEMP = ScannerReader.scannerInt(2,9);
                success = DBInteraction.updateData("UPDATE car SET seats = '"+ seats_TEMP + "' WHERE car_id = "+ carId +";");
                successCheck(success);
                break;
            case 4:
                formattedPrint("Do you want to close the contract ID or change it?");
                formattedPrint(1,"Change it");
                formattedPrint(2,"Close it");
                slowScroll(500,"Dweit");
                Queries.printLines();
                System.out.print("\tSelect : ");
                int choices = ScannerReader.scannerInt(1,2);
                switch (choices)
                {
                    case 1:
                        int[] limit = showContracts();
                        formattedPrint("What do you want to change the ID to?");
                        int contract_id_TEMP = ScannerReader.scannerInt();
                        success = DBInteraction.updateData("UPDATE car SET contract_id = "+ contract_id_TEMP + ", available = 0 WHERE car_id = "+ carId +";");
                        successCheck(success);
                        break;
                    case 2:
                        formattedHeader("Close the contract");
                        success = DBInteraction.updateData("UPDATE car SET contract_id = NULL , available = 1 WHERE car_id = "+ carId +";");
                        successCheck(success);
                        break;
                }
                break;
            case 5: //main menu

                break;
        }
    }

    public static void manageCar()
    {
        formattedHeader("Manage cars");
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

    //region manageEmployee

    // Create, read and remove salesman from the DB
    // Handles all the prinouts, input and logic related to CRUD of employee
    public static void manageEmployee()
    {
        formattedHeader("Manage Employees");
        formattedPrint("What would you like to do?");
        formattedPrint(1, "See all Employees");
        formattedPrint(2, "Register a new Employee");
        formattedPrint(3, "Remove an Employee");
        formattedPrint(4, "Return to main menu");

        System.out.println();
        Queries.printLines();

        System.out.print("\tSelect : ");
        int selection = ScannerReader.scannerInt(1, 4);
        // choose which action to perform
        switch (selection)
        {
            case 1: //see all existing salesman
                formattedHeader("See all employees");
                // Decide the data presentation order
                formattedPrint("How would you like to order the list:");
                formattedPrint(1, "by last name, Ascending");
                formattedPrint(2, "by last name, Descending");
                String dataOrder = ";";
                switch(ScannerReader.scannerInt(1,2))
                {
                    case 1:
                        dataOrder = " ORDER BY last_name ASC";
                        break;
                    case 2:
                        dataOrder = " ORDER BY last_name DESC";
                        break;
                }

                // Get and display salesman information
                formattedPrint("Getting the list of all employees");
                slowScroll(500, "... ");
                ResultSet rs = DBInteraction.getData("SELECT * FROM salesman" + dataOrder);
                Log.trace("User has retrieved salesman information");
                try
                {
                    System.out.printf("%-11s | %-20s| %-20s | %-15s |\n", "Salesman ID", "First Name", "Last Name", "CPR");
                    printLines();
                    while (rs.next()) // salesman_id, first_name, last_name, cpr
                    {
                        System.out.printf("%-11s | %-20s| %-20s | %-15s |\n",
                                rs.getString("salesman_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("cpr"));
                    }
                } catch (SQLException | NullPointerException e)
                {
                    e.printStackTrace();
                    Log.warning("An issue has occurred while displaying employee information", e);
                }
                break;

            case 2: // Register a new salesman
                formattedHeader("Register a new Employee");
                // get a creationQuery from a method that handles data generation/ user input
                String newEmployeeQuery = createEmployeeQuery();
                // if returned query is null (for example, user has aborted registration), break out
                if (newEmployeeQuery.equals("")) break;

                // send the query to the DB
                int result = DBInteraction.updateData(newEmployeeQuery);
                if (result == -1) formattedPrint("Failed to create a new employee, contact Administrator for help");
                else
                {
                    formattedPrint("New employee has been added");
                    Log.info("New employee has been added");
                }
                break;

            case 3: // Remove an existing salesman
                formattedHeader("Remove an employee");
                formattedPrint("What is the id/cpr of the employee you wish to remove?");
                // search for a specific employee
                String employeeIdentifier = ScannerReader.scannerAll();
                String searchQuery = "SELECT * FROM salesman WHERE salesman_id LIKE '%" + employeeIdentifier + "%' OR cpr LIKE '%" + employeeIdentifier + "%';";

                // check the results for salesman
                ResultSet foundSalesman = DBInteraction.getData(searchQuery);
                int resultCount = 0;
                String name = "N/A";
                Log.trace("User has retrieved salesman information");
                try
                {
                    System.out.printf("%-11s | %-20s| %-20s | %-15s |\n", "Salesman ID", "First Name", "Last Name", "CPR");
                    printLines();
                    while (foundSalesman.next()) // salesman_id, first_name, last_name, cpr
                    {
                        System.out.printf("%-11s | %-20s| %-20s | %-15s |\n",
                                foundSalesman.getString("salesman_id"),
                                foundSalesman.getString("first_name"),
                                foundSalesman.getString("last_name"),
                                foundSalesman.getString("cpr"));
                        name = foundSalesman.getString("first_name") + " " + foundSalesman.getString("last_name");
                        resultCount++;
                    }
                } catch (SQLException | NullPointerException e)
                {
                    if (resultCount == 0)
                    formattedPrint("No employee found");
                    e.printStackTrace();
                    Log.warning("An issue has occurred while displaying employee information", e);
                }
                if (resultCount == 0)
                    formattedPrint("No employee found");
                else if (resultCount > 1)
                    // For simplicity, if the search is unsuccessful, you return to the main menu instead of having the entire thing in a while loop
                    formattedPrint("More than 1 employee found, aborting");
                else
                {
                    formattedPrint("Would you like to delete " + name);
                    formattedPrint(1, "yes");
                    formattedPrint(2, "no");
                    int choice = ScannerReader.scannerInt(1,2);
                    if(choice == 1)
                    {
                        Log.info("Removing salesman " + name);
                        String removalQuery = "DELETE FROM salesman WHERE salesman_id LIKE '%" + employeeIdentifier + "%' OR cpr LIKE '%" + employeeIdentifier + "%';";
                        DBInteraction.updateData(removalQuery);
                    }
                    else Log.trace("Removal of " + name + " has been aborted");
                }
                break;
            case 4: // Return to the main menu
                break;
        }

    }

    // returns a complete query for creating a salesman
    // salesman data is obtained from user input OR auto-generated
    private static String createEmployeeQuery()
    {
        String query = "";

        formattedPrint("Do you wish to manually register a salesman or auto-generate them?");
        System.out.println();
        formattedPrint("[1] manually register");
        formattedPrint("[2] auto-generate");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int choice = ScannerReader.scannerInt(1, 2);

        // prepare variables for handling input/ data generation
        String firstName = "Bob";
        String lastName = "Stronk";
        char sex = 'f'; // used for generation of cpr. Default is f, for female
        String cpr = "";

        // get data based on the method chosen by the user
        switch (choice)
        {
            case 1: // manually gather all the data from user
                formattedPrint("What is the employees' first name?");
                firstName = ScannerReader.scannerWords();
                formattedPrint("What is the employees' Last name?");
                lastName = ScannerReader.scannerWords();

                // get birth date for the CPR
                formattedPrint("What is the employees' birthdate?");
                // stop using deprecated classes!
                Date deprecatedDate = new GregorianCalendar(1901, 1, 1).getTime();
                Date birthDate = ScannerReader.scannerDate(deprecatedDate);
                sex = ((new Random().nextInt(1) == 1) ? 'f' : 'm'); // randomly assign salesman's sex
                cpr = GenPerson.genCpr(birthDate, sex);
                break;

            case 2: // auto-generate all the data using Teo's generation classes
                sex = ((new Random().nextInt(1) == 1) ? 'f' : 'm'); // randomly assign salesman's sex
                firstName = GenPerson.genName(sex);
                lastName = GenPerson.genSurname();
                cpr = GenPerson.genCpr(sex);
                break;
        }
        // create a creation query based on the input/generated data
        query = "INSERT INTO salesman(first_name, last_name, cpr) VALUES ('" + firstName + "', '" + lastName + "', '" + cpr + "');";
        Log.trace("New salesman has been created (but not uploaded yet): " + firstName + " " + lastName + " - " + cpr);

        // Confirm new employee information
        formattedPrint("New salesman will be added: " + firstName + " " + lastName + " - " + cpr);
        formattedPrint(1, "Add");
        formattedPrint(2, "Abort");

        choice = ScannerReader.scannerInt(1, 2);
        if (choice == 1) return query;
        else
        {
            Log.trace("User has aborted addition of " + firstName + " " + lastName);
            return "";
        }
    }
    //endregion

    //region manageCustomer

    // Create, read and remove salesman from the DB
    // Handles all the prinouts, input and logic related to CRUD of employee
    public static void manageCustomer()
    {
        formattedHeader("Manage Customer");
        formattedPrint("What would you like to do?");
        formattedPrint(1, "See all Customers");
        formattedPrint(2, "Register a new Customer");
        formattedPrint(3, "Remove a Customer");
        formattedPrint(4, "Return to main menu");

        System.out.println();
        Queries.printLines();

        System.out.print("\tSelect : ");
        int selection = ScannerReader.scannerInt(1, 4);
        // choose which action to perform
        switch (selection)
        {
            case 1: //see all existing salesman
                formattedHeader("See all Customers");
                // Decide the data presentation order
                formattedPrint("How would you like to order the list:");
                formattedPrint(1, "by last name, Ascending");
                formattedPrint(2, "by last name, Descending");
                String dataOrder = ";";
                switch(ScannerReader.scannerInt(1,2))
                {
                    case 1:
                        dataOrder = " ORDER BY last_name ASC";
                        break;
                    case 2:
                        dataOrder = " ORDER BY last_name DESC";
                        break;
                }

                // Get and display salesman information
                formattedPrint("Getting the list of all Customers");
                slowScroll(500, "... ");
                ResultSet rs = DBInteraction.getData("SELECT * FROM customer" + dataOrder);
                Log.trace("User has retrieved customer information");
                try
                {
                    System.out.printf("%-11s | %-15s| %-20s | %-15s | %-15s | %-5s | %-26s |\n", "Customer ID", "First Name", "Last Name", "CPR", "City", "Zip", "Email");
                    printLines();
                    while (rs.next()) // salesman_id, first_name, last_name, cpr, city, email
                    {
                        System.out.printf("%-11s | %-15s| %-20s | %-15s | %-15s | %-5s | %-26s |\n",
                                rs.getString("customer_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("cpr"),
                                rs.getString("city"),
                                rs.getString("zip"),
                                Security.decryptData(rs.getString("email")));
                    }
                } catch (SQLException | NullPointerException e)
                {
                    e.printStackTrace();
                    Log.warning("An issue has occurred while displaying customer information", e);
                }
                break;

            case 2: // Register a new Customer
                formattedHeader("Register a new Customer");
                // get a creationQuery from a method that handles data generation/ user input
                String newCustomerQuery = createCustomerQuery();
                // if returned query is null (for example, user has aborted registration), break out
                if (newCustomerQuery.equals("")) break;
                // send the query to the DB
                int result = DBInteraction.updateData(newCustomerQuery);
                if (result == -1) formattedPrint("Failed to create a new customer, contact Administrator for help");
                else
                {
                    formattedPrint("New customer has been added");
                    Log.info("New customer has been added");
                }
                break;

            case 3: // Remove an existing customer
                formattedHeader("Remove a Customer");
                formattedPrint("What is the id/cpr of the customer you wish to remove?");
                // search for a specific employee
                String customerIdentifier = ScannerReader.scannerAll();
                String searchQuery = "SELECT * FROM customer WHERE customer_id LIKE '%" + customerIdentifier + "%' OR cpr LIKE '%" + customerIdentifier + "%';";

                // check the results for customer
                ResultSet foundCustomer = DBInteraction.getData(searchQuery);
                int resultCount = 0;
                String name = "N/A";
                Log.trace("User has retrieved customer information");
                try
                {
                    System.out.printf("%-11s | %-15s| %-20s | %-15s | %-15s | %-5s | %-26s |\n", "Customer ID", "First Name", "Last Name", "CPR", "City", "Zip", "Email");
                    printLines();
                    while (foundCustomer.next()) // salesman_id, first_name, last_name, cpr, city, email
                    {
                        System.out.printf("%-11s | %-15s| %-20s | %-15s | %-15s | %-5s | %-26s |\n",
                                foundCustomer.getString("customer_id"),
                                foundCustomer.getString("first_name"),
                                foundCustomer.getString("last_name"),
                                foundCustomer.getString("cpr"),
                                foundCustomer.getString("city"),
                                foundCustomer.getString("zip"),
                                Security.decryptData(foundCustomer.getString("email")));
                        name = foundCustomer.getString("first_name") + " " + foundCustomer.getString("last_name");
                        resultCount++;
                    }
                } catch (SQLException | NullPointerException e)
                {
                    if (resultCount == 0)
                        formattedPrint("No customer found");
                    e.printStackTrace();
                    Log.warning("An issue has occurred while displaying customer information", e);
                }
                if (resultCount == 0)
                    formattedPrint("No customer found");
                else if (resultCount > 1)
                    // For simplicity, if the search is unsuccessful, you return to the main menu instead of having the entire thing in a while loop
                    formattedPrint("More than 1 customer found, aborting");
                else
                {
                    formattedPrint("Would you like to delete " + name);
                    formattedPrint(1, "yes");
                    formattedPrint(2, "no");
                    int choice = ScannerReader.scannerInt(1,2);
                    if(choice == 1)
                    {
                        Log.info("Removing customer " + name);
                        String removalQuery = "DELETE FROM customer WHERE customer_id LIKE '%" + customerIdentifier + "%' OR cpr LIKE '%" + customerIdentifier + "%';";
                        DBInteraction.updateData(removalQuery);
                    }
                    else Log.trace("Removal of " + name + " has been aborted");
                }
                break;
            case 4: // Return to the main menu
                break;
        }

    }

    // returns a complete query for creating a customer
    // customer data is obtained from user input OR auto-generated
    private static String createCustomerQuery()
    {
        String query = "";

        formattedPrint("Do you wish to manually register a customer or auto-generate them?");
        System.out.println();
        formattedPrint("[1] manually register");
        formattedPrint("[2] auto-generate");
        System.out.println();
        Queries.printLines();
        System.out.print("\tSelect : ");
        int choice = ScannerReader.scannerInt(1, 2);

        // get data based on the method chosen by the user
        switch (choice)
        {
            case 1: // manually gather all the data from user
                formattedPrint("Due to performance and value of time, we have auto generated a user for you.");
                //formattedPrint(GenPerson.returnCustomer());
                break;

            case 2: // auto-generate all the data using Teo's generation classes
                break;
        }
        // create a creation query based on the input/generated data
        query = "INSERT INTO customer(first_name, last_name, cpr, zip, city, address, phone_number, email, driver_license_number, driver_since_date)" +
                " VALUES (" + GenPerson.returnCustomer() + ");";

        // extract first name, last name and CPR from the query using regex
        Pattern pattern = Pattern.compile( "VALUES\\s\\('(\\w+)',\\s'(\\w+)',\\s'(\\w+)'");
        Matcher matcher = pattern.matcher(query);
        matcher.find();
        String customerData = matcher.group(1) + " " + matcher.group(2) + " - " + matcher.group(3);

        // get and encrypt the email
        pattern = Pattern.compile("'(\\w+@\\w+\\.\\w+)'");
        matcher = pattern.matcher(query);
        matcher.find();

        query = query.replaceAll("'(\\w+@\\w+\\.\\w+)'", "'" + Security.encryptData(matcher.group(1)) + "'");
        Log.trace("New customer has been created (but not uploaded yet): " + customerData);

        // Confirm new customer information
        formattedPrint("New customer will be added: " + customerData);
        formattedPrint(1, "Add");
        formattedPrint(2, "Abort");

        choice = ScannerReader.scannerInt(1, 2);
        if (choice == 1) return query;
        else
        {
            Log.trace("User has aborted addition of " + customerData);
            return "";
        }
    }
    //endregion

    //endregion Menu methods

    //region System.out.print Methods
    public static void successCheck(int success)
    {
        if (success >= 1)
        {
            formattedPrint("Updating database");
            slowScroll(500, "... ");
            formattedPrint("Update successful");
        } else
        {
            formattedPrint("Something went wrong, database is unchanged");
        }
    }

    public static void formattedPrint(String text)
    {
        System.out.printf("%-2s%-"+ (headerLinesCount-3)+"s%s%n", art, text, art);
    }
    // prints a menu option in a '[num] option text' format
    public static void formattedPrint(int num, String text)
    {
        System.out.printf("%s [" + num + "] %-"+ (headerLinesCount-7) +"s%s%n", art, text, art);
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
        String DBCustomerInfo = "INSERT INTO customer( " +
                "first_name, last_name, cpr, zip, city, address, phone_number, email, driver_license_number, driver_since_date " +
                ") VALUES ( " +
                customerInfo + " );";
        int rows = DBInteraction.updateData( DBCustomerInfo ); //For verification that the data was updated
        System.out.println( "Rows effected : " + rows);
    }

    public static void addCar(String carInfo)
    {
        String DBCarInfo = "INSERT INTO car( " +
                "model, brand, color, plate_number, date_registered, km_driven, available, seats, other_specifications, fuel_type, price_per_day, type " +
                ") VALUES ( " +
                carInfo + " );";
        int rows = DBInteraction.updateData( DBCarInfo ); //For verification that the data was updated
        System.out.println( "Rows effected : " + rows);
    }

    public static int[] showContracts()
    {
        int[] limits = new int[2];
        ResultSet rs = DBInteraction.getData("SELECT * FROM contract;");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdtf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            System.out.printf("%-11s | %-20s| %-10s | %-10s | %-8s | %-11s | %-6s | %-11s |\n" ,"Contract ID","Date signed","Start Date","End Date","Start km","Km drivable","Price","Customer ID","Employee ID");
            printLines();
            while (rs.next()) // contract_id, date_signed, start_date, end_date, start_km, max_km | value | customer_id | salesman_id
            {
                System.out.printf("%-11s | %-20s| %-10s | %-10s | %-8s | %-11s | %-6s | %-11s |\n",
                        rs.getString("contract_id"),
                        rs.getString("date_signed"),
                        sdf.format(sdtf.parse(rs.getString("start_date"))),
                        sdf.format(sdtf.parse(rs.getString("end_date"))),
                        rs.getString("start_km"),
                        rs.getString("max_km"),
                        rs.getString("value"),
                        rs.getString("customer_id"),
                        rs.getString("salesman_id"));
            }
            rs.absolute(0);
            rs.next();
            limits[0] = rs.getInt("contract_id");
            rs.last();
            limits[1] = rs.getInt("contract_id");
        } catch (SQLException | NullPointerException | ParseException e)
        {
            e.printStackTrace();
        }
        return limits;
    }

    //Contact info should be formatted ( Date startDate, Date endDate, int customerId, int salesmanId, int carId, int maxKm, int startKm ) in a db query string
    public static void addContract( String contractInfo ) {
        String DBContractInfo = "INSERT INTO contract( " +
                "date_signed, end_date, customer_id, salesman_id, max_km, start_km, value, start_date " +
                ") VALUES ( " +
                contractInfo + " );";
        //Remember to set the choosen car's contract_id to match this contracts primary key
        int rows = DBInteraction.updateData(DBContractInfo);
        System.out.println( "Rows effected : " + rows);   //For verification that the data was updated
    }
    //endregion
}

