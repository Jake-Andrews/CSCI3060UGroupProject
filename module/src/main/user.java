package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;

//The user class is created after a user logs in
//From Main.java, once a user is created, user.getCommands() is envoked
//This method has a while loop that will accept commands until logout is issued
//The getCommands() class delegates reading the inputs to the Commandline class which is called from 
//test.getInput(). This class doesn't get any input from the user, it recieves all of it from methods in commandline
//This class deals with reciving input and doing stuff based on the input and command recieved (writingb to a file, deleting from file, logging out)
 
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

            //for (String input : commandsSplit) {
            //    System.out.println("Testing: Command Recieved: " + input);
            //}
            //[0] contains "logout" or "delete", etc... Rest are the necessary inputs (rentID, username, etc...)
            callCommand(commandsSplit[0], commandsSplit);
        }
        System.out.println("You have been successfully logged out!"); 
    }

    //The newusername and usertype havent been validated. username could be more than 15 char and usetype could be some random chars
    public void create(String newUsername, String newUsertype){
        System.out.println("Create the user: " + newUsername + " with a usertype of: " + newUsertype);
        writeToAccountsFile(newUsername, newUsertype);
    }

    //The commandline method already checked if the user was an admin, just make sure the user
    //isn't deleting their own account or check if the user exists
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
    public void post(String city, Float rentPrice, int bedrooms){
        System.out.println("Writing the rental to availablerentalsfile.txt");
        writeToRentalsFile(city, rentPrice, bedrooms); 
    }

    public void writeToRentalsFile(String city, Float rentPrice, int bedrooms){
        //Build the string to write to the file, one by one
        //Eventually, take its length and subtract from 45 to find the number of _ needed at the end before number of nights NN

        //need some sort fn that will check if rentprice has .00 appended. if it doesnt, add it 
        String toWriteToFile = randomString(8) + "_" + this.username + "_" + city + "_" + String.valueOf(bedrooms) + "_" +  String.format("%.2f", rentPrice) + "_" + "F";
        //still have to add the NN (number of nights remaining if currently rented), hence the 43 instead of 45.
        int underscoresNeeded = 43 - toWriteToFile.length();
        
        for (int i = 0; i < underscoresNeeded; i ++){
            toWriteToFile = toWriteToFile + "_";
        }

        toWriteToFile = toWriteToFile + "00";

        try(FileWriter fw = new FileWriter("availablerentalsfile.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {   
            out.println();
            out.print(toWriteToFile);
        } catch (IOException e) {
        }
    }


    //used to create a random alphanumeric string used in availablerentalsfile.txt as the rentID
    public String randomString(int length){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++)
           sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    //rent price is max, bedrooms is min, city is req
    //Read through availablerentalsfile.txt, then output every rentals that fits the req
    public void search1(String city, Float rentPrice, int bedrooms){

        //puts lines from availablerentals.txt into arraylist
        ArrayList<String> fileContents = Parser.readFileIntoArrayList("availablerentalsfile.txt");

        //loop through the arraylist, check for the correct city, then check bedroom and price values
        for (String user: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = user.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] line = trimmed.split("_");
            
            //putting the city, rentprice, bedrooms from the array into variables
            //to make it more readable
            String fileCityName = line[2];
            Float fileRentPrice = Float.parseFloat(line[4]);
            int fileBedrooms = Integer.parseInt(line[3]);
            
            if (fileCityName.equals(city) && fileRentPrice <= rentPrice && fileBedrooms >= bedrooms) {
                //System.out.println()    
            } 
        }   
    }

    //Read through availablerentalsfile.txt, then output every rentals that fits the req
    public void search(String city, Float rentPrice, int bedrooms){

        //loop through the Renting units stored in Parser class
        //the units stored there will not included units added this session as per post requirement
        for (Renting rental: Parser.rentals){
            if (rental.city.equals(city) && rental.rentalPricePerNight <= rentPrice && rental.numberOfBedrooms >= bedrooms && rental.rentalFlag.equals("F")) {
                System.out.println(rental);
            }
        }   
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
                Float rentprice = Float.parseFloat(inputGiven[2]);
                int bedrooms = Integer.parseInt(inputGiven[3]);
                post(inputGiven[1],rentprice,bedrooms);
                break;
            case "search":
                //cutym rebtprice bedrooms
                Float rentprice1 = Float.parseFloat(inputGiven[2]);
                int bedrooms1 = Integer.parseInt(inputGiven[3]);
                search(inputGiven[1],rentprice1,bedrooms1);
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

    @Override
    public String toString() {
        return String.format("Username: %s\r\nUserType: %s\r\n", 
        this.username, this.userType);
}
}