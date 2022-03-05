package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        String inputCommand = "";
        while(!inputCommand.equals("logout")) {
            //If the user tries to delete but isn't an admin, ask for another command
            inputCommand = test.getInput(this.userType);

            if (inputCommand.equals("ERROR")) {
                continue; 
            }
            else if (inputCommand.equals("logout")){
                break;
            }

            String[] commandsSplit = inputCommand.split("\\s+");

            for (String input : commandsSplit) {
                System.out.println("Testing: Command Recieved: " + input);
            }

            callCommand(commandsSplit[0], commandsSplit);
            //[0] contains "logout" or "login", etc... Rest are the necessary inputs (rentID, username, etc...)
            //callCommand(commandsSplit[0], commandsSplit);
        }
        System.out.println("You have been successfully logged out!"); 
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

    //creates a temp file, if the username is found, dont write to temp file
    //Once done, delete useraccounts.txt and rename temp file to useraccounts.txt
    public void deleteFromFile(String usernameToDelete) throws IOException {
        File inputFile = new File("useraccounts.txt");
        File tempFile = new File("tempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            String[] line = trimmedLine.split("_");
            //if the username equals the username given, don't write to temp file
            if(!line[0].equals(usernameToDelete)){
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }
        writer.close(); 
        reader.close(); 
        inputFile.delete();
        tempFile.renameTo(inputFile);
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