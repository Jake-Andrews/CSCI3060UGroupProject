package main;
import java.util.Scanner;
//class that handles all the command line dialogue with the user
//handles sanitizing input
public class Commandline {
    public static String[] validCommands = {"login", "logout", "create", "delete", "post", "search", "rent"};
    
    public Commandline(){}

    //Got bored with the regex
    //To do: Make sure the command we are returning is in the validcommands list. 
    //currently login123 would be returned instead of cropping 123 off and returning login
    //Could also just reject any user input that doesn't match (case insensitive) a command. 
    public String getInput() {
        //Get user input, while loop to make sure input is not empty. Give help commands if they want them. 
        System.out.println("Please enter a command!\nFor a list of commands type (help)!");
        try (Scanner scanner = new Scanner(System.in)) {
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
            //in the form of a space seperated list
            String usersCommands = determineCommand(userInput);
            return usersCommands;
        }
        }
    
    public void printCommands(){
        System.out.println("Printing a list of valid commands:");
        for (String command: validCommands){
            System.out.println(command);
        }
        System.out.println("Please enter a command!");
    }

    public String getCommand(String command){
        return "";
    }
    //determine what command was entered by the user
    //going to assume for now that the input was valid 
    public String determineCommand(String commandGiven){
        String listOfCommands = "";
        switch (commandGiven) {
            case "login":
                listOfCommands = getLogin();
                break;
            case "logout":
                listOfCommands = "logout";
                break;
            case "create":
                listOfCommands = getCreate();
                break;
            case "delete":
                listOfCommands = getDelete();
                break;
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
    //username
    public String getLogin(){
        String username = getGenericInput("Please enter a valid username:");
        return "login " + username;
    }
    //username, usertype
    public String getCreate(){
        String username = getGenericInput("Please enter a valid username:");
        String usertype = getGenericInput("Please enter a valid usertype");
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
        String rentPrice = getGenericInput("Please enter a valid rentPrice:");
        String bedrooms = getGenericInput("Please enter a valid bedrooms:");
        return "search " + city + " " + rentPrice + " " + bedrooms;
    }
    //rentID, nights
    public String getRent(){
        String rentID = getGenericInput("Please enter a valid rentID:");
        String nights = getGenericInput("Please enter a valid nights");
        return "rent " + rentID + " " + nights;
    }

    //Used to get input from a user
    //Prevents a user from entering nothing
    //in the next phase can add a variable that allows you to determine the length of the input
    //allowed. Maybe other stuff as well. Assumes the input is valid currently as per proj req
    public String getGenericInput(String repeat){
        String userInput = ""; 
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(repeat);
            userInput = sc.nextLine();
        }
        while (userInput.isEmpty());
        //System.out.println();

        return userInput; 
    }
}
