/*
    This code is taking care about user typing down here you can find different methods for user console interaction
    Created by: Itai
 */

import java.util.Scanner;

public class ScannerReader {

    public static String scannerAll ()
    {
        Scanner console = new Scanner(System.in);
        return console.next();
    }

    public static String scannerIntAsString ()
    {
        Scanner console = new Scanner(System.in);
        String keystroke = console.next();

        while (keystroke.matches("^[a-zA-Z]*$")) {
            System.out.println("This doesn't look right, try again only using numbers :)");
            keystroke = console.next();
        }

        return keystroke;
    }

    public static String scannerEMail ()
    {
        Scanner console = new Scanner(System.in);
        String keystroke = console.next();
        while (!keystroke.matches("^(.+)@(.+)$")) {
            System.out.println("This doesn't look right, try again to write the email address :)");
        }
        return keystroke;
    }

    public static String scannerIntAsString (int size)
    {
        Scanner console = new Scanner(System.in);
        String keystroke = console.next();

        while ((keystroke.matches("^[a-zA-Z]*$")) || (keystroke.length() != size)) {
            System.out.println("This doesn't look right, try again only using numbers :)");
            keystroke = console.next();
        }

        return keystroke;
    }

    //Used when needing String input
    public static String scannerWords ()
    {
        Scanner console = new Scanner(System.in);
        String keystroke = console.next();

        while (!keystroke.matches("^[a-zA-Z]*$")) {
            System.out.println("This doesn't look right, try again only using letters :)");
            keystroke = console.next();
        }

        return keystroke;
    }

    public static double scannerDouble ()
    {
        Scanner console = new Scanner(System.in);
        double num = 0.0;
        boolean valid = true;

        do {
            try {
                num = console.nextDouble();
                valid = false;
            } catch (Exception e) {
                console.next();
                console.nextLine();
                System.out.println("You did not enter a number");
            }
        } while (valid);

        return num;
    }

    public static int scannerInt (int min, int max)
    {
        Scanner console = new Scanner(System.in);
        int num = 0;
        do {
            try {
                num = console.nextInt();
                if ( num >= min && num <= max){
                    return num;
                }
                System.out.println("your type is out of range (minimum:"+ min+ " / maximum:"+max+" )");
            } catch (Exception e) {
                console.next();
                console.nextLine();
                System.out.println("You did not enter a number ");
            }
        } while (true);
    }

    public static int scannerInt ()
    { //Used when needing int input
        Scanner console = new Scanner(System.in);
        int num = 0;
        boolean valid = true;

        do {
            try {
                num = console.nextInt();
                valid = false;
            } catch (Exception e) {
                console.next();
                console.nextLine();
                System.out.println("You did not enter a number");
            }
        } while (valid);

        return num;
    }

    //Used to make number into boolean
    public static boolean scannerBoolean (int number)
    {
        while (number > 2 || number < 1) {
            System.out.println("Please type 1 for YES / Type 2 for NO ");
            number = scannerInt();
        }
        if (number == 1) {
            return true;
        } else {
            return false;
        }
    }
}
