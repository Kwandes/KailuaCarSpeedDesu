public class Main
{
    public static void main(String[] args)
    {
        Generate gen = new Generate();
        Person testPerson = gen.returnPersonCar( gen.returnPerson() );
        System.out.println(testPerson.DBToString());
        System.out.println("I am speed");
    }
}