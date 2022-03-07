package main;

//class used to respresent a unit
//holds the various data about a rental, override the toString for testing purposes and in User class in the search method
public class Unit {
    private String rentID; 
    private String username;
    private String city;
    private float rentalPricePerNight;
    private int numberOfBedrooms; 
    private boolean rentalFlag;  
    private int numberOfNightsRemanining; 

    public Unit(String rentID, String username, String city, int numberOfBedrooms, float rentalPricePerNight, String rentalFlag, int numberOfNightsRemanining) {
        this.rentID = rentID; 
        this.username = username;
        this.city = city;
        this.numberOfBedrooms = numberOfBedrooms;
        this.rentalPricePerNight = rentalPricePerNight; 
        this.rentalFlag = Boolean.valueOf(rentalFlag);
        this.numberOfNightsRemanining = numberOfNightsRemanining;
    }
    // Getters
    public String getRentID()       { return this.rentID; }
    public String getUserName()     { return this.username; }
    public String getCity()         { return this.city; }
    public int getNumBedrooms()     { return this.numberOfBedrooms; }
    public float getRentalPrice()   { return this.rentalPricePerNight; }
    public boolean getRentalFlag()  { return this.rentalFlag; }
    public int getNumNights()       { return this.numberOfNightsRemanining; }

    @Override
    public String toString() {
        return String.format("rentID: %s\r\nUsername: %s\r\nCity: %s\r\nnumberOfBedrooms: %d\r\nrentalPricePerNight: %.2f\r\nrentalFlag: %s\r\nnumberOfNightsRemaining: %d\r\n", 
        this.rentID, this.username, this.city, this.numberOfBedrooms, this.rentalPricePerNight, this.rentalFlag, this.numberOfNightsRemanining);
    }
}
