package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class User {
    public String userType;
    public String username;
    public String password;
    public Boolean loggedIn; 
    public Unit[] unitsRented; 
    public String dailyTransactions; 
    public CommandLine test = new CommandLine();

    public User(String username, String userType){
        this.username = username; 
        this.userType = userType; 
        this.loggedIn = true; 
    }
    //When a user logs in, the method is run and accepts user input and 
    //invokes other methods in this class depending on the input until the user quits
    public void takeCommands(){
        //commandLine.getInput will return a string
        //Split the string based on spaces. The first word will determine which function 
        //Gets called in User (logout, etc...). Then the other words are used to complete the function
        //This removes all the commandline/userinput to its own class and cleans up the User class
        //All the validating of input, etc... will be done in Commandline, user only deals with sanitized input. 
        //The string will be split on spaces and the first string thrown to a switch statment (fn callCommand)
        String inputCommand = "";
        while(!inputCommand.equals("logout")) {
            //If the user tries to delete but isn't an admin, ask for another command
            inputCommand = test.getInput(this.userType);

            switch (inputCommand) {
                case "login":
                    System.out.println("ERROR: You are already logged in!");
                    break;
    
                case "logout":
                    this.loggedIn = false;
                    break;
    
                case "create":
                    if (this.userType.equals("AA")) {
                        create();
                        break;
                    } else {
                        System.out.println("ERROR: You must be an admin to issue this command!");
                        break;
                    }
    
                case "delete":
                    if (this.userType.equals("AA")) {
                        delete();
                        break;
                    } else {
                        System.out.println("ERROR: You must be an admin to issue this command!");
                        break;
                    }
    
                case "post":
                    if (!this.userType.equals("RS")) {
                        post();
                        //TODO: print post.toString()
                        break;
                    } else {
                        System.out.println("ERROR: You cannot post a unit on a rent-standard account!");
                        break;
                    }
    
                case "search":
                    // TODO
                    break;
    
                case "rent":
                    // TODO
                    break;
    
                default:
                    System.out.println("Invalid Input!");
            }
        }
        System.out.println("You have been successfully logged out!"); 
    }

    // Transactions
    public void create() {
        String newUsername;
        String newUsertype;

        // Get a username for the new account
        do {
            newUsername = test.getGenericInput("\nPlease enter a valid username: ");
        } while (newUsername.length() > 15 || newUsername.matches("@|#|$|%|^|&|*"));        // Username is limited to at most 15 characters and cannot be special characters

        // Get a user type for the new account
        do {
            newUsertype = test.getGenericInput("\nPlease enter a valid usertype: ");
        } while (!(newUsertype.matches("AA|FS|RS|PS")));                                    // User type must be an accepted type

        System.out.println("Creating the user: " + newUsername + " with a usertype of: " + newUsertype);
        writeToAccountsFile(newUsername, newUsertype);
        System.out.println("User created!");
    }


    public void delete() {
        String deletingUsername;
        Boolean doneDeleting = false;

        do {
            deletingUsername = test.getGenericInput("\nPlease enter a valid username: ");
            System.out.println("Checking if the user exists... ");

            // Get user account to be deleted by using username
            if (test.isUser(username)){
                System.out.println("The user: " + deletingUsername + " exists in our system!");
            }

            // Prevent user from deleting current user's account
            if (!username.equals(this.username)) {
                System.out.println("Deleting user: " + username);
                doneDeleting = true;
                try {
                    deleteFromFile(username);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } else { System.out.println("ERROR: You cannot delete your own account!"); }
        } while (!doneDeleting);
    }

    public Unit post() {
        String city;
        float rentPrice;
        int bedrooms;
        DecimalFormat dfrmt = new DecimalFormat();
        dfrmt.setMaximumFractionDigits(2);

        // Get a city name from user
        do {
            city = test.getGenericInput("Please enter the city where the unit you are renting is: ");
            city.toLowerCase();
        } while (!(city.matches("[a-zA-Z]+") || city.length() > 25));                       // City must have only letters from the alphabet (no numbers or special characters)
        city = city.substring(0, 1).toUpperCase() + city.substring(1);                      // Capatize first letter of city
        // Get a renting price from user
        do {
            rentPrice = Float.parseFloat(test.getGenericInput("Please enter a valid price to rent the unit at (max is 999.99): "));
            dfrmt.format(rentPrice);
        } while (rentPrice < 0.01 || rentPrice > 999.99);                                   // Rent price must be above 0 and less than the maximum
        // Get a number of bedrooms from user
        do {
            bedrooms = Integer.parseInt(test.getGenericInput("Please enter the number of bedrooms (max is 9): "));
        } while (bedrooms < 1 || bedrooms > 9);                                   // Rent price must be above 0 and less than the maximum
        
        System.out.println("Creating unit now!");
        Unit newUnit = new Unit(city, rentPrice, bedrooms);

        writeToRentalsFile(newUnit);

        //TODO: save information to daily transaction file                           
        return newUnit; 
    }

    public Unit search(String city, Float rentPrice, int bedrooms){
        Unit unit = new Unit(city, rentPrice, bedrooms);
        return unit;
    }

    public void rent(int rentID, int nights){}

    
    public void writeToAccountsFile(String newUsername, String newUserType){
        //need to format the username and usertype properly
        //string needs to be 15 units long, subtract 2 for usertype.
        //Therefore, take length of username and subtract from 13 for the number of _ needed
        String toWriteToFile = newUsername;
        int underscoresNeeded = 18 - newUsername.length();
        
        for (int i = 0; i < underscoresNeeded; i ++){
            toWriteToFile += "_";
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

    // Add a new unit to current available rental units
    public void writeToRentalsFile(Unit newUnit) {
        // TODO generate unique ids
        String toWriteToFile = "id______";
        toWriteToFile += "__";

        toWriteToFile += this.username;
        for (int i = 0; i < (17 - this.username.length()); i ++) {
            toWriteToFile += "_";
        }

        toWriteToFile += newUnit.getCity();
        for (int i = 0; i < (27 - newUnit.getCity().length()); i ++) {
            toWriteToFile += "_";
        }

        toWriteToFile += newUnit.getRentPrice();
        for (int i = 0; i < (8 - (String.valueOf(newUnit.getRentPrice())).length()); i++) {
            toWriteToFile += "_";
        }

        toWriteToFile += newUnit.getBedrooms();

        try(FileWriter fw = new FileWriter("availablerentalsfile.txt", true);
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

    public String writeToTransactionFile(String transaction){return "";}
}