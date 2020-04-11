import java.util.*;

public class GenCar {

    //all attributes for generating the cars
    public Map<String, Integer> models = new HashMap<>();
    public Map<String, Integer> brands = new HashMap<>();
    public Map<String, Integer> fuelTypes = new HashMap<>();
    public String[] colors = { "Baby Blue", "GayRed", "Shit Brown", "Kinky Yellow", "Black Madness", "white", "green", "Black with flames", "Puke Green", "Dannebrog" };
    public Random rand = new Random();

    //Object for loading models brands and fuelTypes
    //the hashMaps contain a String (name) and Integer (price pr day increase)
    public GenCar ()
    {
        models.put("W.o.w - Worthless obstacle Wuhu", 50);
        models.put("Shitness 12", 70);
        models.put("Barely manageable", 80);
        models.put("Erh 50", 90);
        models.put("Medium comfort", 100);
        models.put("AVG - Above very gross", 120);
        models.put("Extreme shitness 500", 200);
        models.put("BTO - Better than others", 300);
        models.put("Extreme Turbo Death", 800);
        models.put("Unstoppable Like theRock", 1000);
        models.put("Speed is Love 1100", 1500);
        models.put("Over 9000", 2500);
        models.put("BestCarEU", 4000);

        brands.put("Fiat", 10);
        brands.put("Ford", 100);
        brands.put("Bentley", 7000);
        brands.put("Lamborgini", 15000);
        brands.put("SmartCar", 70);
        brands.put("Toyota", 90);
        brands.put("Porche", 200);
        brands.put("BMW", 120);

        fuelTypes.put( "Electric", 50 );
        fuelTypes.put( "Hybrid", 40 );
        fuelTypes.put( "Disel", 30 );
        fuelTypes.put( "OilLover", 10 );
    }

    //purely to create
    public String returnCarNoName ()
    {
        return returnCar("Unnamed");
    }

    //Generates all the info of a car in a string format made for an SQL Query update
    //Only works with the 'addCar' method in Queries.
    public String returnCar (String name)
    {
        String model = genModel();
        String brand = genBrand();
        String color = genColor();
        String plateNr = genPlateNr();
        Date regDate = GenDate.genCarRegDate();
        int kmDriven = genKmDriven();
        boolean isAvailable = true;
        int seats = 3 + rand.nextInt(4);
        String otherSpects = "Suck my pp, theres nothing more to say.";
        String fuelType = genFuelType();
        int pricePrDay = genPricePrDay( model, brand, kmDriven, seats, fuelType );

        return "'" + model + "', '" + brand + "', '" +  color + "', '" + plateNr + "', '" + GenDate.dateToString( regDate ) +
                "', '" + isAvailable + "', '" + kmDriven + "', '" + seats + "', '" + otherSpects +  "', '" + fuelType +  "', '" + pricePrDay + "'";
    }

    //Generates the prace pr day from the attributes of the car (Model, Brabd, kmDriven, Seats, fuelType)
    private int genPricePrDay(String model, String brand, int kmDriven, int seats, String fuelType)
    {
        double kmDrivenPrice = 0;
        if (kmDriven < 1000) {
            kmDrivenPrice = 50;
        } else if (kmDriven < 2500) {
            kmDrivenPrice = 35;
        } else {
            kmDrivenPrice = 15;
        }
        //3 $ pr seat in the car
        int seatPrice = seats * 3;
        //each brand model and fueltype has an int value connected that is the price.
        return models.get(model) + brands.get(brand) + fuelTypes.get(fuelType) + (int) kmDrivenPrice + seatPrice;
    }

    //returns a random fueltype from the fueltype hasmap.
    private String genFuelType()
    {
        Object[] crunchifyKeys = fuelTypes.keySet().toArray();
        Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
        return key.toString();
    }

    //random km driven from min 50km - 10.000km (we dont have old shitty cars ;) ;) ;) )
    private int genKmDriven()
    {
        return 50 + rand.nextInt(10000);
    }

    //plate nr is just 6 random numbers and 2 random upperCase letters. xxxxxx XX format
    private String genPlateNr()
    {
        String licenceNr = "";
        for (int i = 0; i < 6; i++ )
        {
            licenceNr += rand.nextInt(10);
        }
        char Char1 = (char) (65 + rand.nextInt(25));
        char Char2 = (char) (65 + rand.nextInt(25));
        licenceNr += " " + Char2;
        licenceNr += Char1;
        return licenceNr;
    }

    //returns a color from the color[]
    private String genColor()
    {
        return colors[ rand.nextInt( colors.length ) ];
    }

    //returns a random carBrand from the brands hasmap.
    private String genBrand()
    {
        Object[] crunchifyKeys = brands.keySet().toArray();
        Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
        return key.toString();
    }

    //returns a random Model from the models hasmap.
    private String genModel()
    {
        Object[] crunchifyKeys = models.keySet().toArray();
        Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
        return key.toString();
    }


}
