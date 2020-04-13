import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;

public class Neo4JInteraction
{

    // region Variables
    private static String uri;
    private static String user;
    private static String password;
    private static Driver driver;
    private static Session session;
    // endregion


    public static Result sendQuery(String query)
    {
        Result result = null;
        result = session.run(query);
        return result;
    }

    public static void openSession()
    {
        session = driver.session();
    }

    public static void closeSession()
    {
        session.close();
        driver.close();
    }

    // region Setters
    public static void setUri(String uri)
    {
        Neo4JInteraction.uri = uri;
    }

    public static void setUser(String user)
    {
        Neo4JInteraction.user = user;
    }

    public static void setPassword(String password)
    {
        Neo4JInteraction.password = password;
    }

    public static void setDriver(String uri, String user, String password)
    {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public static void setDriver()
    {
        driver = GraphDatabase.driver(Neo4JInteraction.uri, AuthTokens.basic(Neo4JInteraction.user, Neo4JInteraction.password));
    }
    // endregion
}
