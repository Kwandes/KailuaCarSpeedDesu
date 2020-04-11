import java.text.SimpleDateFormat;
import java.util.Date;

public class Queries {

    public static void addPerson(String customerInfo)
    {
        String DBCostumorInfo = "INSERT INTO cunstomer( " +
                "first_name, last_name, cpr, zip, city, address, phone_number, email, drivers_licence_number, driver_since_date " +
                ") VALUES ( " +
                customerInfo + " );";
        DBInteraction.updateData( DBCostumorInfo );
    }

    public static void addCar(String carInfo)
    {
        String DBCostumorInfo = "INSERT INTO Car( " +
                "model, brand, color, plate_number, date_registered, km_driven, available, seats, other_specifications, fuel_type, price_pr_day " +
                ") VALUES ( " +
                carInfo + " );";
        DBInteraction.updateData( DBCostumorInfo );
    }


    //Contact info should be formattet ( Date startDate, Date endDate, int costumerId, int salesmanId, int carId, int maxKm, int startKm ) in a db query string
    public static void addContract( String contractInfo )
    {
        String DBContractInfo = "INSERT INTO Contract( " +
                "date_signed, end_date, costumer_id, salesman_id, car_id, max_km, start_km, value " +
                ") VALUES ( " +
                contractInfo + " );";
        //Remember to set the choosen car's contract_id to match this contracts primary key
        DBInteraction.updateData( DBContractInfo );
    }

}
