package main;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
        //one large while loop to constantly accept input and commands from the user.
        //gets user to type login, then type their username, validates that the usernameis in the useraccounts.txt file
        //then create user class and calls user.getcommands() to accept commands from the user until they type logout
    public static void main(String[] args) {
        while (true) {
            //get the user to type login
            Scanner scanner = new Scanner(System.in);
            String userInput = "";
            do {
                System.out.print("Welcome, please login to use the program. \nTo start type login: ");
                userInput = scanner.nextLine();
            } while (!userInput.equalsIgnoreCase("login"));

            //reads in the useraccounts file
            try {
                Parser.readUserAccountsFile();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            //get the user's username and validate it 
            userInput = "";
            CommandLine command = new CommandLine();
            do {
                System.out.print("Please enter a valid username: ");
                userInput = scanner.nextLine();
            } while (!command.isUser(userInput)); //keep asking for a valid username while the username given is not a username in the useraccounts.txt file
        

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
            user.takeTransactions();
            scanner.close();
        }
    }
}
