import java.text.SimpleDateFormat;
import java.util.Date;


//region System.out.prints



//endregion

//region queries
public class Queries {

    public static void addPerson(String customer)
    {
        String DBCostumorInfo = "INSERT INTO constumer( " +
                "first_name, last_name, cpr, zip, city, address, phone_number, email, drivers_licence_number, driver_since_date " +
                ") VALUES ( " +
                customer + " );";
        DBInteraction.updateData( DBCostumorInfo );
    }

    public static void addRental( Date startDate, Date endDate, int costumerId, int salesmanId, int carId, int maxKm, int startKm)
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy");
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
                "'" + sdf.format( startDate ) + "', '" + sdf.format( endDate ) + "', '" + costumerId + "', '" + salesmanId + "', '" + carId +
                "', '" + maxKm + "', '" + startKm + "'";
        DBInteraction.updateData( DBContractInfo );
    }
//endregion
    
}
