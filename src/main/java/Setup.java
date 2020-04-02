/*
    A simple class which handles reading of the config file and setting up other classes
    Only one method is called externally, setupProgram, returns a boolean depending on whether or not the setup succeed
    Created by: Jan
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class Setup {
    private static Properties config;

    // setup the program before you actually run it. Return true if setup was successful
    public static boolean setupProgram()
    {

        // only continue if the config has loaded properly

        if (setupConfig()) {
            setupDB();
            setupSecurity();
        }
        return true;
    }

    // load the config file into the program. Return true if successful, false if not
    private static boolean setupConfig()
    {
        try
        {
            config = new Properties();
            config.load(new FileInputStream("config.properties"));
        } catch (FileNotFoundException e) {
            System.out.println("config.properties not found. Make sure to copy it to your project");
            return false;
        } catch (Exception e) {
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
        DBInteraction.setUser(config.getProperty("username"));
        DBInteraction.setPasswd(config.getProperty("password"));
    }
}
