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
        if (rentalFlag.equals("t")){
            this.rentalFlag = true; 
        } else {this.rentalFlag = false; }
        this.numberOfNightsRemanining = numberOfNightsRemanining;
    }

    public String getRentID() {
        return this.rentID;
    }
    public void setRentID(String rentID) {
        this.rentID = rentID;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCity() {
        return this.city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public float getRentalPricePerNight() {
        return this.rentalPricePerNight;
    }
    public void setRentalPricePerNight(float rentalPricePerNight) {
        this.rentalPricePerNight = rentalPricePerNight;
    }
    public int getNumberOfBedrooms() {
        return this.numberOfBedrooms;
    }
    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }
    public boolean isRentalFlag() {
        return this.rentalFlag;
    }
    public boolean getRentalFlag() {
        return this.rentalFlag;
    }
    public void setRentalFlag(boolean rentalFlag) {
        this.rentalFlag = rentalFlag;
    }
    public int getNumberOfNightsRemanining() {
        return this.numberOfNightsRemanining;
    }
    public void setNumberOfNightsRemanining(int numberOfNightsRemanining) {
        this.numberOfNightsRemanining = numberOfNightsRemanining;
    }

    @Override
    public String toString() {
        return String.format("rentID: %s\r\nUsername: %s\r\nCity: %s\r\nnumberOfBedrooms: %d\r\nrentalPricePerNight: %.2f\r\nrentalFlag: %s\r\nnumberOfNightsRemaining: %d\r\n", 
        this.rentID, this.username, this.city, this.numberOfBedrooms, this.rentalPricePerNight, this.rentalFlag, this.numberOfNightsRemanining);
    }
}