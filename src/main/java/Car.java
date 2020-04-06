import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Car
{
    //region Attributes
    private int carId;
    private String model;
    private String brand;
    private String color;
    private String plateNr;
    private Date dateRegistered;
    private int kmDriven;
    private boolean available;
    private int seats;
    private String otherSpec;
    private int contractId;
    private String fuelType;
    private String group;
    //endregion

    //Constructor


    public Car() {}

    //region Getters n setters

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlateNr() {
        return plateNr;
    }

    public void setPlateNr(String plateNr) {
        this.plateNr = plateNr;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public int getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(int kmDriven) {
        this.kmDriven = kmDriven;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getOtherSpec() {
        return otherSpec;
    }

    public void setOtherSpec(String otherSpec) {
        this.otherSpec = otherSpec;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    //endregion

    public String dateString (Date birthday) {
        DateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");
        return dtf.format( birthday );
    }

    public String DBtoString()
    {
        return "'" + brand + "', '" + fuelType + "', '" + plateNr + "', '" + dateString (dateRegistered) + "', '" + kmDriven + "'";
    }
}
