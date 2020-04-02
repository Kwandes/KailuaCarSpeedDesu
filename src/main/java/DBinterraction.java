import java.sql.*;

public class DBinterraction {

    //WHERE DO WE EVEN START
    private static String url;
    private static String schema;
    private static String userName;
    private static String password;


    //region Methods
    public static ResultSet getData(String query) //for SELECT queries
    {
        try
        {
            Connection openSeSaMe = DriverManager.getConnection(url, userName, password);
            Statement queryStatement = openSeSaMe.createStatement();
            queryStatement.execute("USE "+ schema + ";"); //used to not have to declare schema every time
            ResultSet rs = queryStatement.executeQuery(query);
            openSeSaMe.close();
            return rs;
        } catch (SQLException e)
        {
            System.out.println("Shits fucked up yo!");
            e.printStackTrace();
            return null;
        }
    }

    public static int updateData(String query) //for CRUD queries
    {
        try
        {
            Connection openSeSaMe = DriverManager.getConnection(url, userName, password);
            Statement queryStatement = openSeSaMe.createStatement();
            queryStatement.execute("USE "+ schema + ";"); //used to not have to declare schema every time
            int rs = queryStatement.executeUpdate(query);
            openSeSaMe.close();
            return rs;
        } catch (SQLException e)
        {
            System.out.println("Shits fucked up yo!");
            e.printStackTrace();
            return -1;
        }
    }
    //endregion
    //region Getters and Setters
    public static void setUrl(String url)
    {
        DBinterraction.url = "jdbc:mysql://" + url;
    }

    public static void setSchema(String schema)
    {
        DBinterraction.schema = schema;
    }

    public static void setUserName(String userName)
    {
        DBinterraction.userName = userName;
    }

    public static void setPassword(String password)
    {
        DBinterraction.password = password;
    }
    //endregion
}
