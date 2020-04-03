import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class PersonGen
{
    private ArrayList<String> femaleNames;
    private ArrayList<String> maleNames;
    private ArrayList<String> surnames;
    private ArrayList<String[]> countries;
    private ArrayList<String[]> cities;
    private Random rand = new Random();

    public PersonGen ()
    {
        femaleNames = loadStringFile("src/txtFiles/femaleNames.txt");
        maleNames = loadStringFile("src/txtFiles/maleNames.txt");
        surnames = loadStringFile("src/txtFiles/surnames.txt");
        countries = loadCountries();
        cities = loadCities();
    }

    //MAIN METHOD
    public Person returnPerson()
    {
        Person person = new Person();
        person.setSex( getSex() );
        person.setFirstName( getName( person.getSex() ) );
        person.setSurname( getSurname() );
        person.setCountry( "Denmark" );
        person.setCity( getCity() );
        person.setPhoneNr( getPhoneNr() );
        person.setBirthday( getBirthday() );
        person.setCpr( getCpr( person.getBirthday(), person.getSex() ) );
        return person;
    }

    public Person returnPersonCar( Person carPerson )
    {
        carPerson.setZip( getZip( carPerson.getCity() ) );
        carPerson.setDriversLicenceNumber( getDiversLicenceNumber() );
        carPerson.setLicenceDate( getLicenceDate( carPerson.getBirthday() ) );
        carPerson.setEmail( getEmail( carPerson.getFirstName(), carPerson.getSurname() ));
        return carPerson;
    }

    //SINGLE ATTRIBUTE RETURN METHODS
    public String getEmail (String name, String surname )
    {
        String email = "";
        if (rand.nextBoolean())
        {
            email += name + "_" + surname + "@";
        }
        else
        {
            email += surname + "_" + rand.nextInt(100) + "@";
        }
        if (rand.nextBoolean())
        {
            email += "hotmail";
        }
        else
        {
            if (rand.nextBoolean())
            {
                email += "yahoo";
            }
            else
            {
                if (rand.nextBoolean())
                {
                    email += "outlook";
                }
                else
                {
                    if (rand.nextBoolean())
                    {
                        email += "shitMail";
                    }
                    else
                    {
                        email += "gmail";  //Because fuck gmail....
                    }
                }
            }
        }
        if (rand.nextBoolean())
        {
            email += ".com";
        }
        else
        {
            email += ".dk";
        }
        System.out.println(email);
        return email;
    }

    public String getDiversLicenceNumber()
    {
        String licenceNr = "";
        for (int i = 0; i < 6; i++ )
        {
            licenceNr += rand.nextInt(10);
        }
        char Char1 = (char) (65 + rand.nextInt(25));
        char Char2 = (char) (65 + rand.nextInt(25));
        licenceNr += " " + Char2;
        licenceNr += Char1;
        return licenceNr;
    }

    //Gets a random date between the age of 18 and till now.
    //Ex. person was born in 01-01-2000. they turned 18 y/o 01-01-2018.
    //this will return a date between now (01-04-2020) and 01-01-2018.
    public Date getLicenceDate( Date birthday )
    {
        Calendar c = Calendar.getInstance();
        c.setTime(birthday);
        c.add(Calendar.DAY_OF_YEAR, 6570);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date earlyDate = c.getTime();
        Date licenceDate = getLicenceDateRand( earlyDate );
        return licenceDate;
    }

    public String getZip (String city)
    {
        for (String[] cityInfo : cities ) {
            if ( cityInfo[1].equals( city ))
            {
                return cityInfo[0].trim();
            }
        }
        return null;
    }

    public String getCity() { return cities.get( rand.nextInt( 350 ) )[1]; }

    public String getCountry()
    {
        return countries.get( rand.nextInt( 38 ) )[0];
    }

    public String getCpr(Date birthday, char sex)
    {
        String cprNr = "";
        int cprRest = 1000 + rand.nextInt( 9000 );
        DateFormat dtf = new SimpleDateFormat("ddMMyy");
        cprNr += dtf.format( birthday ) + "-";
        if (sex == 'M')
        {
            if (cprRest % 2 == 0) { cprRest -= 1; }
            cprNr += cprRest;
        }
        else
        {
            if (cprRest % 2 != 0) { cprRest -= 1; }
            cprNr += cprRest;
        }
        return cprNr;
    }

    public Date getBirthday()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            Date startInclusive = sdf.parse("01-01-1950");
            Date endExclusive = sdf.parse("01-01-2002");
            long startMillis = startInclusive.getTime();
            long endMillis = endExclusive.getTime();
            long randomMillisSinceEpoch = ThreadLocalRandom.current()
                    .nextLong(startMillis, endMillis);

            return new Date(randomMillisSinceEpoch);
        }
        catch (ParseException e) { e.printStackTrace(); }

        return null;
    }

    public Date getLicenceDateRand( Date earliest )
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            Date endExclusive = sdf.parse("01-01-2002");
            long startMillis = earliest.getTime();
            long endMillis = new Date().getTime();
            long randomMillisSinceEpoch = ThreadLocalRandom.current()
                    .nextLong(startMillis, endMillis);

            return new Date(randomMillisSinceEpoch);
        }
        catch (ParseException e) { e.printStackTrace(); }

        return null;
    }

    public String getPhoneNr()
    {
        String phoneNr = "+45 " + (1 + rand.nextInt(9));
        for (int i = 0; i < 7; i++ )
        {
            phoneNr += rand.nextInt(10);
        }
        return phoneNr;
    }

    public String getPhoneNrEU( String country )
    {
        String phoneNr = "";
        for (String[] countryName : countries)
        {
            if ( countryName[0].equals(country) )
            {
                phoneNr += countryName[2];
            }
        }
        phoneNr += " " + (1 + rand.nextInt(9));
        for (int i = 0; i < 7; i++ )
        {
            phoneNr += rand.nextInt(10);
        }
        return phoneNr;
    }

    public String getSurname()
    {
        return surnames.get( rand.nextInt( surnames.size() ) );
    }

    public String getName(char sex)
    {
        String name = "";
        int nameNr = rand.nextInt( maleNames.size() );

        if (sex == 'M') { name = maleNames.get( nameNr ); }

        else { name = femaleNames.get( nameNr ); }

        return name;
    }

    public char getSex()
    {
        char sex = 'M';
        if (rand.nextBoolean())
        {
            sex = 'F';
        }
        return sex;
    }

    //LOAD NAME FILES
    private ArrayList<String> loadStringFile(String file)
    {
        ArrayList<String> fileInfo = new ArrayList<>();
        Scanner fileReader = new Scanner("");
        try
        {
            fileReader = new Scanner( new File( readFile (file) ));
        }
        catch ( FileNotFoundException e ) { e.printStackTrace(); }

        while ( fileReader.hasNextLine() )  //load the file into an ArrayList
        {
            fileInfo.add( fileReader.nextLine() );
        }
        return fileInfo;
    }

    private ArrayList<String[]> loadCountries()
    {
        ArrayList<String[]> countries = new ArrayList<>();
        Scanner countryFile = new Scanner("");
        try
        {
            countryFile = new Scanner(new File(readFile ("src/txtFiles/europeLands.txt")));
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }

        while ( countryFile.hasNextLine() )
        {
            String[] country = { countryFile.next(), countryFile.next(), countryFile.next() };
            countries.add(country);
        }
        return countries;
    }

    private ArrayList<String[]> loadCities()
    {
        ArrayList<String[]> countries = new ArrayList<>();
        Scanner cityFile = new Scanner("");
        try
        {
            cityFile = new Scanner(new File( readFile ("src/txtFiles/citiesDK.txt")));
        }
        catch (FileNotFoundException e) { e.printStackTrace(); }

        while ( cityFile.hasNextLine() )
        {
            String[] city = cityFile.nextLine().split("-");
            city[0].trim();
            city[1].trim();
            countries.add(city);
        }
        return countries;
    }

    public String readFile (String fileName)
    {
        //absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
        return System.getProperty("user.dir") + File.separator + fileName;
    }
}