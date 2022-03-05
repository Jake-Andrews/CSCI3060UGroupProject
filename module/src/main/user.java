package main; 

public class User {
    public String userType;
    public String username;
    public String password;
    public boolean loggedIn; 
    public Unit[] unitsRented; 
    public String dailyTransactions; 
    public Commandline test = new Commandline();

    public User(String username, String userType){
        this.username = username; 
        this.userType = userType; 
        this.loggedIn = true; 
    }
    //When a user logs in, the method is run and accepts user input and 
    //invokes other methods in this class depending on the input until the user quits
    public void getCommands(){
        //Commandline.getInput will return a string
        //Split the string based on spaces. The first word will determine which function 
        //Gets called in User (logout, etc...). Then the other words are used to complete the function
        //This removes all the commandline/userinput to its own class and cleans up the User class
        //All the validating of input, etc... will be done in Commandline, user only deals with sanitized input. 
        //The string will be split on spaces and the first string thrown to a switch statment (fn callCommand)
        String inputCommand = test.getInput(this.userType);
        String[] commandsSplit = inputCommand.split("\\s+");

        for (String input : commandsSplit) {
            System.out.println("Testing: Command Recieved: " + input);
        }

        callCommand(commandsSplit[0], commandsSplit);
        //[0] contains "logout" or "login", etc... Rest are the necessary inputs (rentID, username, etc...)
        //callCommand(commandsSplit[0], commandsSplit);
    }

    public void login(String username) {

    }

    public void logout() {}
    public void create(String newUsername, String newUsertype){}
    public void delete(String username){
        
        System.out.println("Checking if the user exists... ");
        if (test.isUser(username)){
            System.out.println("The user: " + username + " exists in our system!");
        }

        System.out.println("Checking to make sure you don't delete your own account...");
        if (!username.equals(this.username)){
            System.out.println("Deleting user: " + username);
        }
        else {System.out.println("Error, you cannot delete yourself!");}
    }
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
                delete(inputGiven[1]);
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