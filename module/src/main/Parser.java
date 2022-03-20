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
            int numberOfNightsRemanining = Integer.parseInt(splitLine[6]);

            Unit rental = new Unit(rentID, username, city, numberOfBedrooms, rentalPricePerNight, rentalFlag, numberOfNightsRemanining);
            rentals.add(rental);
        }
    }

    public static void readUserAccountsFile(String rentalsFile,String  userAccountsFile) throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList(userAccountsFile);

        for (String line: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = line.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] splitLine = trimmed.split("_");

            String username = splitLine[0];
            String userType = splitLine[1];

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
    public static void writePurchaseToRentalsFile(String rentID, int nights, String rentalsFile,String  userAccountsFile) throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList(rentalsFile);

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
            String numberOfNightsRemaining = line[6];

            if (rentID.equals(fileRentID)) {
                String lineToWrite= fileRentID + "__";
                lineToWrite += username;
                    for (int i = 0; i < (17 - username.length()); i ++) {
                        lineToWrite += "_";
                    }
                lineToWrite += city;
                for (int i = 0; i < (27 - city.length()); i ++) {
                    lineToWrite += "_";
                }
                lineToWrite += numberOfBedrooms + "__";
                lineToWrite += rentalPricePerNight;
                for (int i = 0; i < (8 - rentalPricePerNight.length()); i++) {
                    lineToWrite += "_";
                }
                lineToWrite += "true___" + nights;

                System.out.println(lineToWrite);
                lines.add(lineToWrite);
            }
            else {
                String lineToWrite= fileRentID + "__";
                lineToWrite += username;
                    for (int i = 0; i < (17 - username.length()); i ++) {
                        lineToWrite += "_";
                    }
                lineToWrite += city;
                for (int i = 0; i < (27 - city.length()); i ++) {
                    lineToWrite += "_";
                }
                lineToWrite += numberOfBedrooms + "__";
                lineToWrite += rentalPricePerNight;
                for (int i = 0; i < (8 - rentalPricePerNight.length()); i++) {
                    lineToWrite += "_";
                }
                lineToWrite += rentalFlag;
                for (int i = 0; i < (7 - rentalFlag.length()); i ++) {
                    lineToWrite += "_";
                }
                lineToWrite += numberOfNightsRemaining;
                
                System.out.println(lineToWrite);
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
>>>>>>> 0db076c99f996bd92744f5b589e83e4df66e12db
