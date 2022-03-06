package main;
import java.util.ArrayList;
import java.util.Scanner;
//class that handles all the command line dialogue with the user
//also handles sanitizing input
public class Commandline {
    //list of valid commands
    public static String[] validCommands = {"login", "logout", "create", "delete", "post", "search", "rent"};
    
    public Commandline(){}

    //given a username, will return their usertype
    public String getUserType(String username){
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = Parser.readFileIntoArrayList("useraccounts.txt");

        //loop through the arraylist, find the username given, return their userType
        for (String user: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = user.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] line = trimmed.split("_");
            
            //if the username was found in the file, return usertype
            if (username.equals(line[0])) {
                return line[1];
            } 
        }
        return "";    
    }

    //Checks if the user is an admin by reading in the useraccounts.txt file
    //Used by various commands (delete)
    public Boolean isAdmin(String username){
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = Parser.readFileIntoArrayList("useraccounts.txt");

        //fileContents.forEach(line->System.out.println(line.split("_")[0]));

        //loop through the arraylist, find the username given, check if they are an admin
        for (String user: fileContents){
            String[] line = user.split("_");
            if (username.equals(line[0])) {
                if ("AA".equals(line[1])) {
                    return true; 
                } 
            } 
        }
        return false; 
    }

    //used in main to check that the username given is in the useraccounts.txt file
    public Boolean isUser(String username){
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = Parser.readFileIntoArrayList("useraccounts.txt");

        //fileContents.forEach(line->System.out.println(line.split("_")[0]));

        //loop through the arraylist, see if the username given is a user
        for (String user: fileContents){
            String[] line = user.split("_");

            if (username.equals(line[0])) {
                System.out.println("You have been logged in!");
                return true; 
            } 
        }
        return false; 
    }

    //Got bored with the regex
    //To do: Make sure the command we are returning is in the validcommands list. 
    //currently login123 would be returned instead of cropping 123 off and returning login
    //Could also just reject any user input that doesn't match (case insensitive) a command.
    
    //This method is envoked from the User class
    //It is used to get the user to input a command (delete, login, etc..)
    //Once a command is given, call determinecommand method
    public String getInput(String userType) {
        //Get user input, while loop to make sure input is not empty. Give help commands if they want them. 
        System.out.println("Please enter a command!\nFor a list of commands type (help)!");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        if (userInput.matches("^.*help.*$")){
        //regex to match help, and checks for help as a substring
        //if (userInput.matches("(?i).*help.*")){
            printCommands();
            scanner.nextLine();
        };
        //while (userInput.isEmpty() && !(userInput.matches("^.*?(item1|item2|item3).*$"))){
        while (userInput.isEmpty() && !(userInput.matches("^.*?(login|logout|create|delete|post|search|rent).*$"))){
            System.out.println("Please input a valid command!");
            userInput = scanner.nextLine();
        }
        //once this is reached, a valid command has been entered
        //determine what the command was, get the req input, return this input
        //in the form of a space seperated list (userCommands)
        System.out.println("Command given: " + userInput);
        String usersCommands = determineCommand(userInput, userType);
        return usersCommands;
        }
    
    //used in getInput to print a list of commands
    public void printCommands(){
        System.out.println("Printing a list of valid commands:");
        for (String command: validCommands){
            System.out.println(command);
        }
        System.out.println("Please enter a command!");
    }

    //determine what command was entered by the user
    //going to assume for now that the input was valid
    //switch statement will trigger based on the command given  
    public String determineCommand(String commandGiven, String userType){
        String listOfCommands = "";
        switch (commandGiven) {
            //since this method is only envoked after a user has logged in from the User class
            //if a user issues the login command, they must already be logged in
            //ERROR is returned to notify the User class that they should accept another command
            case "login":
                listOfCommands = "ERROR";
                System.out.println("You are already logged in!");
                break;
            case "logout":
                listOfCommands = "logout";
                break;
            //check admin status AA
            case "create":
                if (userType.equals("AA")){
                  listOfCommands = getCreate();
                  break;
                } 
                else {
                    System.out.println("You must be an admin to issue this command!");
                    listOfCommands = "ERROR";
                    break;
                }
            case "delete":
                if (userType.equals("AA")){
                    listOfCommands = getDelete();
                    break;
                } 
                else {
                    System.out.println("You must be an admin to issue this command!");
                    listOfCommands = "ERROR";
                    break;
                }
            case "post":
                listOfCommands = getPost();
                break;
            case "search":
                listOfCommands = getSearch();
                break;
            case "rent":
                listOfCommands = getRent();
                break;
            default:
                System.out.println("Invalid Input!");
        }
        return listOfCommands; 
    }

    //The various methods below are called from the switch statement above and 
    //are used to get input from the user based on the command given
    //They all return space seperated strings
    public String getCreate(){
        String username = getGenericInput("Please enter a valid username:");
        String usertype = getGenericInput("Please enter a valid usertype:");
        return "create " + username + " " + usertype;
    }
    //username
    public String getDelete(){
        String username = getGenericInput("Please enter a valid username:");
        return "delete " + username;
    }
    //city, rentPrice, bedrooms
    public String getPost(){
        String city = getGenericInput("Please enter a valid city:");
        String rentPrice = getGenericInput("Please enter a valid rentPrice:");
        String bedrooms = getGenericInput("Please enter a valid bedrooms:");
        return "post " + city + " " + rentPrice + " " + bedrooms;
    }
    //city, rentprice, bedrooms
    public String getSearch(){
        String city = getGenericInput("Please enter a valid city:");
        String rentPrice = getGenericInput("Please enter a valid maximum rentPrice:");
        String bedrooms = getGenericInput("Please enter a valid minimum number of bedrooms:");
        return "search " + city + " " + rentPrice + " " + bedrooms;
    }
    //rentID, nights
    public String getRent(){
        String rentID = getGenericInput("Please enter a valid rentID:");
        String nights = getGenericInput("Please enter a valid number of nights:");
        return "rent " + rentID + " " + nights;
    }

    //Used to get input from a user
    //Prevents a user from entering nothing
    //in the next phase can add a variable that allows you to determine the length of the input
    //allowed. Maybe other stuff as well. Assumes the input is valid 
    public String getGenericInput(String repeat){
        String userInput = ""; 
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(repeat);
            userInput = sc.nextLine();
        }
        while (userInput.isEmpty());

        return userInput; 
    }
}
