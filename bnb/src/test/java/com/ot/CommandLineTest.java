package com.ot;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;


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
        List<String> lines = Arrays.asList("USERTEST_______AA");
        Files.write(Paths.get(accountsFile.getName()), lines, StandardCharsets.UTF_8);
        List<String> lines1 = Arrays.asList("");
        Files.write(Paths.get(rentalsFile.getName()), lines1, StandardCharsets.UTF_8);

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