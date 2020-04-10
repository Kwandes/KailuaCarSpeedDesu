import java.text.SimpleDateFormat;
import java.util.Date;

public class Queries {

    private static final String art = "☭";
    private static final int headerLinesCount = 100;
    private static final String headerLines = "*";

    //region System.out.prints
    public static void car()
    {
        formattedHeader("Car menu");
        formattedPrint("View all cars");
            formattedHeader("View all cars");
            formattedPrint("To see a list of all available cars, please specify a sort type");
            System.out.println();
            formattedPrint("Sort by type");
            formattedPrint("Sort by availability");
            formattedPrint("Sort by price");
            formattedPrint("Sort by PP suck");
        formattedPrint("Manage cars");
            formattedHeader("Manage cars");
            formattedPrint("Change a car's information");
                formattedHeader("Change a car's information");
                formattedPrint("What would you like to change?");
                //show list of all cars


    }

    //region menu methods
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
        formattedPrint("Please type the registry date of the new car"); //the date we acquire it
        // String? or Date? idk you decide, in DB is type 'DATE'. the date we input always has to be current date though, since its the date we acquire it.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reg_date = sdf.format(new Date());
        System.out.println(reg_date);
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
        formattedPrint("This action can not be undone later.");
        System.out.println();
        formattedPrint("[1] Yes.");
        formattedPrint("[2] No.");
        int choice = ScannerReader.scannerInt(1,2);
        switch (choice)
        {
            case 1:
                formattedPrint("The car you selected has been deleted");
                //create query to delete the car
                //send the query
                break;
            case 2:
                //abort mission
                formattedPrint("Delete operation has been aborted");
                //return to main menu, somehow.
                break;
        }

    }

    //endregion

    //region System.out.print Methods
    public static void formattedPrint(String text)
    {

        System.out.printf("%-4s%-95s%-4s%n", art, text, art);
    }
    // prints a menu option in a '[num] option text' format
    public static void formattedPrint(int num, String text)
    {
        System.out.printf("%-4s [" + num + "] %-95s%-4s%n", art, text, art);
        //System.out.println("[" + num + "] " + text);
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
    public static void addPerson(String customer)
    {
        String DBCostumorInfo = "INSERT INTO constumer( " +
                "first_name, last_name, cpr, zip, city, address, phone_number, email, drivers_licence_number, driver_since_date " +
                ") VALUES ( " +
                customer + " );";
        DBInteraction.updateData(DBCostumorInfo);
    }

    public static void addRental(Date startDate, Date endDate, int costumerId, int salesmanId, int carId, int maxKm, int startKm)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String DBContractInfo = "INSERT INTO Contract( " +
                "date_signed, " +
                "end_date, " +
                "costumer_id, " +
                "salesman_id, " +
                "car_id, " +
                "max_km, " +
                "start_km," +
                "value" +
                ") VALUES ( " +
                "'" + sdf.format(startDate) + "', '" + sdf.format(endDate) + "', '" + costumerId + "', '" + salesmanId + "', '" + carId +
                "', '" + maxKm + "', '" + startKm + "'";
        DBInteraction.updateData(DBContractInfo);
    }
    //endregion
}

