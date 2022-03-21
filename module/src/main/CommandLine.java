package main;
import java.util.ArrayList;
import java.util.Scanner;
//class that handles all the command line dialogue with the user
//also handles sanitizing input
public class CommandLine {
    //list of valid commands
    public static String[] validCommands = {"login", "logout", "create", "delete", "post", "search", "rent"};
    public CommandLine() {}

    //given a username, will return their usertype
    public String getUserType(String username) {
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
    public Boolean isAdmin(String username) {
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
    public Boolean isUser(String username) {
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = Parser.readFileIntoArrayList("useraccounts.txt");

        //fileContents.forEach(line->System.out.println(line.split("_")[0]));

        //loop through the arraylist, see if the username given is a user
        for (String user: fileContents){
            String[] line = user.split("_");

            if (username.equals(line[0])) {
                System.out.println("\nYou have been logged in!");
                return true; 
            } 
        }
        return false; 
    }

     //Got bored with the regex
    //To do: Make sure the command we are returning is in the validcommands list. 
    //currently login123 would be returned instead of cropping 123 off and returning login
    //Could also just reject any user input that doesn't match (case insensitive) a command. 
    public String recieveTransaction() {
        //Get user input, while loop to make sure input is not empty. Give help commands if they want them. 
        System.out.print("\nPlease enter a command!\nFor a list of commands type 'help': ");
        String userInput = "";

        if (Parser.sc.hasNextLine()){
            userInput = Parser.sc.nextLine();
        }
        if (userInput.matches("^.*help.*$")){
        //regex to match help, and checks for help as a substring
        //if (userInput.matches("(?i).*help.*")){
            printCommands();
            Parser.sc.nextLine();
        };

        //take input from a user until user provides a valid command
        userInput.toLowerCase();

        while (userInput.isEmpty() && !(userInput.matches("^.*?(login|logout|create|delete|post|search|rent).*$"))){
            System.out.print("\nPlease input a valid command: ");
            userInput = Parser.sc.nextLine();
        }
        //once this is reached, a valid command has been entered
        //determine what the command was, get the req input, return this input
        //in the form of a space seperated list
        return userInput;
    }
    
    //'help' command- print out valid commands for the user to take
    public void printCommands(){
        System.out.println("\nPrinting a list of valid commands:");
        for (String command: validCommands){
            System.out.println(command);
        }
        System.out.print("\nPlease enter a command:");
    }

    //Used to get input from a user
    //Prevents a user from entering nothing
    //in the next phase can add a variable that allows you to determine the length of the input
    //allowed. Maybe other stuff as well. Assumes the input is valid 
    public String getGenericInput(String repeatPhrase){
        String userInput = ""; 

        do {
            System.out.println(repeatPhrase);
            if (Parser.sc.hasNextLine()){
                userInput = Parser.sc.nextLine();
            }
        }
        while (userInput.isEmpty());

        return userInput; 
    }
}
>>>>>>> 0db076c99f996bd92744f5b589e83e4df66e12db
