import java.sql.*;

public class DBInteraction
{
    //WHERE DO WE EVEN START
    private static String url;
    private static String schema;
    private static String userName;
    private static String password;
    private static Connection openSeSaMe = null;

    //region Methods
    public static ResultSet getData(String query) //for SELECT queries
    {
        try
        {
            Statement queryStatement = openSeSaMe.createStatement();
            queryStatement.execute("USE " + schema + ";"); //used to not have to declare schema every time
            ResultSet rs = queryStatement.executeQuery(query);
            return rs;
        } catch (SQLException e)
        {
            System.out.println("Shits fucked up yo!");
            e.printStackTrace();
            Log.warning("Failed to get data from the database", e);
            return null;
        }
    }

    public static int updateData(String query) //for CRUD queries
    {
        try
        {
            Statement queryStatement = openSeSaMe.createStatement();
            queryStatement.execute("USE " + schema + ";"); //used to not have to declare schema every time
            int rs = queryStatement.executeUpdate(query);
            return rs;
        } catch (SQLException e)
        {
            System.out.println("Shits fucked up yo!");
            e.printStackTrace();
            Log.warning("Failed to update the database", e);
            return -1;
        }
    }

    // start a connection to the database. Has to be run before the query commands can be used
    public static void openConnection()
    {
        try
        {
            openSeSaMe = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // close the connection. Run after the program has finished and is closing
    public static void closeConnection()
    {
        try
        {
            openSeSaMe.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //endregion

    //region Getters and Setters
    public static void setUrl(String url)
    {
        DBInteraction.url = "jdbc:mysql://" + url;
    }

    public static void setSchema(String schema)
    {
        DBInteraction.schema = schema;
    }

    public static void setUserName(String userName)
    {
        DBInteraction.userName = userName;
    }

    public static void setPassword(String password)
    {
        DBInteraction.password = password;
    }
    //endregion
}
