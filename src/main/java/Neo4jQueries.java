/*
    Methods for generation etc of Neo4j (Cypher) queries
 */

import java.util.List;

public class Neo4jQueries
{
    // region Create
    // Creates a Node with the Dealership label for all the specified locations.
    public static String createDealership (List<String> locations) {
        String query = "CREATE ";
        int i = 0;
        while ( i < locations.size()-1 )
        {
            query += "d" + i + ":Dealership {from: \"" + locations.get(i) + "\"}),";
            i++;
        }
        query += "d" + i + ":Dealership {from: \"" + locations.get(i) + "\"})";

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
