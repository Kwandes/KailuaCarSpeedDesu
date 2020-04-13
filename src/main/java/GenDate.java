import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GenDate {

    private static Random rand = new Random();

    public static Date genBirthday()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            //The persons birthday will be after the day 1950-01-01 so he/she cant be over 70 y/o
            Date startInclusive = sdf.parse("1950-01-01");
            //The persons birthday will be after the day 2002-01-01 so he/she cant be under 18 y/o
            Date endExclusive = sdf.parse("2002-01-01");
            long startMillis = startInclusive.getTime();
            long endMillis = endExclusive.getTime();
            long randomMillisSinceEpoch = ThreadLocalRandom.current()
                    .nextLong(startMillis, endMillis);

            return new Date(randomMillisSinceEpoch);
        }
        catch (ParseException e) { e.printStackTrace(); }

        return null;
    }

    //Get (int) days between two dates
    public static int daysBetween(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return 1 + (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Date genCarRegDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            //The persons birthday will be after the day 1950-01-01 so he/she cant be over 70 y/o
            Date startInclusive = sdf.parse("1980-01-01");
            //The persons birthday will be after the day 2002-01-01 so he/she cant be under 18 y/o
            Date endExclusive = sdf.parse("2020-01-01");
            long startMillis = startInclusive.getTime();
            long endMillis = endExclusive.getTime();
            long randomMillisSinceEpoch = ThreadLocalRandom.current()
                    .nextLong(startMillis, endMillis);

            return new Date(randomMillisSinceEpoch);
        }
        catch (ParseException e) { e.printStackTrace(); }

        return null;
    }

    //Gets a random date between the age of 18 and till now.
    //Ex. person was born in 01-01-2000. they turned 18 y/o 01-01-2018.
    //this will return a date between now (01-04-2020) and 01-01-2018.
    public static Date genLicenceDate( Date birthday )
    {
        Calendar c = Calendar.getInstance();
        c.setTime(birthday);
        c.add(Calendar.DAY_OF_YEAR, 6570);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date earlyDate = c.getTime();

        return genRandDate( new Date(), earlyDate );
    }

    //return a date from now to 180 days from now(ca. 6 months)
    //used for determining the rental start
    public static Date genRentalDate()
    {
        int rentalDays = rand.nextInt(180);
        Calendar cal = Calendar.getInstance();
        cal.setTime( new Date() );
        cal.add(Calendar.DAY_OF_MONTH, rentalDays);
        return cal.getTime();
    }

    //Takest the rental start date and returns a random end date of rental
    //MAX 60 days.
    public static Date genRentalEnd( Date rentalStart )
    {
        int rentalDays = rand.nextInt(60);
        Calendar cal = Calendar.getInstance();
        cal.setTime(rentalStart);
        cal.add(Calendar.DAY_OF_MONTH, rentalDays);
        return cal.getTime();
    }

    public static Date genRandDate( Date earliest, Date latest )
    {
        return new Date( ThreadLocalRandom.current().nextLong( latest.getTime(), earliest.getTime() ));
    }

    public static String dateToString( Date date )
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
