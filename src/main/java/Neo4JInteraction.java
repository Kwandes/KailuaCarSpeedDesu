import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import static org.neo4j.driver.Values.parameters;

public class Neo4JInteraction {

    // region Variables
    private static String uri;
    private static String user;
    private static String password;
    private static Driver driver;
    // endregion


    public void close() throws Exception
    {
        driver.close();
    }

    public static void printGreeting( final String message )
    {
        try
        {
            Session session = driver.session();
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( "CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // region Setters
    public static void setUri(String uri) {
        Neo4JInteraction.uri = uri;
    }
    public static void setUser(String user) {
        Neo4JInteraction.user = user;
    }
    public static void setPassword(String password) {
        Neo4JInteraction.password = password;
    }
    public static void setDriver(String uri, String user, String password) {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ));
    }
    public static void setDriver() {
        driver = GraphDatabase.driver( Neo4JInteraction.uri, AuthTokens.basic( Neo4JInteraction.user, Neo4JInteraction.password ));
    }
    // endregion
}
