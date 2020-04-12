/*
    A simple class which handles reading of the config file and setting up other classes
    Only one method is called externally, setupProgram, returns a boolean depending on whether or not the setup succeed
    Created by: Jan
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class Setup
{
    private static Properties config;

    // setup the program before you actually run it. Return true if setup was successful
    public static boolean setupProgram()
    {
        // only continue if the config has loaded properly

        if (setupConfig())
        {
            setupDB();
            setupNeo();
            setupSecurity();
            setupLog();
            return true;
        }
        return false;
    }

    // load the config file into the program. Return true if successful, false if not
    private static boolean setupConfig()
    {
        try
        {
            config = new Properties();
            config.load(new FileInputStream("config.properties"));
        } catch (FileNotFoundException e)
        {
            System.out.println("config.properties not found. Make sure to copy it to your project");
            return false;
        } catch (Exception e)
        {
            System.out.println("Something went wrong with reading the config.properties file");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // assign the secret key for encryption
    private static void setupSecurity()
    {
        Security.setKey(config.getProperty("secretKey"));
    }

    // setup the DB connection information and credentials
    private static void setupDB()
    {
        DBInteraction.setUrl(config.getProperty("url"));
        DBInteraction.setUserName(config.getProperty("username"));
        DBInteraction.setPassword(config.getProperty("password"));
        DBInteraction.setSchema(config.getProperty("schema"));
    }

    // setup the Neo4j connection information and credentials
    private static void setupNeo()
    {
        Neo4JInteraction.setUri(config.getProperty("uri"));
        Neo4JInteraction.setPassword(config.getProperty("passWord"));
        Neo4JInteraction.setUser(config.getProperty("user"));
        // setup the driver (uses previously set attributes)
        Neo4JInteraction.setDriver();
    }

    private static void setupLog()
    {
        Log.setUserName(config.getProperty("username"));
    }
}
