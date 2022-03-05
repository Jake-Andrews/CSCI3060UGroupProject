package main;
import java.util.Scanner;

public class Main {
    /*
    Prevent user from issuing commands without being logged in
    Check the users rank before issuing certain commands
    Add functionality to commands
    */
    public static void main(String[] args) {
        //add a while loop, stops when user types exit or something. logout?


        //get the user to type login
        Scanner sc = new Scanner(System.in);
        String userInput = "";
        do {
            System.out.print("Welcome, please login to use the program. \nTo start type login:");
            userInput = sc.nextLine();
        }
        while (!userInput.equalsIgnoreCase("login"));

        //get the user's username
        userInput = "";
        do {
            System.out.print("Please enter your username:");
            userInput = sc.nextLine();
        }
        while (false);//sent to a method to check if the username is in the user accounts file, probably need a new class for this? filehandler class or something.


        Commandline test = new Commandline();
        //Commandline.getInput will return a string
        //Split the string based on spaces. The first word will determine which function 
        //Gets called in User (logout, etc...). Then the other words are used to complete the function
        //This removes all the commandline/userinput to its own class and cleans up the User class
        //All the validating of input, etc... will be done in Commandline, user only deals with sanitized input. 
        //The string will be split on spaces and the first string thrown to a switch statment (fn callCommand)
        String inputCommand = test.getInput();
        String[] commandsSplit = inputCommand.split("\\s+");

        for (String input : commandsSplit) {
            System.out.println("Testing: Command Recieved: " + input);
        }
        //[0] contains "logout" or "login", etc... Rest are the necessary inputs (rentID, username, etc...)
        //callCommand(commandsSplit[0], commandsSplit);
    }
}
