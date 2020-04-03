import java.util.Date;

public class Car {

    private String brand;
    private String fuelType;
    private String registrationNr;
    private Date registrationYear;
    private int kmDriven;

    //Constructor
    public Car(String brand, String fuelType, String registrationNr, Date registrationYear, int kmDriven) {
        this.brand = brand;
        this.fuelType = fuelType;
        this.registrationNr = registrationNr;
        this.registrationYear = registrationYear;
        this.kmDriven = kmDriven;
    }

    public Car() {}

    //Getters n setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getRegistrationNr() {
        return registrationNr;
    }

    public void setRegistrationNr(String registrationNr) {
        this.registrationNr = registrationNr;
    }

    public Date getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(Date registrationYear) {
        this.registrationYear = registrationYear;
    }

    public int getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(int kmDriven) {
        this.kmDriven = kmDriven;
    }
}
