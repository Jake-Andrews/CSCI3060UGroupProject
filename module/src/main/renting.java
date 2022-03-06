package main;

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
        return String.format("rentID: %s\r\nUsername: %s\r\nCity: %s\r\nnumberOfBedrooms: %d\r\nrentalPricePerNight: %f\r\nrentalFlag: %s\r\numberOfNightsRemaining: %d\r\n", 
        this.rentID, this.username, this.city, this.numberOfBedrooms, this.rentalPricePerNight, this.rentalFlag, this.numberOfNightsRemanining);
    }
}