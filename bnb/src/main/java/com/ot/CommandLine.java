package com.ot;

import java.util.ArrayList;

public class CommandLine {
    public static String[] validCommands = {"login", "logout", "create", "delete", "post", "search", "rent"};
    public CommandLine() {}

    //given a username, will return their usertype
    public String getUserType(String username) {
        //loop through the arraylist, find the username given, return their userType
        for (User user: Parser.users){
            //if the username was found in the arraylist, return usertype
            if (username.equals(user.username)) {
                return user.userType;
            } 
        }
        return "";    
    }

    //Checks if the user is an admin by reading in the useraccounts.txt file
    //Used by various commands (delete)
    public Boolean isAdmin(String username, String filename) {
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = Parser.readFileIntoArrayList(filename);

        //loop through the arraylist, find the username given, check if they are an admin
        for (String user: fileContents){
            String trimmed = user.trim().replaceAll("_{2,}", "_").trim();
            String[] line = trimmed.split("_");
            //System.out.println(line[0]);
            if (username.equals(line[0])) {
                //System.out.println(line[1]);
                if ("AA".equals(line[1])) {
                    return true; 
                } 
            } 
        }
        return false; 
    }

    //used in main to check that the username given is in the useraccounts.txt file
    public Boolean isUser(String username, String filename) {
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = Parser.readFileIntoArrayList(filename);

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

    public int getGenericIntegerInput(String repeatPhrase){ 
        int daysStayed = 0;
        boolean notANumber = false; 
        do {
            System.out.println(repeatPhrase);
            try
            {
                daysStayed = Integer.parseInt(Parser.sc.nextLine());
                notANumber = false; 
            }
            catch(NumberFormatException e)
            {
                notANumber = true; 
            }
        }while (notANumber);

        return daysStayed; 
    }
}