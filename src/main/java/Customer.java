import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer
{
    //region ATTRIBUTES
    private String firstName;
    private String surname;
    private String phoneNr;
    private char sex;
    private Date birthday;
    private String cpr;
    private String country;
    private String city;
    private String address;
    private String zip;
    private String email;
    private String driversLicenceNumber;
    private Date licenceDate;
    //endregion

    //CONSTRUCTORS
    public Customer() {}

    public Customer(String firstName, String surname, String phoneNr, char sex, Date birthday, String cpr, String address, String city) {
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNr = phoneNr;
        this.sex = sex;
        this.birthday = birthday;
        this.cpr = cpr;
        this.address = address;
        this.country = "Denmark";
        this.city = city;
    }

    public Customer(String firstName, String surname, String phoneNr, String cpr, String city, String zip, String email, String driversLicenceNumber, Date licenceDate) {
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNr = phoneNr;
        this.cpr = cpr;
        this.country = "Denmark";
        this.city = city;
        this.zip = zip;
        this.email = email;
        this.driversLicenceNumber = driversLicenceNumber;
        this.licenceDate = licenceDate;
    }

    //region GETTERS N SETTERS
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriversLicenceNumber() {
        return driversLicenceNumber;
    }

    public void setDriversLicenceNumber(String driversLicenceNumber) {
        this.driversLicenceNumber = driversLicenceNumber;
    }

    public Date getLicenceDate() {
        return licenceDate;
    }

    public void setLicenceDate(Date licenceDate) {
        this.licenceDate = licenceDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCpr() {
        return cpr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    //endregion

    //STRING FORMATTING
    public String dateString (Date birthday) {
        DateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");
        return dtf.format( birthday );
    }

    //TO STRINGS
    @Override
    public String toString() {
        return firstName + " " + surname + " " + phoneNr + " " + sex + " " + birthday + " " + cpr;
    }

    public String DBToString() {
        return  "'" + firstName + "', '" + surname + "', '" + cpr + "', '" +  zip + "', '" + city + "', '" + address + "', '" + phoneNr + "', '" +
                email + "', '" + driversLicenceNumber +  "', '" + dateString(licenceDate) + "'";
    }

    public void toStringPrint() {
        System.out.printf("\t%-30s |   %-15s \n\t%-30s |   %-15s \n\t%-30s |   %-15s\n",
                "Name     : " + firstName + " " + surname , "Phone Nr : " +  phoneNr,
                "City     :" + city, "Country  : " + country,
                "Birthday : " + dateString( birthday ), "Cpr Nr   : " + cpr );
    }
}