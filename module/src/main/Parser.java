package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    public static ArrayList<Renting> rentals = new ArrayList<Renting>(); 

    public static void readAvailableRentalsFile() throws FileNotFoundException{
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList("availablerentalsfile.txt");

        for (String user: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = user.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] line = trimmed.split("_");

            //for (String tes : line){
            //    System.out.println(tes);
            //}
            String rentID = line[0];
            String username = line[1];
            String city = line[2];
            float rentalPricePerNight = Float.parseFloat(line[3]);
            int numberOfBedrooms = Integer.parseInt(line[4]);

            //Renting rental = new Renting(rentID, username, city, numberOfBedrooms, rentalPricePerNight);
            //rentals.add(rental);
        }

        //Scanner input = new Scanner(new File("availablerentalsfile.txt"));
        //input.useDelimiter("_|\n");
        //int rentID int nights Boolean rentFlag
        /*
        while (input.hasNext()){
            String rentID = input.next();
            String username = input.next();
            String city = input.next();
            int numberOfBedrooms = input.nextInt();
            float rentalPricePerNight = input.nextFloat();
            String rentalFlag = input.next();
            int numberOfNightsRemanining = input.nextInt();

            Renting rental = new Renting(rentID, username, city, numberOfBedrooms, rentalPricePerNight, rentalFlag, numberOfNightsRemanining);
            rentals.add(rental);
        }
        */
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
