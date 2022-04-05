package com.ot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//The parser class is used to read the availablerentalsfile.txt and userraccounts.txt files
//into arraylists that can be accessed from other classes
public class Parser {
    public static ArrayList<Unit> rentals = new ArrayList<Unit>(); 
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<String> dailyTransactions1 = new ArrayList<String>();
    public final static Scanner sc = new Scanner(System.in); 
    public static String transactionsFile = "dailytransactionsfile1.txt";

    public static void readAvailableRentalsFile(File rentalsFile, File userAccountsFile) throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList(rentalsFile.getName());
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

    public static void readUserAccountsFile(File rentalsFile, File  userAccountsFile) throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList(userAccountsFile.getName());

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

            User user = new User(username, userType, rentalsFile.getName(), userAccountsFile.getName());
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

        //Checking to see what number to append to the end of the transactionfile name
    //Does the file exist, if so, add a 1 to the end. loop until it doesn't exist.
    public static void writeToTransactionFile(){
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

    public static void addToTransactionArrayList(String command, Unit unit, String username, String userType) {
        //01-create, 02-delete, 03-post, 04-search, 05-rent, 00-end of session
        String toWriteToFile = command + "_";

        //adding username then filling in the possible empty spaces
        toWriteToFile += username;
        for (int i = 0; i < (14 - username.length()); i ++) {
            toWriteToFile += "_";
        }
        toWriteToFile += "_"; //seperating username and usertype

        toWriteToFile += userType + "_";

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
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        toWriteToFile += df.format(unit.getRentalPricePerNight());

        for (int i = 0; i < (6 - (String.valueOf(df.format(unit.getRentalPricePerNight()))).length()); i++) {
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
}
