package main;

public class unit {
    public String city; 
    public Float rentPrice; 
    public int bedrooms; 
    public Boolean rentFlag; 

    public void setCity(String city){}
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
