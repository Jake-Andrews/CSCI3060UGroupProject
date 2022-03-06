package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
//class that handles all the command line dialogue with the user
//handles sanitizing input
public class CommandLine {
    public static String[] validCommands = {"logout", "create", "delete", "post", "search", "rent"};
    
    public CommandLine(){}

    public String getUserType(String username){
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList("useraccounts.txt");

        //loop through the arraylist, find the username given, return their userType
        for (String user: fileContents){
            //remove two or more _ and replace with one _
            String trimmed = user.trim().replaceAll("_{2,}", "_").trim();
            //Since there are only one _ between words, split based on _ 
            String[] line = trimmed.split("_");
            //for (String testing: line) {
            //    System.out.println("TEST: " + testing);
            //}
            //System.out.println("0: " + line[0] + ", " + line[1]);
            if (username.equals(line[0])) {
                return line[1];
            } 
        }
        return "";    
    }

    public Boolean isAdmin(String username){
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList("useraccounts.txt");

        //fileContents.forEach(line->System.out.println(line.split("_")[0]));

        //loop through the arraylist, find the username given, check if they are an admin
        for (String user: fileContents){
            String[] line = user.split("_");
            if (username.equals(line[0])) {
                if ("AA".equals(line[1])) {
                    return true; 
                } 
            } 
        }
        return false; 
    }

    public Boolean isUser(String username){
        //puts lines from useraccounts.txt into arraylist
        ArrayList<String> fileContents = readFileIntoArrayList("useraccounts.txt");

        //fileContents.forEach(line->System.out.println(line.split("_")[0]));

        //loop through the arraylist, see if the username given is a user
        for (String user: fileContents){
            String[] line = user.split("_");
            //System.out.println("username given: " + username + ", file: " + line[0] + ",");
            if (username.equals(line[0])) {
                System.out.println("You have been logged in!");
                return true; 
            } 
        }
        return false; 
    }

    public ArrayList<String> readFileIntoArrayList(String filename) {
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

    //Got bored with the regex
    //To do: Make sure the command we are returning is in the validcommands list. 
    //currently login123 would be returned instead of cropping 123 off and returning login
    //Could also just reject any user input that doesn't match (case insensitive) a command. 
    public String getInput(String userType) {
        //Get user input, while loop to make sure input is not empty. Give help commands if they want them. 
        System.out.println("\nPlease enter a command!\nFor a list of commands type 'help'");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();
        if (userInput.matches("^.*help.*$")){
        //regex to match help, and checks for help as a substring
        //if (userInput.matches("(?i).*help.*")){
            printCommands();
            scanner.nextLine();
        };

        //take input from a user until user provides a valid command
        userInput.toLowerCase();
        while (userInput.isEmpty() && !(userInput.matches("^.*?(login|logout|create|delete|post|search|rent).*$"))){
            System.out.println("Please input a valid command!");
            userInput = scanner.nextLine();
        }
        //once this is reached, a valid command has been entered
        //determine what the command was, get the req input, return this input
        //in the form of a space seperated list
        System.out.println("\nCommand given: " + userInput);
        return userInput;
    }
    
    //'help' command- print out valid commands for the user to take
    public void printCommands(){
        System.out.println("\nPrinting a list of valid commands:");
        for (String command: validCommands){
            System.out.println(command);
        }
        System.out.println("\nPlease enter a command!");
    }

    //Used to get input from a user
    //Prevents a user from entering nothing
    //in the next phase can add a variable that allows you to determine the length of the input
    //allowed. Maybe other stuff as well. Assumes the input is valid currently as per proj req
    public String getGenericInput(String repeat){
        String userInput = ""; 
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(repeat);
            userInput = sc.nextLine();
        }
        while (userInput.isEmpty());
        //System.out.println();

        return userInput; 
    }
}
