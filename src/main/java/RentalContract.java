import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RentalContract
{
    //region Attributes
    private String renterName;
    private String renterZip;
    private String renterCity;
    private String renterLicenceNr;
    private Date rentalStart;
    private Date rentalEnd;
    private String maxKm;
    private String totalKm;
    private String registrationPlateNr;
    //endregion

    //Constructora
    public RentalContract(String renterName, String renterZip, String renterCity, String renterLicenceNr, Date rentalStart, Date rentalEnd, String maxKm, String totalKm, String registrationPlateNr)
    {
        this.renterName = renterName;
        this.renterZip = renterZip;
        this.renterCity = renterCity;
        this.renterLicenceNr = renterLicenceNr;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.maxKm = maxKm;
        this.totalKm = totalKm;
        this.registrationPlateNr = registrationPlateNr;
    }

    public RentalContract() {}


    //region Getter and setter
    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getRenterZip() {
        return renterZip;
    }

    public void setRenterZip(String renterZip) {
        this.renterZip = renterZip;
    }

    public String getRenterCity() {
        return renterCity;
    }

    public void setRenterCity(String renterCity) {
        this.renterCity = renterCity;
    }

    public String getRenterLicenceNr() {
        return renterLicenceNr;
    }

    public void setRenterLicenceNr(String renterLicenceNr) {
        this.renterLicenceNr = renterLicenceNr;
    }

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

    public String getMaxKm() {
        return maxKm;
    }

    public void setMaxKm(String maxKm) {
        this.maxKm = maxKm;
    }

    public String getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(String totalKm) {
        this.totalKm = totalKm;
    }

    public String getRegistrationPlateNr() {
        return registrationPlateNr;
    }

    public void setRegistrationPlateNr(String registrationPlateNr) {
        this.registrationPlateNr = registrationPlateNr;
    }
    //endregion

    public String dateString (Date birthday) {
        DateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");
        return dtf.format( birthday );
    }

    //toString
    public String DBtoString()
    {
        return "'" + renterName + "', '" + renterZip + "', '" + renterCity + "', '" + renterLicenceNr + "', '" +
                dateString(rentalStart) + "', '" + dateString(rentalEnd) +  "', '" + maxKm + "', '" +
                totalKm + "', '" + registrationPlateNr + "'";
    }
}
