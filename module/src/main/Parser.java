package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//The parser class is used to read the availablerentalsfile.txt and userraccounts.txt files
//into arraylists that can be accessed from other classes
public class Parser {
    public static ArrayList<Renting> rentals = new ArrayList<Renting>(); 
    public static ArrayList<User> users = new ArrayList<User>();

    public static void readAvailableRentalsFile() throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList("availablerentalsfile.txt");

        for (String user: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = user.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] line = trimmed.split("_");

            String rentID = line[0];
            String username = line[1];
            String city = line[2];
            int numberOfBedrooms = Integer.parseInt(line[3]);
            float rentalPricePerNight = Float.parseFloat(line[4]);
            String rentalFlag = line[5];
            int numberOfNightsRemanining = Integer.parseInt(line[6]);

            Renting rental = new Renting(rentID, username, city, numberOfBedrooms, rentalPricePerNight, rentalFlag, numberOfNightsRemanining);
            rentals.add(rental);
        }

        //for (Renting rental : rentals){
        //    System.out.println(rental);
        //}
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

        //for (User user : users){
        //    System.out.println(user);
        //}
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
}
