package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
CSCI3060UGroupProject
The program is run, by running the Main.java file. The code was written in vscode and the csci3060ugroupproject folder is open
in the code editor, so the current directory is the csci3060ugroupproject folder and the program will be able to find the .txt files. 
The program will ask for the user to type "login". Once they do, it will ask for their username. 
When a correct username is given, the program will ask for a command will accept the required commands and a help command
that lists the various commands. 

All of the input and output files are in the root directory, "availablerentalsfile.txt, dailytransactionsfile.txt, useraccounts.txt"
*/
public class Main {
        //one large while loop to constantly accept input and commands from the user.
        //gets user to type login, then type their username, validates that the usernameis in the useraccounts.txt file
        //then create user class and calls user.getcommands() to accept commands from the user until they type logout
    public static void main(String[] args) {
        //ot-bnb users.txt rental-units.txt transout.atf
        if (args.length <=2){
            System.out.println("Incorrect usage! Proper usage: Main availablerentalsfile.txt useraccounts.txt dailytransactionsfile.txt");
            System.exit(0);
        }

        String rentalsFile = new File(args[0]).getAbsolutePath();
        String userAccountsFile = new File(args[1]).getAbsolutePath();
        String transactionsFile = new File(args[2]).getAbsolutePath();
        //System.out.println(rentalsFile);

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
                Parser.readUserAccountsFile(rentalsFile, userAccountsFile, transactionsFile);
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
            User user = new User(userInput, command.getUserType(userInput), rentalsFile, userAccountsFile, transactionsFile);

            //reads in the avaliable rental units file since the user has sucessfully logged in 
            try {
                Parser.readAvailableRentalsFile(rentalsFile, userAccountsFile, transactionsFile);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //let them choose a command
            user.takeTransactions();
        }
    }
}
