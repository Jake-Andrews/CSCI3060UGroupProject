package com.ot;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Rule;

//statement coverage

public class CommandLineTest 
{
    //Create a temporary folder, used to create temporary useraccounts/rentalsfiles for testing
    //See: https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html 
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    //Checking if the getUserType method correctly returns a user's type
    @Test
    public void checkUserType() throws IOException {
        //create temp files in tempFolder
        final File accountsFile = tempFolder.newFile("useraccounts.txt");
        final File rentalsFile = tempFolder.newFile("availablerentalsfile.txt");

        //creating a temporary useraccounts.txt file and a blank rentals file
        FileWriter fw1 = new FileWriter( accountsFile );
        BufferedWriter bw1 = new BufferedWriter( fw1 );
        bw1.write("USERTEST_______AA");
        bw1.close();
        FileWriter fw2 = new FileWriter( rentalsFile );
        BufferedWriter bw2 = new BufferedWriter( fw2 );
        bw2.write("");
        bw2.close();

        //reading in the useraccounts file because commandline.usertype uses a class variable
        //from parser that is created from Parser.readuseraccountsfile
        Parser.readUserAccountsFile(rentalsFile, accountsFile);
        CommandLine command = new CommandLine();
        System.out.println("Assert equals command.getUserType with username that is in the file.");
        assertEquals("AA", command.getUserType("USERTEST"));
        System.out.println("Assert equals command.getUserType with username that is not in the file.");
        assertEquals("", command.getUserType("USERTEST1"));
    }
}