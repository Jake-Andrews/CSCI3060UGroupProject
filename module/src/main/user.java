package main; 

public class User {
    public String userType;
    public String username;
    public String password;
    public boolean loggedIn; 
    public Unit[] unitsRented; 
    public String dailyTransactions; 

    public User(String username, String userType){
        this.username = username; 
        this.userType = userType; 
        this.loggedIn = true; 
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

    public Unit search(String city, Float rentPrice, int bedrooms){
        Unit unit = new Unit(city, rentPrice, bedrooms);
        return unit;
    }

    public void rent(int rentID, int nights){}

    public String writeToTransactionFile(String transaction){return "";}

    public String callCommand(String mainCommand, String[] inputGiven){

        switch (mainCommand) {
            case "login":
                login("");
                break;
            case "logout":
                logout();
                break;
            case "create":
                create("","");
                break;
            case "delete":
                delete("");
                break;
            case "post":
                post("",0.0f,1);
                break;
            case "search":
                search("", 0.0f, 1);
                break;
            case "rent":
                rent(1,1);
                break;
            default:
                System.out.println("Invalid Input!");
        }
        return ""; 
    }
}