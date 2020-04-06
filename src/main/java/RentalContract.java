import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalContract
{
    //region Attributes
    private static int contractIdCounter = 0;
    private Date rentalStart;
    private Date rentalEnd;
    private int costumerId;
    private int salesmanId;
    private int contractId;
    private int maxKm;
    private int startKm;
    private int value;
    //endregion

    //Constructora


    public RentalContract(Date rentalStart, Date rentalEnd, int costumerId, int salesmanId, int maxKm, int startKm, int value) {
        this.contractId = ++contractIdCounter;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.costumerId = costumerId;
        this.salesmanId = salesmanId;
        this.maxKm = maxKm;
        this.startKm = startKm;
        this.value = value;
    }

    public RentalContract() {}

    //region Getter and setter

    public Date getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(Date rentalStart) {
        this.rentalStart = rentalStart;
    }

    public Date getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(Date rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public int getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(int costumerId) {
        this.costumerId = costumerId;
    }

    public int getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        this.salesmanId = salesmanId;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getMaxKm() {
        return maxKm;
    }

    public void setMaxKm(int maxKm) {
        this.maxKm = maxKm;
    }

    public int getStartKm() {
        return startKm;
    }

    public void setStartKm(int startKm) {
        this.startKm = startKm;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //endregion

    public String dateString (Date birthday) {
        DateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");
        return dtf.format( birthday );
    }

    //toString
    public String DBtoString()
    {
        return "'" + dateString(rentalStart) + "', '" + dateString(rentalEnd) + "', '" + costumerId + "', '" + salesmanId + "', '" +
                contractId +  "', '" + maxKm + "', '" + startKm + "', '" + value + "'";
    }
}
