package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

//The user class is created after a user logs in
//From Main.java, once a user is created, user.getCommands() is envoked
//This method has a while loop that will accept commands until logout is issued
//The getCommands() class delegates reading the inputs to the Commandline class which is called from 
//test.getInput(). This class doesn't get any input from the user, it recieves all of it from methods in commandline
//This class deals with reciving input and doing stuff based on the input and command recieved (writingb to a file, deleting from file, logging out)

public class User {
    public String userType;
    public String username;
    public boolean loggedIn; 
    public ArrayList<String> dailyTransactions1 = new ArrayList<String>();
    public CommandLine test = new CommandLine();
    public String rentalsFile = "";
    public String userAccountsFile = "";
    public String transactionsFile = "";

    public User(String username, String userType, String rentalsFile, String userAccountsFile){
        this.username = username; 
        this.userType = userType; 
        this.rentalsFile = rentalsFile; 
        this.userAccountsFile = userAccountsFile; 
        this.transactionsFile = "dailytransactionsfile1.txt";
    }

    //When a user logs in, the method is run and accepts user input and 
    //invokes other methods in this class depending on the input until the user quits
    public void takeTransactions(){
        //Commandline.getInput will return a string
        //Split the string based on spaces. The first word will determine which function 
        //Gets called in User (logout, etc...). Then the other words are used to complete the function
        //This removes all the commandline/userinput to its own class and cleans up the User class
        //All the validating of input, etc... will be done in Commandline, user only deals with sanitized input. 
        String inputCommand = "";
        while(!inputCommand.equals("logout")) {
            inputCommand = test.recieveTransaction();

            switch (inputCommand) {
                case "login":
                    System.out.println("ERROR: You are already logged in!");
                    break;
    
                case "logout":
                    this.loggedIn = false;
                    Unit unit = new Unit("", "", "", 000000, 0, "", 00);
                    addToTransactionArrayList("00", unit);
                    writeToTransactionFile();
                    System.out.print("\nPrinting Daily Transactions:");
                    for (String transaction: dailyTransactions1) {
                        System.out.println(transaction);
                    }
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
                        post().toString();
                        break;
                    } else {
                        System.out.println("ERROR: You cannot post a unit on a rent-standard account!");
                        break;
                    }
    
                case "search":
                    search();
                    break;
    
                case "rent":
                if (!this.userType.equals("PS")) {
                    rent();
                    break;
                } else {
                    System.out.println("ERROR: You cannot rent a unit on a post-standard account!");
                    break;
                }
    
                default:
                    System.out.println("Invalid Input!");
            }
        }
        System.out.println("You have been successfully logged out!"); 
    }
    public boolean invalidUsername(String username){
        if (!(username.length() < 15) || !(username.matches("[a-zA-Z0-9 ]*"))){
            System.out.println("Error, the username " + username + " is invalid. Usernames must contain less than 15 characters and alphanumeric characters!");
            return true; 
        }   
        return false; 
    }
    // Transactions
    public void create() {
        String newUsername;
        String newUsertype;

        // Get a username for the new account
        do {
            newUsername = test.getGenericInput("\nPlease enter a valid username: ");
        } while (invalidUsername(newUsername) || usernameExists(newUsername));        // Username is limited to at most 15 characters and cannot be special characters

        // Get a user type for the new account
        do {
            newUsertype = test.getGenericInput("Please enter a valid usertype: ");
        } while (!(newUsertype.matches("AA|FS|RS|PS")));                                    // User type must be an accepted type

        System.out.println("Creating the user: " + newUsername + " with a usertype of: " + newUsertype);
        //checking to see if there is already a user with the same username

        writeToAccountsFile(newUsername, newUsertype);
        System.out.println("User created!");

        //Unit(String rentID, String username, String city, float rentalPricePerNight, int numberOfBedrooms, String rentalFlag, int numberOfNightsRemanining)
        Unit unit = new Unit("", "", "", 0, 0, "", 0);
        addToTransactionArrayList("01", unit);
    }

    //used in create() to check if a usernameExists
    public boolean usernameExists(String username){
        for (User user: Parser.users){
            if (user.username.equals(username)){
                System.out.println("Error, the username " + username + " already exists.");
                return true;
            }
        }
        return false; 
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

            // Prevent user from deleting current user's account or an admin's account
            if (deletingUsername.equals(this.username)) {
                System.out.println("Error you cannot delete your own account!");
                continue;
            } 
            else if (test.isAdmin(deletingUsername)) {
                System.out.println("Error you cannot delete an admin's account!");
                continue;
            }
            else {                
                System.out.println("Deleting user: " + deletingUsername);
                doneDeleting = true;
                try {
                    deleteFromAccountsFile(deletingUsername);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }}
        } while (!doneDeleting);

        Unit unit = new Unit("", "", "", 0, 0, "", 0);
        addToTransactionArrayList("02", unit);
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
        } while (city.length() > 25 || (!(city.matches("[a-zA-Z[-\\s]]+"))));                 // City must have only letters from the alphabet (no numbers or special characters)
        city = city.substring(0, 1).toUpperCase() + city.substring(1);                      // Capatize first letter of cities
        // Get a renting price from user
        do {
            rentPrice = Float.parseFloat(test.getGenericInput("Please enter a valid price to rent the unit at (max is 999.99): "));
            dfrmt.format(rentPrice);
        } while (rentPrice < 0.01 || rentPrice > 999.99);                                   // Rent price must be above 0 and less than the maximum
        // Get a number of bedrooms from user
        do {
            bedrooms = Integer.parseInt(test.getGenericInput("Please enter the number of bedrooms (max is 9): "));
        } while (bedrooms < 1 || bedrooms > 9);                                             // Rent price must be above 0 and less than the maximum
        
        System.out.println("Creating unit now!");
        String unitID = generateUnitID();
        Unit newUnit = new Unit(unitID, this.username, city, bedrooms,  rentPrice, "false", 00);

        writeToRentalsFile(newUnit);

        // Add the new posted unit to the list of daily transactions
        addToTransactionArrayList("03", newUnit);

        return newUnit; 
    }

    //Read through availablerentalsfile.txt, then output every rentals that fits the req
    public void search() {
        String city;
        Float rentPrice;
        int bedrooms;
        DecimalFormat dfrmt = new DecimalFormat();
        dfrmt.setMaximumFractionDigits(2);

        do {
            city = test.getGenericInput("Please enter the city where the unit you are renting is: ");
            city.toLowerCase();
        } while ((!(city.matches("[a-zA-Z[-\\s]]+")) && city.length() > 25));                 // City must have only letters from the alphabet (no numbers or special characters)
        city = city.substring(0, 1).toUpperCase() + city.substring(1);                      // Capatize first letter of cities, since search is case sensitive
        
        do {
            rentPrice = Float.parseFloat(test.getGenericInput("Please enter a valid price to rent the unit at (max is 999.99): "));
            dfrmt.format(rentPrice);
        } while (rentPrice < 0.01 || rentPrice > 999.99);                                   // Rent price must be above 0 and less than the maximum
        // Get a number of bedrooms from user
        do {
            bedrooms = Integer.parseInt(test.getGenericInput("Please enter the number of bedrooms (max is 9): "));
        } while (bedrooms < 1 || bedrooms > 9);                                             // Rent price must be above 0 and less than the maximum

        //loop through the available Unit stored in Parser class
        //the units stored there will not included units added this session as per post requirement
        for (Unit rental: Parser.rentals){
            if (rental.getCity().equals(city) && rental.getRentalPricePerNight() <= rentPrice && rental.getNumberOfBedrooms() >= bedrooms && !(rental.getRentalFlag())) {
                System.out.println(rental);

                // Add each queried unit to the list of daily transactions
                addToTransactionArrayList("04", rental);
            }
        }   
    }

    public void rent() {
        String rentID;
        int nights;
        Unit rental;
        DecimalFormat dfrmt = new DecimalFormat();
        dfrmt.setMaximumFractionDigits(2);

        do {
            rentID = test.getGenericInput("Please enter the id of the unit you wish to rent: ");
            rental = rentalExists(rentID);
        } while (rental == null);                                                           // Rent ID must be an available unit (existing ID and is currently not being rented)

        do {
            nights = test.getGenericIntegerInput("Please enter a valid number of nights (integer value, minimum of 1, max of 14):");
        } while (nights <= 0 || nights > 14);                                                // Number of nights must be above 0 and less than the maximum

        int totalCost = nights * Math.round(rental.getRentalPricePerNight());
        
        dfrmt.format(totalCost);
        String confirmation = test.getGenericInput("Are you sure you want to book " + rentID + " with a price per night of $" + String.valueOf(rental.getRentalPricePerNight()) + " , and a total cost of $" + totalCost + "?\nType 'yes' to accept or 'no' to decline: ");
    
        //The user wants the rental, change rental flag and remaining nights
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Processing your order!");
            try {
                Parser.writePurchaseToRentalsFile(rentID, nights, rentalsFile, userAccountsFile);
                addToTransactionArrayList("05", rental);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("Your order has been completed!");
        } else {
            System.out.println("Order has been cancelled.");
        }
    }  

    public void writeToAccountsFile(String newUsername, String newUserType){
        //need to format the username and usertype properly
        //string needs to be 15 units long, subtract 2 for usertype.
        //Therefore, take length of username and subtract from 13 for the number of _ needed
        String toWriteToFile = newUsername;
        int underscoresNeeded = 15 - newUsername.length();
        
        for (int i = 0; i < underscoresNeeded; i ++){
            toWriteToFile += "_";
        }

        toWriteToFile += newUserType;

        try(FileWriter fw = new FileWriter(userAccountsFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {   
            out.println();
            out.print(toWriteToFile);
        } catch (IOException e) {
        }
        //reading the new useraccounts file into an arraylist for ease of use elsewhere in the program
        try {
            Parser.readUserAccountsFile(rentalsFile, userAccountsFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //creates a temp file, if the username is found, dont write to temp file
    //Once done, delete useraccounts.txt and rename temp file to useraccounts.txt
    public void deleteFromAccountsFile(String usernameToDelete) throws IOException {
        File inputFile = new File(userAccountsFile);
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

    // Add a new unit to current available rental units
    public void writeToRentalsFile(Unit newUnit) {
        String toWriteToFile = newUnit.getRentID() + "_";

        toWriteToFile += newUnit.getUsername();
        for (int i = 0; i < (14 - newUnit.getUsername().length()); i ++) {
            toWriteToFile += "_";
        }
        toWriteToFile += "_";

        toWriteToFile += newUnit.getCity();
        for (int i = 0; i < (25 - newUnit.getCity().length()); i ++) {
            toWriteToFile += "_";
        }
        toWriteToFile += "_";

        toWriteToFile += newUnit.getNumberOfBedrooms() + "_";
        //split based on period, if theres only one number, add a 0 infront of it (500.0) add 0 -> (500.00)
        String [] partsOfFloat = Float.toString(newUnit.getRentalPricePerNight()).split("\\.");
        String properDecimal = "";
        if (partsOfFloat[1].length() <= 1) {
            properDecimal = Float.toString(newUnit.getRentalPricePerNight()) + "0";
            toWriteToFile += properDecimal;
        } else {newUnit.getRentalPricePerNight();}

        for (int i = 0; i < (6 - (String.valueOf(newUnit.getRentalPricePerNight())).length()); i++) {
            toWriteToFile += "_";
        }
        toWriteToFile += "_";
        //if the rental flag is set to true, write t to the file, vice versa
        if (newUnit.getRentalFlag()){
            toWriteToFile += "t"; 
        } else {toWriteToFile += "f"; }
        toWriteToFile += "_";

        if (newUnit.getNumberOfNightsRemanining() > 9 && newUnit.getNumberOfNightsRemanining() < 100){
            toWriteToFile += Integer.toString(newUnit.getNumberOfNightsRemanining()); 
        } else {
            String rightJustified = "0" + Integer.toString(newUnit.getNumberOfNightsRemanining());
            toWriteToFile += rightJustified;
        }   

        try(FileWriter fw = new FileWriter(rentalsFile, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {   
            out.println();
            out.print(toWriteToFile);
        } catch (IOException e) {}
    }
    //Checking to see what number to append to the end of the transactionfile name
    //Does the file exist, if so, add a 1 to the end. loop until it doesn't exist.
    private void writeToTransactionFile(){
        File f = new File(transactionsFile);
        //getting filename from path
        String fileNameWithoutPath = f.getName();
        //removing .
        String fileNameWithoutExtension = fileNameWithoutPath.replaceFirst("[.][^.]+$", "");
        fileNameWithoutExtension = fileNameWithoutExtension.replaceFirst(".$","");
        int counter = 0;
        String filename = transactionsFile; 
        //check if file exists, if it does, add a 1 to the end, loop until file doesn't exist
        while(f.isFile()){ 
            counter++;
            filename = fileNameWithoutExtension + Integer.toString(counter) + ".txt";
            f = new File(filename); 
        } 
        //writing to a file
        try (FileWriter fw = new FileWriter(filename, true)) {
            for (String transaction : dailyTransactions1) {
                fw.write(transaction + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        

    // Generate a unique String to identify units
    private String generateUnitID() {
        String id = UUID.randomUUID().toString();
        id = id.substring(0, 8);
        return id;
    }

    // Find a given rentID is in availablerentalsfiles.txt. If it exists, return it. If not, return null
    private Unit rentalExists(String rentID) {
        for (Unit rental: Parser.rentals) {
            if (rental.getRentID().equals(rentID) && !(rental.getRentalFlag())) {
                return rental;
            }
        }  
        return null;
    }

    private void addToTransactionArrayList(String command, Unit unit) {
        //01-create, 02-delete, 03-post, 04-search, 05-rent, 00-end of session

        String toWriteToFile = command + "_";

        //adding username then filling in the possible empty spaces
        toWriteToFile += this.username;
        for (int i = 0; i < (14 - this.username.length()); i ++) {
            toWriteToFile += "_";
        }
        toWriteToFile += "_"; //seperating username and usertype

        toWriteToFile += this.userType + "_";

        //if there is no rentid (logout, etc...) add 9 spaces, 8 for rent id, one for space between items
        if (unit.getRentID().isEmpty()){
            for (int i = 0; i < 9; i ++){
                toWriteToFile += "_";
            }
        }
        else {toWriteToFile += unit.getRentID() + "_";}

        toWriteToFile += unit.getCity();
        for (int i = 0; i < (25 - unit.getCity().length()); i ++) {
            toWriteToFile += "_";
        }
        toWriteToFile += "_";

        toWriteToFile += Integer.toString(unit.getNumberOfBedrooms()) + "_";

        String [] partsOfFloat = Float.toString(unit.getRentalPricePerNight()).split("\\.");
        String properDecimal = "";
        if (partsOfFloat[1].length() <= 1) {
            properDecimal = Float.toString(unit.getRentalPricePerNight()) + "0";
            toWriteToFile += properDecimal;
        } else {unit.getRentalPricePerNight();}

        for (int i = 0; i < (5 - (String.valueOf(unit.getRentalPricePerNight())).length()); i++) {
            toWriteToFile += "_";
        }
        toWriteToFile += "_";

        //if the number of nights is a double digit
        if (unit.getNumberOfNightsRemanining() > 9 && unit.getNumberOfNightsRemanining() < 100){
            toWriteToFile += Integer.toString(unit.getNumberOfNightsRemanining()); 
        } else {
            String rightJustified = "0" + Integer.toString(unit.getNumberOfNightsRemanining());
            toWriteToFile += rightJustified;
        }   

        System.out.println("Daily transaction file string: " + toWriteToFile);
        dailyTransactions1.add(toWriteToFile);
    }

    @Override
    public String toString() {
        return String.format("Username: %s\r\nUserType: %s\r\n", 
        this.username, this.userType);
    }
}