import java.util.*;

public class GenCar {

    public Map<String, Integer> models = new HashMap<>();
    public Map<String, Integer> brands = new HashMap<>();
    public Map<String, Integer> fuelTypes = new HashMap<>();
    public String[] colors = { "Baby Blue", "GayRed", "Shit Brown", "Kinky Yellow", "Black Madness", "white", "green", "Black with flames", "Puke Green", "Dannebrog" };
    public Random rand = new Random();

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

    public String returnCarNoName ()
    {
        return returnCar("Unnamed");
    }

    public String returnCar (String name)
    {
        String model = genModel();
        String brand = genBrand();
        String color = genColor();
        String plateNr = genPlateNr();
        Date regDate = GenDate.genCarRegDate();
        int kmDriven = genKmDriven();
        int seats = 3 + rand.nextInt(4);
        String otherSpects = "Suck my pp, theres nothing more to say.";
        String fuelType = genFuelType();
        int pricePrDay = genPricePrDay( model, brand, kmDriven, seats, fuelType );

        return "'" + name + "', '" + model + "', '" + brand + "', '" +  color + "', '" + plateNr + "', '" + GenDate.dateToString( regDate ) +
                "', '" + kmDriven + "', '" + seats + "', '" + otherSpects +  "', '" + fuelType +  "', '" + pricePrDay + "'";
    }

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
        int seatPrice = seats * 3;
        return models.get(model) + brands.get(brand) + fuelTypes.get(fuelType) + (int) kmDrivenPrice + seatPrice;
    }

    private String genFuelType()
    {
        Object[] crunchifyKeys = fuelTypes.keySet().toArray();
        Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
        return key.toString();
    }

    private int genKmDriven()
    {
        return 50 + rand.nextInt(10000);
    }

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

    private String genColor()
    {
        return colors[ rand.nextInt( colors.length ) ];
    }

    private String genBrand()
    {
        Object[] crunchifyKeys = brands.keySet().toArray();
        Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
        return key.toString();
    }

    private String genModel()
    {
        Object[] crunchifyKeys = models.keySet().toArray();
        Object key = crunchifyKeys[new Random().nextInt(crunchifyKeys.length)];
        return key.toString();
    }


}
