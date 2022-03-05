package main;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /*
    Prevent user from issuing commands without being logged in
    Check the users rank before issuing certain commands
    Add functionality to commands
    */
    public static void main(String[] args) {
        //gets user to type login, type username, then create user class that will handle the rest

        while(true) {

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
        Commandline command = new Commandline();
        do {
            System.out.print("Please enter a valid username:");
            userInput = sc.nextLine();
        }
        while (!command.isUser(userInput));//sent to a method to check if the username is in the user accounts file, probably need a new class for this? filehandler class or something.

        //create a user with the info given
        User user = new User(userInput, command.getUserType(userInput));

        //reads in the avaliable rental units file since the user has sucessfully logged in 
        try {
            Parser.readAvailableRentalsFile();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //let them choose a command
        user.getCommands();
    }

    }
}
