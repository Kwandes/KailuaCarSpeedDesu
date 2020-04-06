import java.util.Date;

public class UserInteraction {

    public static void addPerson(Customer costumor)
    {
        String DBCostumorInfo = "INSERT INTO constumer( " +
                "first_name, " +
                "last_name, " +
                "cpr, " +
                "zip, " +
                "city, " +
                "address, " +
                "phone_number," +
                "email," +
                "drivers_licence_number," +
                "driver_since_date" +
                ") VALUES ( " +
                costumor.DBToString() + ");";
        DBInteraction.updateData( DBCostumorInfo );
    }

    public static void addRental( Date startDate, Date endDate, int costumerId, int salesmanId, int carId, int maxKm, int startKm)
    {
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
                "'" + startDate + "', '" + endDate + "', '" + costumerId + "', '" + salesmanId + "', '" + carId +
                "', '" + maxKm + "', '" + startKm + "'";
        DBInteraction.updateData( DBContractInfo );
    }

}
