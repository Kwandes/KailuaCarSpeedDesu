import java.text.SimpleDateFormat;
import java.util.Date;

public class Main
{
    public static void main(String[] args)
    {
        Generate gen = new Generate();
        Costumer testPerson = gen.returnCostumer();
        System.out.println(testPerson.DBToString());
        System.out.println("I am speed");

        for (int i = 0; i < 1000; i++)
        {
            Date start = gen.genRentalDate();
            Date end = gen.genRentalEnd(start);
            RentalContract testContract = new RentalContract(start, end, 1, 1, 1000, 3000, 200);
            System.out.println(testContract.DBtoString());
        }
    }
}