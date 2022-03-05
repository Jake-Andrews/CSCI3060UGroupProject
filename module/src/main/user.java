package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

    //The newusername and usertype havent been validated. username could be more than 15 char and usetype could be some random chars
    public void create(String newUsername, String newUsertype){
        System.out.println("Create the user: " + newUsername + " with a usertype of: " + newUsertype);
        writeToAccountsFile(newUsername, newUsertype);
    }
    public void delete(String username){
        
        System.out.println("Checking if the user exists... ");
        if (test.isUser(username)){
            System.out.println("The user: " + username + " exists in our system!");
        }

        System.out.println("Checking to make sure you don't delete your own account...");
        if (!username.equals(this.username)){
            System.out.println("Deleting user: " + username);
            try {
                deleteFromFile(username);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        else {System.out.println("Error, you cannot delete yourself!");}


    }
    public void writeToAccountsFile(String newUsername, String newUserType){
        //need to format the username and usertype properly
        //string needs to be 15 units long, subtract 2 for usertype.
        //Therefore, take length of username and subtract from 13 for the number of _ needed
        String toWriteToFile = newUsername;
        int underscoresNeeded = 13 - newUsername.length();
        
        for (int i = 0; i < underscoresNeeded; i ++){
            toWriteToFile = toWriteToFile + "_";
        }

        toWriteToFile = toWriteToFile + newUserType;

        try(FileWriter fw = new FileWriter("useraccounts.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {   
            out.println();
            out.print(toWriteToFile);
        } catch (IOException e) {
        //exception handling left as an exercise for the reader
        }
    }


    //creates a temp file, if the username is found, dont write to temp file
    //Once done, delete useraccounts.txt and rename temp file to useraccounts.txt
    public void deleteFromFile(String usernameToDelete) throws IOException {
        File inputFile = new File("useraccounts.txt");
        File tempFile = new File("tempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine = reader.readLine();

        while(currentLine != null) {
            //remove potential whitespace
            String trimmedLine = currentLine.trim();
            //split string based on _ 
            String[] line = trimmedLine.split("_");
            //if the username equals the username given, don't write to temp file
            if(!line[0].equals(usernameToDelete)){
                writer.write(currentLine);
            }
            //get the next line, if it not null, add a newline char
            currentLine = reader.readLine();
            if (currentLine != null) {
                writer.write(System.getProperty("line.separator"));
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
            case "create":
                create(inputGiven[1], inputGiven[2]);
                break;
            case "delete":
                delete(inputGiven[1]);
                break;
            case "post":
                //city, rentprice, bedrooms
                float rentprice = Float.parseFloat(inputGiven[1]);
                int bedrooms = Integer.parseInt(inputGiven[2]);
                post(inputGiven[0],rentprice,bedrooms);
                break;
            case "search":
                //cutym rebtprice bedrooms
                float rentprice1 = Float.parseFloat(inputGiven[1]);
                int bedrooms1 = Integer.parseInt(inputGiven[2]);
                search(inputGiven[0],rentprice1,bedrooms1);
                break;
            case "rent":
                //rentID, nights
                int rentID = Integer.parseInt(inputGiven[1]);
                int nights = Integer.parseInt(inputGiven[2]);
                rent(rentID,nights);
                break;
            default:
                System.out.println("Invalid Input!");
        }
        return ""; 
    }
}