package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//The parser class is used to read the availablerentalsfile.txt and userraccounts.txt files
//into arraylists that can be accessed from other classes
public class Parser {
    public static ArrayList<Unit> rentals = new ArrayList<Unit>(); 
    public static ArrayList<User> users = new ArrayList<User>();
    public final static Scanner sc = new Scanner(System.in); 

    public static void readAvailableRentalsFile(String rentalsFile,String  userAccountsFile) throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList(rentalsFile);
        for (String line: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = line.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] splitLine = trimmed.split("_");
            String rentID = splitLine[0]; 
            String username = splitLine[1];
            String city = splitLine[2];
            int numberOfBedrooms = Integer.parseInt(splitLine[3]);
            float rentalPricePerNight = Float.parseFloat(splitLine[4]);
            String rentalFlag = splitLine[5];
            if (splitLine[5].equals("t")){
                rentalFlag = "true";
            } else {rentalFlag = "false";}
            int numberOfNightsRemanining = Integer.parseInt(splitLine[6]);

            Unit rental = new Unit(rentID, username, city, numberOfBedrooms, rentalPricePerNight, rentalFlag, numberOfNightsRemanining);
            rentals.add(rental);
        }
    }

    public static void readUserAccountsFile(String rentalsFile, String  userAccountsFile) throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList(userAccountsFile);

        for (String line: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = line.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] splitLine = trimmed.split("_");
            String username = ""; 
            String userType = "";
            if (splitLine[0] != null) {
                username = splitLine[0];
            } 
            if (splitLine[1] != null) {
                userType = splitLine[1];
            } 

            User user = new User(username, userType, rentalsFile, userAccountsFile);
            users.add(user);
        }
    }

    public static ArrayList<String> readFileIntoArrayList(String filename) {
        ArrayList<String> list = new ArrayList<String>();
        try (Scanner s = new Scanner(new File(filename))) {
            while (s.hasNext()){
                list.add(s.next());
            }
            s.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    //Have to re-read from the file, since if the current user posted a rental, the rental wont be in the 
    //arraylist rentals, but it will be in the file
    //Writes the rent to a file, changing rentflag and nightsremaining for the given rentID
    public static void writePurchaseToRentalsFile(String rentID, int nights, String rentalsFile, String  userAccountsFile) throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList(rentalsFile);

        //loop through the contents of the file, when the rentID matches the rentID given to this method
        //change the number of nights remaining and the rental flag
        //add the lines to a list Lines
        List<String> lines = new ArrayList<>();
        for (String lineInFile: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = lineInFile.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] line = trimmed.split("_");

            String fileRentID = line[0];
            String username = line[1];
            String city = line[2];
            String numberOfBedrooms = line[3];
            String rentalPricePerNight = line[4];
            String rentalFlag = line[5];
            int numberOfNightsRemaining = Integer.parseInt(line[6]);

            if (rentID.equals(fileRentID)) {
                String lineToWrite= fileRentID + "_";
                lineToWrite += username;
                for (int i = 0; i < (14 - username.length()); i ++) {
                    lineToWrite += "_";
                }
                lineToWrite += "_";

                lineToWrite += city;
                for (int i = 0; i < (25 - city.length()); i ++) {
                    lineToWrite += "_";
                }
                lineToWrite += "_";

                lineToWrite += numberOfBedrooms + "_";

                lineToWrite += rentalPricePerNight;
                for (int i = 0; i < (6 - rentalPricePerNight.length()); i++) {
                    lineToWrite += "_";
                }
                lineToWrite += "_";
                lineToWrite += "t_";
                if (nights > 9 && nights < 100){
                    lineToWrite += Integer.toString(nights); 
                } else {
                    String rightJustified = "0" + Integer.toString(nights);
                    lineToWrite += rightJustified;
                }   

                lines.add(lineToWrite);
            }
            else {
                String lineToWrite= fileRentID + "_";
                lineToWrite += username;
                for (int i = 0; i < (14 - username.length()); i ++) {
                    lineToWrite += "_";
                }
                lineToWrite += "_";

                lineToWrite += city;
                for (int i = 0; i < (25 - city.length()); i ++) {
                    lineToWrite += "_";
                }
                lineToWrite += "_";

                lineToWrite += numberOfBedrooms + "_";

                lineToWrite += rentalPricePerNight;
                for (int i = 0; i < (6 - rentalPricePerNight.length()); i++) {
                    lineToWrite += "_";
                }
                lineToWrite += "_";
                lineToWrite += rentalFlag;
                lineToWrite += "_";

                if (numberOfNightsRemaining > 9 && numberOfNightsRemaining < 100){
                    lineToWrite += Integer.toString(numberOfNightsRemaining); 
                } else {
                    String rightJustified = "0" + Integer.toString(numberOfNightsRemaining);
                    lineToWrite += rightJustified;
                }   
                
                //System.out.println(lineToWrite);
                lines.add(lineToWrite);
            }
        }

        //We are done with reading from the file and changing the values
        //The file is in the list lines, write this to a file
        Path file = Paths.get(rentalsFile);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
