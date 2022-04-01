package com.ot;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        //both of the filenames entered by the user
        String rentalsFile = args[0];
        String userAccountsFile = args[1];
        //getting the .jar files location regardless of where it's run, important for for the bash scripts
        String jarPath = "";
        try {
            jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
        } catch (URISyntaxException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        //removing the .jar filename from the path
        String jarDirectory = (new File(jarPath)).getParentFile().getPath();
        //temp path, used to get parent directories
        Path tempPath = Paths.get(jarDirectory); 
        Path pathToSRC = tempPath.getParent();
        String trueRentalsFilePath = pathToSRC.toString()+File.separator+"src"+File.separator+"test"+File.separator+"java"+File.separator+"resources"+File.separator+rentalsFile;
        String trueuserAccountsFilePath = pathToSRC.toString()+File.separator+"src"+File.separator+"test"+File.separator+"java"+File.separator+"resources"+File.separator+userAccountsFile;

        String userInput = "";    
        while (!userInput.equalsIgnoreCase("quit")) {
            //get the user to type login
            do {
                System.out.print("Welcome, please login to use the program. \nTo exit type exit, To start type login: ");
                userInput = Parser.sc.nextLine();
                if (userInput.equalsIgnoreCase("exit")){
                    System.out.print("Thank you for using our program!");
                    Parser.writeToTransactionFile();
                    Parser.sc.close();
                    System.exit(0);
                }
            } while (!userInput.equalsIgnoreCase("login"));

            //reads in the useraccounts file
            try {
                Parser.readUserAccountsFile(trueRentalsFilePath, trueuserAccountsFilePath);
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            //get the user's username and validate it 
            userInput = "";
            CommandLine command = new CommandLine();
            do {
                System.out.print("\nPlease enter a valid username: ");
                userInput = Parser.sc.nextLine();
                if (userInput.equalsIgnoreCase("exit")){
                    System.out.print("Thank you for using our program!");
                    Parser.writeToTransactionFile();
                    System.exit(0);
                }
            } while (!command.isUser(userInput, trueuserAccountsFilePath)); //keep asking for a valid username while the username given is not a username in the useraccounts.txt file

            //create a user with the info given
            User user = new User(userInput, command.getUserType(userInput, trueuserAccountsFilePath), trueRentalsFilePath, trueuserAccountsFilePath);

            //reads in the avaliable rental units file since the user has sucessfully logged in 
            try {
                Parser.readAvailableRentalsFile(trueRentalsFilePath, trueuserAccountsFilePath);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //let them choose a command
            user.takeTransactions();
        }
    }
}
