public class Main
{
    public static void main(String[] args)
    {
        PersonGen gen = new PersonGen();
        Person testPerson = gen.returnPersonCar( gen.returnPerson() );
        System.out.println(testPerson.DBPersonCar());
        System.out.println("I am speed");
    }
}