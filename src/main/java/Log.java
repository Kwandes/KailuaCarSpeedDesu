/*
    Simple logging system based off the JUI.
    Contains multiple versions of the same logging method, with slightly different body depending on the log level
    All logs are sent to the database (therefore, internet connection is required)
 */

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Log
{
    // Define basic logging levels
    public static enum Level
    {
        TRACE,
        INFO,
        WARNING
    }

    private static String userName = "Bob";
    // threadpools are used for asynchronous usage of the database. They help with performance and speed of the program
    private static ExecutorService threadpool = Executors.newCachedThreadPool();

    // All logging methods follow the same system
    public static void log(Level level, String message)
    {
        // Add a given task to the threadpool. The task is sending an insert query to the database.
        // The insert query contains all the relevant log information
        threadpool.submit(() ->
                DBInteraction.updateData("INSERT INTO kailua_car_rental.program_log(log_level, log_message, log_time, user_name) VALUES ('" +
                        level.toString() + "', '" + message.replace("'", "\"") + "', '" + getDateTime() + "', '" + userName + "');"));
    }

    public static void log(Level level, String message, Exception e)
    {
        threadpool.submit(() ->
                DBInteraction.updateData("INSERT INTO kailua_car_rental.program_log(log_level, log_message, log_exception, log_time, user_name) VALUES ('" +
                        level + "', '" + message.replace("'", "\"") + "', '" + e + "', '" + getDateTime() + "', '" + userName + "');"));
    }

    public static void trace(String message)
    {
        threadpool.submit(() ->
                DBInteraction.updateData("INSERT INTO kailua_car_rental.program_log(log_level, log_message, log_time, user_name) VALUES ('" +
                        Level.TRACE + "', '" + message.replace("'", "\"") + "', '" + getDateTime() + "', '" + userName + "');"));
    }

    public static void info(String message)
    {
        threadpool.submit(() ->
                DBInteraction.updateData("INSERT INTO kailua_car_rental.program_log(log_level, log_message, log_time, user_name) VALUES ('" +
                        Level.INFO + "', '" + message.replace("'", "\"") + "', '" + getDateTime() + "', '" + userName + "');"));
    }

    public static void warning(String message)
    {
        threadpool.submit(() ->
                DBInteraction.updateData("INSERT INTO kailua_car_rental.program_log(log_level, log_message, log_time, user_name) VALUES ('" +
                        Level.WARNING + "', '" + message.replace("'", "\"") + "', '" + getDateTime() + "', '" + userName + "');"));
    }

    public static void warning(String message, Exception e)
    {
        threadpool.submit(() ->
                DBInteraction.updateData("INSERT INTO kailua_car_rental.program_log(log_level, log_message, log_exception, log_time, user_name) VALUES ('" +
                        Level.WARNING + "', '" + message.replace("'", "\"") + "', '" + e.toString().replace("'", "\"") + "', '" + getDateTime() + "', '" + userName + "');"));
    }

    // get current date and time, with milliseconds up to the 6th digit
    private static String getDateTime()
    {
        return LocalDateTime.now().toString().replace("T", " ");
    }

    public static void setUserName(String userName)
    {
        Log.userName = userName;
    }
}
