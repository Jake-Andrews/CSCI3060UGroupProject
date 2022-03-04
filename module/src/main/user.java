package main;
import main.Unit;

public class User {
    public String userType;
    public String username;
    public String password;
    public boolean loggedIn; 
    public String dailyTransactions; 
    public static void main(String[] args) {
        System.out.println("test");
    }

    public void login(String username) {

    }

    public void logout() {}
    public void create(String newUsername, String newUsertype){}
    public void delete(String username){}
    public Unit post(String city, Float rentPrice, int bedrooms){
        Unit unit = new Unit(city, rentPrice, bedrooms);
        return unit; 
    }

}