import java.util.Date;

public class UserInteraction {

    public static void addPerson(Person person)
    {
        String DBPersonInfo = "INSERT INTO person( " +
                "pe_first_name, " +
                "pe_last_name, " +
                "pe_zip, " +
                "pe_city, " +
                "pe_cpr, " +
                "pe_phone," +
                "pe_email," +
                "pe_licence_number," +
                "pe_licence_date" +
                ") VALUES ( " +
                person.DBPersonCar() + ");";
        DBInteraction.updateData( DBPersonInfo );
    }

    public static void addRental(Person person, Car car, Date startDate, Date endDate)
    {
        String DBPersonInfo = "INSERT INTO person( " +
                "pe_first_name, " +
                "pe_last_name, " +
                "pe_zip, " +
                "pe_city, " +
                "pe_cpr, " +
                "pe_phone," +
                "pe_email," +
                "pe_licence_number," +
                "pe_licence_date" +
                ") VALUES ( " +
                person.DBPersonCar() + ");";
        DBInteraction.updateData( DBPersonInfo );
    }

}
