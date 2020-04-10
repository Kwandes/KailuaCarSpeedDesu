import java.security.spec.RSAOtherPrimeInfo;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Queries {

    private static final String art = "☭";
    private static final int headerLinesCount = 100;
    private static final String headerLines = "*";

    //region System.out.prints


    public static void formattedPrint(String text)
    {
        System.out.printf("%-4s%-95s%-4s%n", art, text, art);
    }

    public static void formattedHeader(String text)
    {
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

