package main;

//class used to respresent a renting unit
//holds the various data about a rental, override the toString for testing purposes and in User class in the search method
public class Renting {
    public String rentID; 
    public String username;
    public String city;  
    public int numberOfBedrooms; 
    public float rentalPricePerNight;
    public String rentalFlag;  
    public int numberOfNightsRemanining; 

    public Renting(String rentID, String username, String city, int numberOfBedrooms, float rentalPricePerNight, String rentalFlag, int numberOfNightsRemanining){
        this.rentID = rentID; 
        this.username = username;
        this.city = city;  
        this.numberOfBedrooms = numberOfBedrooms; 
        this.rentalPricePerNight = rentalPricePerNight; 
        this.rentalFlag = rentalFlag;
        this.numberOfNightsRemanining = numberOfNightsRemanining;
    }

    @Override
    public String toString() {
        return String.format("rentID: %s\r\nUsername: %s\r\nCity: %s\r\nnumberOfBedrooms: %d\r\nrentalPricePerNight: %.2f\r\nrentalFlag: %s\r\nnumberOfNightsRemaining: %d\r\n", 
        this.rentID, this.username, this.city, this.numberOfBedrooms, this.rentalPricePerNight, this.rentalFlag, this.numberOfNightsRemanining);
}
}