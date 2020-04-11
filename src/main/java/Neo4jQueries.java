/*
    Methods for generation etc of Neo4j (Cypher) queries
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.spi.DateFormatProvider;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Neo4jQueries
{
    // region Create

    // Creates a Node with the Dealership label for all the specified locations.
    public static String createDealership (List<String> locations)
    {
        String query = "CREATE ";
        int i = 0;
        while ( i < locations.size()-1 )
        {
            query += "(d" + i + ":Dealership {from: \"" + locations.get(i) + "\"}),";
            i++;
        }
        query += "(d" + i + ":Dealership {from: \"" + locations.get(i) + "\"})";

        return query;
    }

    // Creates a Node with the Salesman label for all given names & assigns each Salesman to the desired Dealership.
    // Note : List<String> names - contains a string of both the first & last names. The first names
    // will be separated from the last names by a semicolon (;)
    public static String addSalesmanToDealership (List<String> names, String dealership)
    {
        Random r = new Random();
        String[] name;
        String query = "MATCH (d:Dealership) WHERE d.from = \"" + dealership + "\"\n" +
                       "CREATE ";
        int i = 0;
        while ( i < names.size()-1 )
        {
            name = names.get(i).split(";", 2);
            query += "(s" + i + ":Salesman {first_name: \"" + name[0] + "\", last_name \"" + name[1] + "\"})," +
                     "(s" + i + ")-[:WORKS_AT {sales_made: " + r.nextInt(10) + "}]->(d),";
            i++;
        }
        name = names.get(i).split(";", 2);
        query += "(s" + i + ":Salesman {first_name: \"" + name[0] + "\", last_name \"" + name[1] + "\"})," +
                "(s" + i + ")-[:WORKS_AT {sales_made: " + r.nextInt(10) + "}]->(d)";

        return query;
    }

    // Creates a Node with the Car label for all given car details & assigns each Car to the desired Dealership.
    // Note : List<String> carDetails - contains a string of all the car details, separated by a semicolon (;)
    public static String addCarToDealership (List<String> carDetails, String dealership)
    {
        Random r = new Random();
        String[] car;
        String query = "MATCH (d:Dealership) WHERE d.from = \"" + dealership + "\"\n" +
                "CREATE ";
        int i = 0;
        while ( i < carDetails.size()-1 )
        {
            car = carDetails.get(i).split(";", 3);
            query += "(c" + i + ":Car {brand: \"" + car[0] + "\", model \"" + car[1] + "\", year: \"" + car[2] + "\"})," +
                    "(d)-[:HAS {since: " + (r.nextInt(20) + 2000) + "}]->(c" + i + "),";
            i++;
        }
        car = carDetails.get(i).split(";", 3);
        query += "(c" + i + ":Car {brand: \"" + car[0] + "\", model \"" + car[1] + "\", year: \"" + car[2] + "\"})," +
                "(d)-[:HAS {since: " + (car[2] + r.nextInt(5)) + "}]->(c" + i + ")";

        return query;
    }

    // Creates a Node with the Customer label for all given names & assigns each Customer a random location from the list provided.
    // Note : List<String> names - contains a string of both the first & last names. The first names
    // will be separated from the last names by a semicolon (;)
    public static String createCustomer (List<String> names, List<String> locations)
    {
        Random r = new Random();
        String[] name;
        String query = "CREATE ";
        int i = 0;
        while ( i < names.size()-1 )
        {
            name = names.get(i).split(";", 2);
            query += "(c" + i + ":Customer {first_name: \"" + name[0] + "\", last_name \"" + name[1] + "\", from: \"" + locations.get(r.nextInt(locations.size())) + "\"}),";
            i++;
        }
        name = names.get(i).split(";", 2);
        query += "(c" + i + ":Customer {first_name: \"" + name[0] + "\", last_name \"" + name[1] + "\", from: \"" + locations.get(r.nextInt(locations.size())) + "\"}),";

        return query;
    }

    // Creates a Node with the Contract label with the given information.
    public static String createContract (int value, String carDetails, String customerName, String salesmanName)
    {
        String[] customer = customerName.split(";", 2);
        String[] salesman = salesmanName.split(";", 2);
        String[] car = carDetails.split(";", 3);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String query = "MATCH (cus:Customer) WHERE cus.first_name = \"" + customer[0] + "\" AND cus.last_name = \"" + customer[1] + "\"\n" +
                       "MATCH (sal:Salesman) WHERE sal.first_name = \"" + salesman[0] + "\" AND sal.last_name = \"" + salesman[1] + "\"\n" +
                       "MATCH (car:Car) car.brand = \"" + car[0] + "\" AND car.model = \"" + car[1] + "\" AND car.year = \"" + car[2] + "\"\n" +
                       "CREATE (con:Contract {date_signed: \"" + dateFormat.format(date) + "\", value: \"" + value + "\"})," +
                       "(cus)-[:SIGNED]->(con)," +
                       "(sal)-[:SEALED]->(con)," +
                       "(car)-[:IS_RENTED]->(con)";

        return query;
    }

    // endregion

    // region Read
    // endregion

    // region Update
    // endregion

    // region Delete
    // endregion

    public static String populateDB()
    {
        String query = "";
        String brandName = "Testing";
        String model = "Yeeter";
        String color = "RGB";

        query = "CREATE (b1:Brand { name:'" + brandName + "'})," +
                "(c1:Car {name:'" + model + "', model:'" + model + "', color:'" + color + "'})" +
                "-[:BELONGS_TO]->(b1)";

        return query;
    }


    public static String populateDB(String brand, int amount)
    {
        String query = "";
        String model = "Yeeter";
        String color = "RGB";

        query = "CREATE (b1:Brand { name:'" + brand + "'}),";
        for(int i = 1; i <= amount; i++)
        {
            query += "(c" + i + ":Car {name:'" + model + "', model:'" + model + "', color:'" + color + "'})" +
                            "-[:BELONGS_TO]->(b1),";
        }
        query = query.replaceAll(".$", ""); // remove an extra ',' at the end

        return query;
    }
}
