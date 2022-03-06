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

    public static void readAvailableRentalsFile() throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList("availablerentalsfile.txt");

        for (String line: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = line.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] splitLine = trimmed.split("_");

            String rentID = splitLine[0];
            String username = splitLine[1];
            String city = splitLine[2];
            float rentalPricePerNight = Float.parseFloat(splitLine[3]);
            int numberOfBedrooms = Integer.parseInt(splitLine[4]);
            String rentalFlag = splitLine[5];
            int numberOfNightsRemanining = Integer.parseInt(splitLine[6]);

            Unit rental = new Unit(rentID, username, city, rentalPricePerNight, numberOfBedrooms, rentalFlag, numberOfNightsRemanining);
            rentals.add(rental);
        }
    }

    public static void readUserAccountsFile() throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList("useraccounts.txt");

        for (String line: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = line.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] splitLine = trimmed.split("_");

            String username = splitLine[0];
            String userType = splitLine[1];

            User user = new User(username, userType);
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

    //When rent command is used, this writes the rental to a file
    /*
    public static void writePurchaseToRentalsFile(String rentID, int nights){

        try(FileWriter fw = new FileWriter("availablerentalsfile.txt", false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
        {   
            //loop through the rentals
            for (Unit rental : rentals){
                if (rental.rentID.equals(rentID)){
                    String lineToWrite = rental.rentID + "_" + rental.username + "_" + rental.city + "_" + Integer.toString(rental.numberOfBedrooms) + "_" + Float.toString(rental.rentalPricePerNight) + "_" + "T" + "_" + Integer.toString(nights);
                    out.print(lineToWrite);
                    out.println();
                }
                else {
                    String lineToWrite = rental.rentID + "_" + rental.username + "_" + rental.city + "_" + 
                    Integer.toString(rental.numberOfBedrooms) + "_" + Float.toString(rental.rentalPricePerNight) + 
                    "_" + rental.rentalFlag + "_" + Integer.toString(rental.numberOfNightsRemanining);
                    out.print(lineToWrite);
                    out.println();  
                }
            }
        } catch (IOException e) {
        }
    }*/

    //Have to re-read from the file, since if the current user posted a rental, the rental wont be in the 
    //arraylist rentals, but it will be in the file
    //Writes the rent to a file, changing rentflag and nightsremaining for the given rentID
    public static void writePurchaseToRentalsFile(String rentID, int nights) throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList("availablerentalsfile.txt");

        //loop through the contents of the file, when the rentID matches the rentID given to this method
        //change the number of nights remaining and the rental flag
        //add the lines to a list Lines
        List<String> lines = new ArrayList<>();
        for (String user: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = user.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] line = trimmed.split("_");

            String fileRentID = line[0];
            String username = line[1];
            String city = line[2];
            String numberOfBedrooms = line[3];
            String rentalPricePerNight = line[4];
            String rentalFlag = line[5];
            String numberOfNightsRemanining = line[6];

            if (rentID.equals(fileRentID)){
                String lineToWrite = fileRentID + "_" + username + "_" + city + "_" + numberOfBedrooms + "_" + rentalPricePerNight + "_" + "true" + "_" + nights;
                System.out.println(lineToWrite);
                lines.add(lineToWrite);
            }
            else {
                String lineToWrite = fileRentID + "_" + username + "_" + city + "_" + numberOfBedrooms + "_" + rentalPricePerNight + "_" + rentalFlag + "_" + numberOfNightsRemanining;
                System.out.println(lineToWrite);
                lines.add(lineToWrite);
            }
        }

        //We are done with reading from the file and changing the values
        //The file is in the list lines, write this to a file
        Path file = Paths.get("availablerentalsfile.txt");
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
