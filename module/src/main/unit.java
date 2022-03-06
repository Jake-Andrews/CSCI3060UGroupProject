package main;

public class Unit {
    private String city; 
    private Float rentPrice; 
    private int bedrooms; 
    private Boolean rentFlag; 

    public Unit(String city, Float rentPrice, int bedrooms){
        this.city = city; 
        this.rentPrice = rentPrice; 
        this.bedrooms = bedrooms; 
    }

    public void setCity(String city){
        this.city = city; 
    }
    public String getCity(){
        return city;
    }
    public void setRentprice(Float rentPrice){
        this.rentPrice = rentPrice; 
    }
    public Float getRentPrice(){
        return rentPrice;
    }
    public void setBedrooms(int bedrooms){
        this.bedrooms = bedrooms;
    }
    public int getBedrooms(){
        return bedrooms; 
    }
}
